package playfit.se.members.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import playfit.se.members.entities.*;
import playfit.se.members.repositories.InvoiceRepository;
import playfit.se.members.repositories.SessionRepository;
import playfit.se.members.repositories.UserEntityRepository;
import playfit.se.members.responses.InvoiceGenerationResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserEntityRepository userEntityRepository;
    private final SessionRepository sessionRepository;

    //Method to generate a monthly invoice for all subscribed users
    public List<InvoiceGenerationResponse> generateMonthlyInvoices(Long userId) throws FileNotFoundException {
        List<InvoiceGenerationResponse> responses = new ArrayList<>();
        try {
            UserEntity user = userEntityRepository.findById(userId)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));

            for (SubscriptionEntity subscription : user.getSubscriptions()) {
                InvoiceEntity invoice = createInvoiceForSubscription(user, subscription);
                String filePath = generateAndStoreInvoice(invoice);
                responses.add(new InvoiceGenerationResponse(true, "Monthly invoice generated for subscription " + subscription.getId(), filePath));
            }
        } catch (Exception e) {
            responses.add(new InvoiceGenerationResponse(false, "Error generating monthly invoices: " + e.getMessage(), null));
        }
        return responses;
    }


    //Helper method to create an invoice for a subscription
    private InvoiceEntity createInvoiceForSubscription(UserEntity user, SubscriptionEntity subscription) {
        ClubEntity club = user.getClubEntity(); // User's associated club

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setUserEntity(user);
        invoice.setSubscriptionEntity(subscription);
        invoice.setClubEntity(club);

        invoice.setMemberFirstName(user.getFirstName());
        invoice.setMemberLastName(user.getLastName());
        invoice.setMemberEmail(user.getEmail());
        invoice.setClubName(club.getClubName());
        invoice.setOrganizationalNumber(club.getOrgNr());
        invoice.setOrganizationAddress(club.getAddress() + ", " + club.getZipCode() + " " + club.getCity());
        invoice.setTotaltAmount(subscription.getPricing().getPrice());

        // Additional fields like invoice number, date, due date, etc., should be set here

        return invoice;
    }





    //Helper method to Schedule generation of monthly invoices for all users
    @Scheduled(cron = "0 0 1 25 * ?") // Runs at 1 AM on the 25th of every month
    public void generateMonthlyInvoicesForAllUsers() throws FileNotFoundException {
        LocalDate scheduledDate = LocalDate.now().withDayOfMonth(25);
        LocalDate invoiceDate = adjustToWeekdayIfWeekend(scheduledDate);

        if (LocalDate.now().isEqual(invoiceDate)) {
            List<UserEntity> users = userEntityRepository.findAll();
            for (UserEntity user : users) {
                generateMonthlyInvoices(user.getId());
                // Handle the responses as needed
            }
        }
    }
    //Helper method to adjust the date to the nearest weekday if it falls on a weekend
    private LocalDate adjustToWeekdayIfWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return date.minusDays(1); // Move to Friday
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            return date.minusDays(2); // Move to Friday
        }
        return date;
    }





    //Method to generate invoices for all users in a session
    public List<InvoiceGenerationResponse> generateInvoicesForSession(Long sessionId) throws FileNotFoundException {
        List<InvoiceGenerationResponse> responses = new ArrayList<>();
        SessionEntity session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        for (UserEntity user : session.getUsers()) {
            if (user.getSubscriptions().isEmpty()) {
                InvoiceEntity invoice = createInvoiceForSession(user, session);
                String filePath = generateAndStoreInvoice(invoice);
                responses.add(new InvoiceGenerationResponse(true, "Invoice generated for user " + user.getId(), filePath));
            }
        }
        return responses;
    }
    //Method to generate an invoice for a user in a session
    public InvoiceGenerationResponse generateInvoiceForUserInSession(Long userId, Long sessionId) throws FileNotFoundException {
        UserEntity user = userEntityRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        SessionEntity session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        if (!user.getSubscriptions().isEmpty()) {
            return new InvoiceGenerationResponse(false, "User has a subscription, no invoice needed", null);
        }

        InvoiceEntity invoice = createInvoiceForSession(user, session);
        String filePath = generateAndStoreInvoice(invoice);
        return new InvoiceGenerationResponse(true, "Invoice generated for user " + user.getId(), filePath);
    }


    //Helper method to create an invoice for a user in a session
    private static InvoiceEntity createInvoiceForSession (UserEntity user, SessionEntity session) {
        ClubEntity club = user.getClubEntity(); // User's associated club

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setMemberFirstName(user.getFirstName());
        invoice.setMemberLastName(user.getLastName());
        invoice.setMemberEmail(user.getEmail());
        invoice.setClubName(club.getClubName());
        invoice.setOrganizationalNumber(club.getOrgNr());
        invoice.setOrganizationAddress(club.getAddress() + ", " + club.getZipCode() + " " + club.getCity());

        invoice.setTotaltAmount(session.getPricing().getPrice());
        return invoice;
    }

    //Helper method to generate and store an invoice
    public String generateAndStoreInvoice(InvoiceEntity invoice) throws FileNotFoundException {

    String filePath = "invoices/invoice_" + invoice.getId() + ".pdf";
    PdfWriter writer = new PdfWriter(filePath);
    PdfDocument pdfDoc = new PdfDocument(writer);
    Document document = new Document(pdfDoc);

    // Invoice Header
    document.add(new Paragraph("PlayFit Sports Club").setBold());
    document.add(new Paragraph("Invoice").setFontSize(14).setUnderline());

    // Invoice Details
    document.add(new Paragraph("Invoice Number: " + invoice.getInvoiceNum()));
    document.add(new Paragraph("Invoice Date: " + invoice.getInvoiceDate().format(DateTimeFormatter.ISO_DATE)));
    document.add(new Paragraph("Due Date: " + invoice.getDueDate()));

    // Club Details
    document.add(new Paragraph("Club Name: " + invoice.getClubName()));
    document.add(new Paragraph("Organization Number: " + invoice.getOrganizationalNumber()));
    document.add(new Paragraph("Address: " + invoice.getOrganizationAddress()));

    // Member Details
    document.add(new Paragraph("Member Name: " + invoice.getMemberFirstName() + " " + invoice.getMemberLastName()));
    document.add(new Paragraph("Member Email: " + invoice.getMemberEmail()));

    // Pricing Details
    document.add(new Paragraph("Total Amount: $" + invoice.getTotaltAmount()));
    document.add(new Paragraph("Price Excl. VAT: $" + invoice.getPriceExclVAT()));
    if (invoice.getDiscount() != null && invoice.getDiscount() > 0) {
        document.add(new Paragraph("Discount: $" + invoice.getDiscount()));
    }

    // Invoice Status and Payment Method
    document.add(new Paragraph("Invoice Status: " + invoice.getInvoiceStatus()));
    document.add(new Paragraph("Payment Method: " + invoice.getPaymentMethod()));

    // Close the document
    document.close();

    // Update the InvoiceEntity with the file path and save
    invoice.setInvoiceFilePath(new File(filePath).getAbsolutePath());
    invoiceRepository.save(invoice);
    return filePath;
}


}



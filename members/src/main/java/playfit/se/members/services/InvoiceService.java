package playfit.se.members.services;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;

import com.itextpdf.layout.element.Paragraph;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Service;
import playfit.se.members.entities.*;
import playfit.se.members.repositories.InvoiceRepository;
import playfit.se.members.repositories.SessionRepository;
import playfit.se.members.repositories.UserEntityRepository;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final UserEntityRepository userEntityRepository;
    private final SessionRepository sessionRepository;


    public String generateMonthlyInvoice(Long userId) throws FileNotFoundException {
        UserEntity user = userEntityRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        ClubEntity club = user.getClubEntity();
        SubscriptionEntity subscription = user.getSubscriptions().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No subscription found for user"));

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setMemberFirstName(user.getFirstName());
        invoice.setMemberLastName(user.getLastName());
        invoice.setMemberEmail(user.getEmail());
        invoice.setClubName(club.getClubName());
        invoice.setOrganizationalNumber(club.getOrgNr());
        invoice.setOrganizationAddress(club.getAddress() + ", " + club.getZipCode() + " " + club.getCity());

        invoice.setTotaltAmount(subscription.getPricing().getPrice());
        // Set other invoice details like invoice number, date, etc.

        return generateAndStoreInvoice(invoice);
    }



    public String generateSingleSessionInvoice(Long sessionId) throws FileNotFoundException {
        SessionEntity session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        // Assuming we are invoicing the first user linked to the session
        UserEntity user = session.getUsers().stream().findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No user found for session"));

        ClubEntity club = user.getClubEntity(); // User's associated club

        InvoiceEntity invoice = new InvoiceEntity();
        invoice.setMemberFirstName(user.getFirstName());
        invoice.setMemberLastName(user.getLastName());
        invoice.setMemberEmail(user.getEmail());
        invoice.setClubName(club.getClubName());
        invoice.setOrganizationalNumber(club.getOrgNr());
        invoice.setOrganizationAddress(club.getAddress() + ", " + club.getZipCode() + " " + club.getCity());

        invoice.setTotaltAmount(session.getPricing().getPrice());
        // Set other invoice details

        return generateAndStoreInvoice(invoice);
    }






    public String generateAndStoreInvoice(InvoiceEntity invoice) throws FileNotFoundException, FileNotFoundException {

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









/*    public String generateMonthlyInvoice(Long userId) throws FileNotFoundException {
        UserEntity user = userEntityRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String desiredType = "MONTHLY";
        SubscriptionEntity specificTypeSubscription = user.getSubscriptions().stream()
                .filter(subscription -> desiredType.equals(subscription.getType()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("No '" + desiredType + "' subscriptions found for user"));

        // Calculate the total amount based on subscription
        Double totalAmount = calculateTotalAmountForSubscription(specificTypeSubscription);

        InvoiceEntity invoice = new InvoiceEntity();
        // Populate invoice details...
        invoice.setUserEntity(user);
        invoice.setSubscriptionEntity(specificTypeSubscription);
        invoice.setTotaltAmount(totalAmount);
        // ...other details

        return generateAndStoreInvoice(invoice);
    }*/



    /*public String generateSingleSessionInvoice(Long sessionId) throws FileNotFoundException {
        SessionEntity session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Session not found"));

        // Assuming the session has pricing information
        Double sessionPrice = session.getPrice();

        InvoiceEntity invoice = new InvoiceEntity();
        // Populate invoice details...
        invoice.setSessionEntity(session);
        invoice.setTotaltAmount(sessionPrice);
        // ...other details

        return generateAndStoreInvoice(invoice);
    }*/


  /*  private Double calculateTotalAmountForSubscription(SubscriptionEntity subscription) {
        Double totalAmount = subscription.getPrice();
        // Logic to add any additional charges from activities
        // For example, iterating over linked activities and adding up charges

        return totalAmount;
    }*/




}



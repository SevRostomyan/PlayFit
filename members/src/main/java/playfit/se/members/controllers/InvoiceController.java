package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.responses.InvoiceGenerationResponse;
import playfit.se.members.services.InvoiceService;

import java.io.FileNotFoundException;
import java.util.List;

@RequestMapping("/api/v1/Invoices")
@RestController
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;


    //TODO: I metoden för att skapa en session har du lagt in ett default pris. Dubbelkolla så att generateMonthlyInvoices-metoden  ignorerar detta pris om kunden har prenumeration varifrån priset hämtas
    @PostMapping("/generate/monthly/{userId}")
    public ResponseEntity<List<InvoiceGenerationResponse>> generateMonthlyInvoices(@PathVariable Long userId) throws FileNotFoundException {
        List<InvoiceGenerationResponse> responses = invoiceService.generateMonthlyInvoices(userId);
        return ResponseEntity.ok(responses); // Respond with a list of responses
    }

    //Todo: se till denna metod använder enbart priset på sessionen och inte priset på prenumerationen
    //For all users in a single session
    @PostMapping("/generate-for-session/{sessionId}")
    public ResponseEntity<List<InvoiceGenerationResponse>> generateInvoicesForSession(@PathVariable Long sessionId) throws FileNotFoundException {
        List<InvoiceGenerationResponse> responses = invoiceService.generateInvoicesForSession(sessionId);
        return ResponseEntity.ok(responses);
    }

    //Todo: se till denna metod använder enbart priset på sessionen och inte priset på prenumerationen
    //For a single user in a single session

    @PostMapping("/generate-for-user/{userId}/in-session/{sessionId}")
    public ResponseEntity<InvoiceGenerationResponse> generateInvoiceForUserInSession(@PathVariable Long userId, @PathVariable Long sessionId) throws FileNotFoundException {
        InvoiceGenerationResponse response = invoiceService.generateInvoiceForUserInSession(userId, sessionId);
        return response.isSuccess() ? ResponseEntity.ok(response) : ResponseEntity.badRequest().body(response);
    }

}

















    /*@PostMapping("/generate/monthly/{userId}")
    public ResponseEntity<?> generateMonthlyInvoice(@PathVariable Long userId) {
        try {
            String invoiceFilePath = invoiceService.generateMonthlyInvoice(userId);
            return ResponseEntity.ok("Monthly invoice generated successfully. Stored at: " + invoiceFilePath);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }*/


    //TODO Ta reda på om vi behöver fakturera för en enskild session?

   /* @PostMapping("/generate/single/{sessionId}")
    public ResponseEntity<?> generateSingleSessionInvoice(@PathVariable Long sessionId) {
        try {
            String invoiceFilePath = invoiceService.generateSingleSessionInvoice(sessionId);
            return ResponseEntity.ok("Single session invoice generated successfully. Stored at: " + invoiceFilePath);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }*/


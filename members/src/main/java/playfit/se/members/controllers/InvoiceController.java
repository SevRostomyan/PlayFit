package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.entities.InvoiceEntity;
import playfit.se.members.services.InvoiceService;

import java.io.FileNotFoundException;

@RequestMapping("/api/v1/Invoices")
@RestController
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService invoiceService;



    @PostMapping("/generate/monthly/{userId}")
    public ResponseEntity<?> generateMonthlyInvoice(@PathVariable Long userId) {
        try {
            String invoiceFilePath = invoiceService.generateMonthlyInvoice(userId);
            return ResponseEntity.ok("Monthly invoice generated successfully. Stored at: " + invoiceFilePath);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/generate/single/{sessionId}")
    public ResponseEntity<?> generateSingleSessionInvoice(@PathVariable Long sessionId) {
        try {
            String invoiceFilePath = invoiceService.generateSingleSessionInvoice(sessionId);
            return ResponseEntity.ok("Single session invoice generated successfully. Stored at: " + invoiceFilePath);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
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


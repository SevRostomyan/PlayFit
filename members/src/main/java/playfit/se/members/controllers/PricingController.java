package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.PricingDTO;
import playfit.se.members.entities.PricingEntity;
import playfit.se.members.responses.AllPricingOptionsResponse;
import playfit.se.members.responses.CreatePricingResponse;
import playfit.se.members.services.PricingService;

import java.util.List;

@RequestMapping("/api/v1/pricing")
@RestController
@RequiredArgsConstructor
public class PricingController {

    private final PricingService pricingService;

    @PostMapping("/create-pricing")
    public ResponseEntity<CreatePricingResponse> createPricing(@RequestBody PricingDTO pricingDTO) {
        CreatePricingResponse response = pricingService.createPricing(pricingDTO);
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.badRequest().body(response);
        }
    }


    @GetMapping("/all-pricing-options")
    public ResponseEntity<AllPricingOptionsResponse> getAllPricingOptions() {
        AllPricingOptionsResponse response = pricingService.getAllPricingOptions();
        if (response.isSuccess()) {
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}


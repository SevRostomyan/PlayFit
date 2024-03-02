package playfit.se.members.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import playfit.se.members.DTOs.PricingDTO;
import playfit.se.members.entities.PricingEntity;
import playfit.se.members.services.PricingService;

import java.util.List;

@RequestMapping("/api/v1/pricing")
@RestController
@RequiredArgsConstructor
public class PricingController {

    private final PricingService pricingService;

    @PostMapping("/create-pricing")
    public ResponseEntity<PricingEntity> createPricing(@RequestBody PricingDTO pricingDTO) {
        PricingEntity createdPricing = pricingService.createPricing(pricingDTO);
        return new ResponseEntity<>(createdPricing, HttpStatus.CREATED);
    }

    @GetMapping("/all-pricing-options")
    public ResponseEntity<List<PricingEntity>> getAllPricingOptions() {
        List<PricingEntity> pricingOptions = pricingService.getAllPricingOptions();
        return ResponseEntity.ok(pricingOptions);
    }

}

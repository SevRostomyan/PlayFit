package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.PricingDTO;
import playfit.se.members.entities.PricingEntity;
import playfit.se.members.repositories.PricingRepository;
import playfit.se.members.responses.AllPricingOptionsResponse;
import playfit.se.members.responses.CreatePricingResponse;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingService {
    private final PricingRepository pricingRepository;

    public CreatePricingResponse createPricing(PricingDTO pricingDTO) {
        CreatePricingResponse response = new CreatePricingResponse();
        try {
            PricingEntity pricing = new PricingEntity();
            pricing.setPrice(pricingDTO.getPrice());
            pricing.setDiscount(pricingDTO.getDiscount());
            // Set other fields as necessary from the DTO

            PricingEntity savedPricing = pricingRepository.save(pricing);
            response.setSuccess(true);
            response.setMessage("Pricing created successfully");
            response.setPricingEntity(savedPricing);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setMessage("Failed to create pricing: " + e.getMessage());
        }
        return response;
    }

    public AllPricingOptionsResponse getAllPricingOptions() {
        try {
            List<PricingEntity> pricingOptions = pricingRepository.findAll();
            return new AllPricingOptionsResponse(true, "Pricing options fetched successfully", pricingOptions);
        } catch (Exception e) {
            return new AllPricingOptionsResponse(false, "Failed to fetch pricing options: " + e.getMessage(), null);
        }
    }




}

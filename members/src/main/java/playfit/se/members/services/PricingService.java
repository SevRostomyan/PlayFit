package playfit.se.members.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import playfit.se.members.DTOs.PricingDTO;
import playfit.se.members.entities.PricingEntity;
import playfit.se.members.repositories.PricingRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PricingService {
    private final PricingRepository pricingRepository;

    public List<PricingEntity> getAllPricingOptions() {
        return pricingRepository.findAll();
    }


    public PricingEntity createPricing(PricingDTO pricingDTO) {
        PricingEntity pricing = new PricingEntity();
        pricing.setPrice(pricingDTO.getPrice());
        pricing.setDiscount(pricingDTO.getDiscount());
        // Set other fields as necessary from the DTO
        return pricingRepository.save(pricing);
    }

}

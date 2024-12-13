package bg.softuni.mobilele.service.impl;

import bg.softuni.mobilele.model.dtos.offer.NewOfferDTO;
import bg.softuni.mobilele.model.dtos.offer.OfferDetailsDTO;
import bg.softuni.mobilele.model.entity.Offer;
import bg.softuni.mobilele.model.enums.EngineType;
import bg.softuni.mobilele.model.enums.TransmissionType;
import bg.softuni.mobilele.repository.OfferRepository;
import bg.softuni.mobilele.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OfferServiceImpl implements OfferService {

    private final OfferRepository offerRepository;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public UUID addOffer(NewOfferDTO newOfferDTO) {
        Offer offer = modelMapper.map(newOfferDTO, Offer.class);
        offer.setId(UUID.randomUUID());
        offer.setCreated(LocalDateTime.now());
        offer.setModified(LocalDateTime.now());
        return offerRepository.save(offer).getId();
    }

    @Override
    public EngineType[] getAllEngineTypes() {
        return EngineType.values();
    }

    @Override
    public TransmissionType[] getAllTransmissionTypes() {
        return TransmissionType.values();
    }

    @Override
    public OfferDetailsDTO getOfferById(UUID id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found!"));
        return modelMapper.map(offer, OfferDetailsDTO.class);
    }
}

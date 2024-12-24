package bg.softuni.mobilele.service.impl;

import bg.softuni.mobilele.model.dtos.offer.NewOfferDTO;
import bg.softuni.mobilele.model.dtos.offer.OfferDetailsDTO;
import bg.softuni.mobilele.model.dtos.offer.SummaryOfferDTO;
import bg.softuni.mobilele.model.entity.Offer;
import bg.softuni.mobilele.model.enums.EngineType;
import bg.softuni.mobilele.model.enums.TransmissionType;
import bg.softuni.mobilele.repository.OfferRepository;
import bg.softuni.mobilele.service.OfferService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
        return offerRepository.saveAndFlush(offer).getId();
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

    @Override
    public List<OfferDetailsDTO> getAllOffers() {
        return offerRepository.findAll()
                .stream()
                .map(offer -> modelMapper.map(offer, OfferDetailsDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOfferById(UUID id) {
        offerRepository.deleteById(id);
    }

    @Override
    public SummaryOfferDTO getOfferSummaryById(UUID id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found!"));
        return modelMapper.map(offer, SummaryOfferDTO.class);
    }

    @Override
    public NewOfferDTO getOfferDetailsForEdit(UUID id) {
        Offer offer = offerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found!"));
        return modelMapper.map(offer, NewOfferDTO.class);
    }

    public void updateOffer(UUID id, NewOfferDTO updatedOfferDTO) {
        Offer existingOffer = offerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found!"));

        // Update fields
//        existingOffer.setModel(updatedOfferDTO.getModel());
        existingOffer.setPrice(updatedOfferDTO.getPrice());
        existingOffer.setEngine(updatedOfferDTO.getEngine());
        existingOffer.setTransmission(updatedOfferDTO.getTransmission());
        existingOffer.setYear(updatedOfferDTO.getYear());
        existingOffer.setMileage(updatedOfferDTO.getMileage());
        existingOffer.setDescription(updatedOfferDTO.getDescription());
        existingOffer.setImageUrl(updatedOfferDTO.getImageUrl());

        offerRepository.save(existingOffer);
    }
}

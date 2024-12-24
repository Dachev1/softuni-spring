package bg.softuni.mobilele.service;

import bg.softuni.mobilele.model.dtos.offer.NewOfferDTO;
import bg.softuni.mobilele.model.dtos.offer.OfferDetailsDTO;
import bg.softuni.mobilele.model.dtos.offer.SummaryOfferDTO;
import bg.softuni.mobilele.model.enums.EngineType;
import bg.softuni.mobilele.model.enums.TransmissionType;

import java.util.List;
import java.util.UUID;

public interface OfferService {
    UUID addOffer(NewOfferDTO newOfferDTO);

    EngineType[] getAllEngineTypes();

    TransmissionType[] getAllTransmissionTypes();

    OfferDetailsDTO getOfferById(UUID offerId);

    List<OfferDetailsDTO> getAllOffers();

    void deleteOfferById(UUID id);
    SummaryOfferDTO getOfferSummaryById(UUID id);

    void updateOffer(UUID id, NewOfferDTO  newOfferDTO );

    NewOfferDTO getOfferDetailsForEdit(UUID id);
}

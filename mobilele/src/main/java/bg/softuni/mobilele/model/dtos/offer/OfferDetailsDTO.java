package bg.softuni.mobilele.model.dtos.offer;

import bg.softuni.mobilele.model.enums.EngineType;
import bg.softuni.mobilele.model.enums.TransmissionType;

import java.time.LocalDateTime;
import java.util.UUID;

public record OfferDetailsDTO(
        UUID id,
        String description,
        EngineType engine,
        String imageUrl,
        int mileage,
        double price,
        TransmissionType transmission,
        int year,
        LocalDateTime created,
        LocalDateTime modified
) {
}

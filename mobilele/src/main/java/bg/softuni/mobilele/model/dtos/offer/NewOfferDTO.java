package bg.softuni.mobilele.model.dtos.offer;

import bg.softuni.mobilele.model.enums.EngineType;
import bg.softuni.mobilele.model.enums.TransmissionType;
import jakarta.validation.constraints.*;

public record NewOfferDTO(
        @NotBlank String model,
        @Positive double price,
        @NotNull EngineType engine,
        @NotNull TransmissionType transmission,
        @Min(1886) @Max(2100) int year,
        @Positive int mileage,
        @NotBlank String description,
        @NotBlank @Pattern(regexp = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|jpeg|png)") String imageUrl
) {
}

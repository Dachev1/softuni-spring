package bg.softuni.mobilele.model.dtos.offer;

import bg.softuni.mobilele.model.enums.EngineType;
import bg.softuni.mobilele.model.enums.TransmissionType;
import jakarta.validation.constraints.*;

import java.util.UUID;

public class NewOfferDTO {

    private UUID id;

    @NotBlank
    private String model;

    @Positive
    private Double price;

    @NotNull
    private EngineType engine;

    @NotNull
    private TransmissionType transmission;

    @Min(1886)
    @Max(2100)
    private Integer year;

    @Positive
    private Integer mileage;

    @NotBlank
    private String description;

    @Pattern(regexp = "(http(s?):)([/|.|\\w|\\s|-])*\\.(?:jpg|jpeg|png)")
    private String imageUrl;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public EngineType getEngine() {
        return engine;
    }

    public void setEngine(EngineType engine) {
        this.engine = engine;
    }

    public TransmissionType getTransmission() {
        return transmission;
    }

    public void setTransmission(TransmissionType transmission) {
        this.transmission = transmission;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}

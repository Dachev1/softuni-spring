package bg.softuni.mobilele.model.dtos.offer;

import bg.softuni.mobilele.model.enums.EngineType;
import bg.softuni.mobilele.model.enums.TransmissionType;

import java.util.UUID;

public class SummaryOfferDTO {

    private int mileage;
    private double price;
    private String imageUrl;
    private EngineType engine;
    private TransmissionType transmission;

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
package bg.softuni.mobilele.repository;

import bg.softuni.mobilele.model.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OfferRepository extends JpaRepository<Offer, UUID> {
}

package com.paintingscollectors.repository;

import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
    List<Painting> findByOwner(User owner);

    List<Painting> findByOwnerNot(User user);

    List<Painting> findTop2ByOrderByVotesDescNameAsc();

    Optional<Painting> findByIdAndOwner(Long paintingId, User user);
}

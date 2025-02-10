package com.paintingscollectors.repository;

import com.paintingscollectors.model.entity.Painting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, UUID> {

    List<Painting> findAllByOwnerId(UUID id);

    List<Painting> findAllByOwnerIdNot(UUID id);

    List<Painting> findTop2ByOrderByVotesDescNameAsc();
}

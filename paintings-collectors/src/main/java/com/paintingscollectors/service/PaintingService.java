package com.paintingscollectors.service;

import com.paintingscollectors.model.dto.PaintingAddDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.User;
import jakarta.transaction.Transactional;

import java.util.List;

public interface PaintingService {
    @Transactional
    void addPainting(PaintingAddDTO paintingAddDTO, User owner);

    List<Painting> getPaintingsByOwner(User owner);

    List<Painting> getFavoritePaintings(User user);

    List<Painting> getOtherPaintings(User user);

    List<Painting> getMostRatedPaintings();

    List<Style> getAllStyles();

    void voteForPainting(Long paintingId, User user);

    void addToFavorites(Long paintingId, User user);

    void removePainting(Long paintingId, User user);

    void removeFavoritePainting(Long paintingId, User user);

    @Transactional
    boolean hasUserVoted(Long paintingId, User user);

    boolean hasUserVoted(Painting painting, User user);
}

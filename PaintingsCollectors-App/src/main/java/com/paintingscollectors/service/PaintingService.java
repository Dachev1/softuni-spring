package com.paintingscollectors.service;

import com.paintingscollectors.model.dto.AddPainting;
import com.paintingscollectors.model.dto.HomePageView;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PaintingService {

    private final PaintingRepository paintingRepository;
    private final StyleService styleService;
    private final UserService userService;

    @Autowired
    public PaintingService(PaintingRepository paintingRepository, StyleService styleService, UserService userService) {
        this.paintingRepository = paintingRepository;
        this.styleService = styleService;
        this.userService = userService;
    }

    public Painting addPainting(AddPainting addPainting, User owner) {

        Style style = styleService.findByName(addPainting.getStyle())
                .orElseThrow(() -> new IllegalArgumentException("Selected style does not exist"));

        Painting painting = Painting.builder()
                .name(addPainting.getName())
                .author(addPainting.getAuthorName())
                .imageUrl(addPainting.getImageUrl())
                .style(style)
                .owner(owner)
                .isFavorite(false)
                .votes(0)
                .build();

        return paintingRepository.save(painting);
    }

    @Transactional
    public HomePageView getHomePageData(UUID userId) {

        List<Painting> myPaintings = paintingRepository.findAllByOwnerId(userId);
        List<Painting> otherPaintings = paintingRepository.findAllByOwnerIdNot(userId);
        List<Painting> mostRatedPaintings = paintingRepository.findTop2ByOrderByVotesDescNameAsc();

        User currentUser = userService.getById(userId);
        List<Painting> myFavorites = currentUser.getFavoritePaintings().stream().toList();

        List<Painting> ratedPaintings = currentUser.getRatedPaintings() != null
                ? currentUser.getRatedPaintings().stream().toList()
                : List.of();

        return HomePageView.builder()
                .username(currentUser.getUsername())
                .myPaintings(myPaintings)
                .myFavorites(myFavorites)
                .otherPaintings(otherPaintings)
                .mostRatedPaintings(mostRatedPaintings)
                .ratedPaintings(ratedPaintings)
                .build();
    }

    @Transactional
    public Painting removePainting(UUID paintingId, UUID userId) {

        Painting painting = getPainting(paintingId);
        if (!painting.getOwner().getId().equals(userId)) {
            throw new IllegalStateException("You are not authorized to delete this painting.");
        }

        if (painting.getVotes() > 0) {
            throw new IllegalStateException("Cannot delete painting as it has likes.");
        }

        long favoritesCount = userService.getFavoritesCount(paintingId, userId);
        if (favoritesCount > 0) {
            throw new IllegalStateException("Cannot delete painting as it is favorited by other users.");
        }

        userService.getAllUsers().forEach(u -> {
            u.getRatedPaintings().remove(painting);
            u.getFavoritePaintings().remove(painting);
        });

        paintingRepository.delete(painting);
        return painting;
    }

    @Transactional
    public Painting addToFavorites(UUID paintingId, UUID userId) {

        Painting painting = getPainting(paintingId);
        if (painting.getOwner().getId().equals(userId)) {
            throw new IllegalStateException("You cannot add your own painting to favorites.");
        }

        User user = userService.getById(userId);
        if (user.getFavoritePaintings().contains(painting)) {
            throw new IllegalStateException("Painting is already in favorites.");
        }

        user.getFavoritePaintings().add(painting);
        return painting;
    }

    @Transactional
    public Painting removeFromFavorites(UUID paintingId, UUID userId) {

        Painting painting = getPainting(paintingId);
        User user = userService.getById(userId);

        if (!user.getFavoritePaintings().contains(painting)) {
            throw new IllegalStateException("Painting is not in favorites.");
        }

        user.getFavoritePaintings().remove(painting);
        return painting;
    }

    @Transactional
    public Painting votePainting(UUID paintingId, UUID userId) {

        Painting painting = getPainting(paintingId);
        if (painting.getOwner().getId().equals(userId)) {
            throw new IllegalStateException("You cannot vote for your own painting.");
        }

        User user = userService.getById(userId);
        if (user.getRatedPaintings().contains(painting)) {
            throw new IllegalStateException("You have already voted for this painting.");
        }

        user.getRatedPaintings().add(painting);
        painting.setVotes(painting.getVotes() + 1);
        return paintingRepository.save(painting);
    }

    private Painting getPainting(UUID paintingId) {
        return paintingRepository.findById(paintingId)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found"));
    }
}

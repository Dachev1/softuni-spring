package com.paintingscollectors.service.impl;

import com.paintingscollectors.model.dto.PaintingAddDTO;
import com.paintingscollectors.model.entity.Painting;
import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.User;
import com.paintingscollectors.repository.PaintingRepository;
import com.paintingscollectors.repository.StyleRepository;
import com.paintingscollectors.repository.UserRepository;
import com.paintingscollectors.service.PaintingService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaintingServiceImpl implements PaintingService {

    private final PaintingRepository paintingRepository;
    private final StyleRepository styleRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public PaintingServiceImpl(PaintingRepository paintingRepository, StyleRepository styleRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.paintingRepository = paintingRepository;
        this.styleRepository = styleRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Transactional
    @Override
    public void addPainting(PaintingAddDTO paintingAddDTO, User owner) {
        Style style = styleRepository.findByStyleName(paintingAddDTO.getStyle())
                .orElseThrow(() -> new IllegalArgumentException("Invalid style!"));

        Painting painting = modelMapper.map(paintingAddDTO, Painting.class);
        painting.setStyle(style);
        painting.setOwner(owner);

        paintingRepository.save(painting);
    }

    @Override
    public List<Painting> getPaintingsByOwner(User owner) {
        return paintingRepository.findByOwner(owner);
    }

    @Override
    @Transactional
    public List<Painting> getFavoritePaintings(User user) {
        User fetchedUser = userRepository.findByIdWithFavoritePaintings(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return List.copyOf(fetchedUser.getFavoritePaintings());
    }

    @Override
    @Transactional
    public List<Painting> getOtherPaintings(User user) {
        List<Painting> paintings = paintingRepository.findByOwnerNot(user);

        for (Painting painting : paintings) {
            painting.setHasUserVoted(painting.getVoters().contains(user));
        }

        return paintings;
    }

    @Override
    public List<Painting> getMostRatedPaintings() {
        return paintingRepository.findTop2ByOrderByVotesDescNameAsc();
    }

    @Override
    public List<Style> getAllStyles() {
        return styleRepository.findAll();
    }

    @Override
    @Transactional
    public void voteForPainting(Long paintingId, User user) {
        Painting painting = paintingRepository.findById(paintingId)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found"));
        if (!painting.getVoters().contains(user)) {
            painting.getVoters().add(user);
            painting.setVotes(painting.getVotes() + 1);
            painting.setHasUserVoted(true);
            paintingRepository.save(painting);
        }
    }

    @Override
    public boolean hasUserVoted(Long paintingId, User user) {
        Painting painting = paintingRepository.findById(paintingId)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found"));
        return painting.getVoters().contains(user);
    }

    @Override
    @Transactional
    public void addToFavorites(Long paintingId, User user) {
        Painting painting = paintingRepository.findById(paintingId)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found"));
        User fetchedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        fetchedUser.getFavoritePaintings().add(painting);
        painting.setFavorite(true);
        userRepository.save(fetchedUser);
        paintingRepository.save(painting);
    }

    @Override
    public void removePainting(Long paintingId, User user) {
        Painting painting = paintingRepository.findByIdAndOwner(paintingId, user)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found or you are not the owner"));

        paintingRepository.delete(painting);
    }

    @Override
    @Transactional
    public void removeFavoritePainting(Long paintingId, User user) {
        Painting painting = paintingRepository.findById(paintingId)
                .orElseThrow(() -> new IllegalArgumentException("Painting not found"));

        User fetchedUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        fetchedUser.getFavoritePaintings().remove(painting);
        painting.setFavorite(false); // Update state
        userRepository.save(fetchedUser);
        paintingRepository.save(painting);
    }


    @Override
    public boolean hasUserVoted(Painting painting, User user) {
        return painting.getVoters().contains(user);
    }
}

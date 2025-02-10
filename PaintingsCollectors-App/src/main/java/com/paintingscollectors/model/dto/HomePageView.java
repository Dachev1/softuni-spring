package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.entity.Painting;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class HomePageView {

    private String username;
    private List<Painting> myPaintings;
    private List<Painting> myFavorites;
    private List<Painting> otherPaintings;
    private List<Painting> mostRatedPaintings;
    private List<Painting> ratedPaintings; // List of paintings the user has voted for
}

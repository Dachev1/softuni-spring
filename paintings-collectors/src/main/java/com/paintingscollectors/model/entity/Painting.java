package com.paintingscollectors.model.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "paintings")
public class Painting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @ManyToOne
    @JoinColumn(name = "style_id", nullable = false)
    private Style style;

    @ManyToOne
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private boolean isFavorite;

    @Column(nullable = false)
    private int votes;

    @ManyToMany
    @JoinTable(
            name = "painting_voters",
            joinColumns = @JoinColumn(name = "painting_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> voters;


    @Transient
    private boolean hasUserVoted;

    public Painting() {
        this.voters = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Set<User> getVoters() {
        return voters;
    }

    public void setVoters(Set<User> voters) {
        this.voters = voters;
    }

    public boolean isHasUserVoted() {
        return hasUserVoted;
    }

    public void setHasUserVoted(boolean hasUserVoted) {
        this.hasUserVoted = hasUserVoted;
    }
}


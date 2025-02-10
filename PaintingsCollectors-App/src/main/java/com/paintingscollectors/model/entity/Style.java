package com.paintingscollectors.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Style {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private StyleName styleName;

    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "style")
    private Set<Painting> paintings = new HashSet<>();
}

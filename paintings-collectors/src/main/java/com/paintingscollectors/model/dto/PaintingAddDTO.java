package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.enums.StyleName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PaintingAddDTO {

    @NotBlank(message = "Name cannot be empty!")
    @Size(min = 5, max = 40, message = "Name length must be between 5 and 40 characters!")
    private String name;

    @NotBlank(message = "Author cannot be empty!")
    @Size(min = 5, max = 30, message = "Author length must be between 5 and 30 characters!")
    private String author;

    @NotBlank(message = "Image URL cannot be empty!")
    private String imageUrl;

    private StyleName style;

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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StyleName getStyle() {
        return style;
    }

    public void setStyle(StyleName style) {
        this.style = style;
    }
}

package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.entity.StyleName;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class AddPainting {

    @Size(min = 5, max = 40, message = "Name length must be between 5 and 40 characters!")
    private String name;

    @Size(min = 5, max = 30, message = "Author name must be between 5 and 30 characters!")
    private String authorName;

    @URL(message = "Please enter valid image URL!")
    private String imageUrl;

    @NotNull(message = "You must select style!")
    private StyleName style;

}

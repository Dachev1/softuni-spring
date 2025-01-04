package com.bonappetit.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.io.Serializable;

public class RecipeAddDTO implements Serializable {

    @NotBlank
    @Size(min = 2, max = 40)
    private String name;

    @NotBlank
    @Size(min = 2, max = 150)
    private String ingredients;

    @NotBlank
    @Pattern(regexp = "MAIN_DISH|DESSERT|COCKTAIL", message = "Invalid category")
    private String category;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

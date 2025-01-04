package com.bonappetit.init;

import com.bonappetit.model.entity.Category;
import com.bonappetit.model.entity.emuns.CategoryName;
import com.bonappetit.repo.CategoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitCategories {

    private static final Logger logger = LoggerFactory.getLogger(InitCategories.class);

    @Bean(name = "initCategoriesRunner")
    CommandLineRunner initCategories(CategoryRepository categoryRepository) {
        return args -> {
            if (categoryRepository.count() == 0) {
                categoryRepository.save(new Category(CategoryName.MAIN_DISH, CategoryName.MAIN_DISH.getDescription()));
                categoryRepository.save(new Category(CategoryName.DESSERT, CategoryName.DESSERT.getDescription()));
                categoryRepository.save(new Category(CategoryName.COCKTAIL, CategoryName.COCKTAIL.getDescription()));
                logger.info("Categories initialized in the database.");
            } else {
                logger.info("Categories already exist in the database. No initialization needed.");
            }
        };
    }
}
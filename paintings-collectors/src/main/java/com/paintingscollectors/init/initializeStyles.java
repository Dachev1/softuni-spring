package com.paintingscollectors.init;

import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.enums.StyleName;
import com.paintingscollectors.repository.StyleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class initializeStyles implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(initializeStyles.class);
    private final StyleRepository styleRepository;

    public initializeStyles(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (styleRepository.count() == 0) {
            LOGGER.info("Initializing styles...");

            Arrays.stream(StyleName.values()).forEach(styleName -> {
                Style style = new Style();
                style.setStyleName(styleName);
                style.setDescription(styleName.getDescription());
                styleRepository.save(style);
                LOGGER.info("Added style: {} - {}", styleName, styleName.getDescription());
            });

            LOGGER.info("All styles have been initialized.");
        } else {
            LOGGER.info("Styles already exist in the database. Initialization skipped.");
        }
    }
}

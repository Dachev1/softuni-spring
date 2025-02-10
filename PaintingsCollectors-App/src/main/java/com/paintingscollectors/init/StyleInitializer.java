package com.paintingscollectors.init;

import com.paintingscollectors.model.entity.Style;
import com.paintingscollectors.model.entity.StyleName;
import com.paintingscollectors.repository.StyleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class StyleInitializer implements CommandLineRunner {

    private final StyleRepository styleRepository;

    @Autowired
    public StyleInitializer(StyleRepository styleRepository) {
        this.styleRepository = styleRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        if (styleRepository.count() == 0) {

            List<Style> styles = Arrays.stream(StyleName.values())
                    .map(styleName -> Style.builder()
                            .styleName(styleName)
                            .description(styleName.getDefaultDescription())
                            .build())
                    .toList();

            styleRepository.saveAll(styles);

            log.info("Default styles have been initialized.");
        }
    }
}

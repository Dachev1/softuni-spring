package com.paintingscollectors.model.entity;

public enum StyleName {
    IMPRESSIONISM("Impressionism is a painting style most commonly associated with the 19th century where small brush strokes are used to build up a larger picture."),
    ABSTRACT("Abstract art does not attempt to represent recognizable subjects in a realistic manner."),
    EXPRESSIONISM("Expressionism is a style of art that doesn't concern itself with realism."),
    SURREALISM("Surrealism is characterized by dreamlike, fantastical imagery that often defies logical explanation."),
    REALISM("Also known as naturalism, this style of art is considered as 'real art' and has been the dominant style of painting since the Renaissance.");

    private final String defaultDescription;

    StyleName(String defaultDescription) {
        this.defaultDescription = defaultDescription;
    }

    public String getDefaultDescription() {
        return defaultDescription;
    }
}

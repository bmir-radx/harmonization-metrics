package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashMap;
import java.util.Map;

public enum DataFileCategory {
    ORIG("orig"),
    TRANSFORM("transform");

    private String text;

    DataFileCategory(String text) {
        this.text = text;
    }

    private static final Map<String, DataFileCategory> textToCategory;

    static {
        textToCategory = new HashMap<>();
        textToCategory.put(ORIG.text, ORIG);
        textToCategory.put(TRANSFORM.text, TRANSFORM);
    }

    public static DataFileCategory fromString(String text) throws InvalidDataFileCategoryException {
        if (!textToCategory.containsKey(text)) {
            throw new InvalidDataFileCategoryException(text);
        }
        return textToCategory.get(text);
    }
}

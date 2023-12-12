package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.HashMap;
import java.util.Map;

/*
Enum class that provides an identifier to distinguish between
origcopy and transformcopy files.
 */
public enum OrigTransformCategory {
    ORIG("orig"),
    TRANSFORM("transform");

    private final String text;

    OrigTransformCategory(String text) {
        this.text = text;
    }

    private static final Map<String, OrigTransformCategory> textToCategory;

    static {
        Map <String, OrigTransformCategory> textToCategoryMap = new HashMap<>();
        textToCategoryMap.put(ORIG.text, ORIG);
        textToCategoryMap.put(TRANSFORM.text, TRANSFORM);
        textToCategory = textToCategoryMap;
    }

    public static OrigTransformCategory fromString(String text)
            throws InvalidOrigTransformCategoryException {
        if (!textToCategory.containsKey(text)) {
            throw new InvalidOrigTransformCategoryException(text);
        }
        return textToCategory.get(text);
    }
}

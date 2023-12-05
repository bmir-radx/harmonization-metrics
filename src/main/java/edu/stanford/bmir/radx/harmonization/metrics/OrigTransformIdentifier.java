package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashMap;
import java.util.Map;

/*
Enum class that provides an identifier to distinguish between
origcopy and transformcopy files.
 */
public enum OrigTransformIdentifier {
    ORIG("orig"),
    TRANSFORM("transform");

    private String text;

    OrigTransformIdentifier(String text) {
        this.text = text;
    }

    private static final Map<String, OrigTransformIdentifier> textToIdentifier;

    static {
        textToIdentifier = new HashMap<>();
        textToIdentifier.put(ORIG.text, ORIG);
        textToIdentifier.put(TRANSFORM.text, TRANSFORM);
    }

    public static OrigTransformIdentifier fromString(String text)
            throws InvalidOrigTransformIdentifierException {
        if (!textToIdentifier.containsKey(text)) {
            throw new InvalidOrigTransformIdentifierException(text);
        }
        return textToIdentifier.get(text);
    }
}

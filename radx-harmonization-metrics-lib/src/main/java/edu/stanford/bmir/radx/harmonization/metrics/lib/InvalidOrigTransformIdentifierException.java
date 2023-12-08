package edu.stanford.bmir.radx.harmonization.metrics.lib;

public class InvalidOrigTransformIdentifierException extends Exception {

    private final String identifierString;

    public InvalidOrigTransformIdentifierException(String identifierString) {
        this.identifierString = identifierString;
    }

    @Override
    public String getMessage() {
        return String.format("%s is not a valid data file identifier. Valid entries: %s, %s",
                identifierString, OrigTransformIdentifier.ORIG, OrigTransformIdentifier.TRANSFORM);
    }
}

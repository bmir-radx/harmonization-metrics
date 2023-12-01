package edu.stanford.bmir.radx.harmonization.metrics;

public class InvalidDataFileCategoryException extends Exception {

    private final String categoryString;

    public InvalidDataFileCategoryException(String categoryString) {
        this.categoryString = categoryString;
    }

    @Override
    public String getMessage() {
        return String.format("%s is not a valid data file category. Valid entries: %s, %s",
                categoryString, DataFileCategory.ORIG, DataFileCategory.TRANSFORM);
    }
}

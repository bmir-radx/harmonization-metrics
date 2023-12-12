package edu.stanford.bmir.radx.harmonization.metrics.lib;

public class InvalidOrigTransformCategoryException extends Exception {

    private final String categoryString;

    public InvalidOrigTransformCategoryException(String categoryString) {
        this.categoryString = categoryString;
    }

    @Override
    public String getMessage() {
        return String.format("%s is not a valid data file category. Valid entries: %s, %s",
                categoryString, OrigTransformCategory.ORIG, OrigTransformCategory.TRANSFORM);
    }
}

package edu.stanford.bmir.radx.harmonization.metrics;

public class NoVersionNumberException extends Exception {
    private final String fileName;

    public NoVersionNumberException(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public String getMessage() {
        return String.format("%s does not have a version number.", fileName);
    }
}

package edu.stanford.bmir.radx.harmonization.metrics;

public class InvalidProgramIdentifierException extends Exception {

    private final String programString;

    public InvalidProgramIdentifierException(String programString) {
        this.programString = programString;
    }

    @Override
    public String getMessage() {
        return String.format("%s is not a valid program identifier. Valid program identifiers: %s, %s, %s, %s",
                programString, ProgramIdentifier.RADXUP, ProgramIdentifier.RADXRAD, ProgramIdentifier.RADXTECH,
                ProgramIdentifier.DHT);
    }
}

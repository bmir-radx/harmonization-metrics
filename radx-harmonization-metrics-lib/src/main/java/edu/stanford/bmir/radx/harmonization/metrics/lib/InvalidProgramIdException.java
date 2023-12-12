package edu.stanford.bmir.radx.harmonization.metrics.lib;

public class InvalidProgramIdException extends Exception {

    private final String programString;

    public InvalidProgramIdException(String programString) {
        this.programString = programString;
    }

    @Override
    public String getMessage() {
        return String.format("%s is not a valid program identifier. Valid program identifiers: %s, %s, %s, %s",
                programString, ProgramId.RADXUP, ProgramId.RADXRAD, ProgramId.RADXTECH,
                ProgramId.DHT);
    }
}

package edu.stanford.bmir.radx.harmonization.metrics;

public class InvalidProgramException extends Exception {

    private final String programString;

    public InvalidProgramException(String programString) {
        this.programString = programString;
    }

    @Override
    public String getMessage() {
        return String.format("%s is not a valid program. Valid programs: %s, %s, %s, %s",
                programString, Program.RADXUP, Program.RADXRAD, Program.RADXTECH, Program.DHT);
    }
}

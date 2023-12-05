package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashMap;
import java.util.Map;

public enum ProgramIdentifier {
    RADXUP("RADx-UP"),
    RADXRAD("RADx-rad"),
    RADXTECH("RADx-Tech"),
    DHT("Digital Health Technologies");

    private final String value;

    ProgramIdentifier(String value) {
        this.value = value;
    }

    private static final Map<String, ProgramIdentifier> stringToProgram;
    static {
        stringToProgram = new HashMap<>();
        stringToProgram.put(RADXUP.value, RADXUP);
        stringToProgram.put(RADXRAD.value, RADXRAD);
        stringToProgram.put(RADXTECH.value, RADXTECH);
        stringToProgram.put(DHT.value, DHT);
    }

    public String toString() {
        return this.value;
    }

    public static ProgramIdentifier fromString(String programString) throws InvalidProgramIdentifierException {
        if (!stringToProgram.containsKey(programString)) {
            throw new InvalidProgramIdentifierException(programString);
        }
        return stringToProgram.get(programString);
    }
}

package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashMap;
import java.util.Map;

public enum Program {
    RADXUP("RADx-UP"),
    RADXRAD("RADx-rad"),
    RADXTECH("RADx-Tech"),
    DHT("Digital Health Technologies");

    private final String value;

    Program(String value) {
        this.value = value;
    }

    private static final Map<String, Program> stringToProgram;
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

    public static Program fromString(String programString) throws InvalidProgramException {
        if (!stringToProgram.containsKey(programString)) {
            throw new InvalidProgramException(programString);
        }
        return stringToProgram.get(programString);
    }
}

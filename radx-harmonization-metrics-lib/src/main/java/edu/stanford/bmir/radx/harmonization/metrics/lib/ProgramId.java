package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.HashMap;
import java.util.Map;

public enum ProgramId {
    RADXUP("RADx-UP"),
    RADXRAD("RADx-rad"),
    RADXTECH("RADx Tech"),
    DHT("Digital Health Technologies");

    private final String value;

    ProgramId(String value) {
        this.value = value;
    }

    private static final Map<String, ProgramId> stringToProgram;
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

    public static ProgramId fromString(String programString) throws InvalidProgramIdException {
        if (!stringToProgram.containsKey(programString)) {
            throw new InvalidProgramIdException(programString);
        }
        return stringToProgram.get(programString);
    }
}

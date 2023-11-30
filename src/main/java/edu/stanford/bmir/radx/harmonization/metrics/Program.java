package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashMap;
import java.util.Map;

public enum Program {
    RADXUP, RADXRAD, RADXTECH, DHT;

    private static final Map<Program, String> programToString;
    private static final Map<String, Program> stringToProgram;
    static {
        programToString = new HashMap<>();
        programToString.put(RADXUP, "RADx-UP");
        programToString.put(RADXRAD, "RADx-rad");
        programToString.put(RADXTECH, "RADx-Tech");
        programToString.put(DHT, "Digital Health Technologies");

        stringToProgram = new HashMap<>();
        for (Map.Entry<Program, String> entry: programToString.entrySet()) {
            stringToProgram.put(entry.getValue(), entry.getKey());
        }
    }

    public String toString() {
        return programToString.get(this);
    }

    public static Program fromString(String programString) {
        return stringToProgram.get(programString);
    }
}

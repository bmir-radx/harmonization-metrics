package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashSet;

public class DataFile {
    private int version;
    private HashSet<String> variableNames;

    public DataFile(int version, HashSet<String> variableNames) {
        this.version = version;
        this.variableNames = variableNames;
    }

    public int getVersion() {
        return version;
    }

    public HashSet<String> getVariableNames() {
        return this.variableNames;
    }
}

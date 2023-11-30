package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Set;

public class DataFile {
    private int version;
    private Set<String> variableNames;

    public DataFile(int version, Set<String> variableNames) {
        this.version = version;
        this.variableNames = variableNames;
    }

    public int getVersion() {
        return version;
    }

    public Set<String> getVariableNames() {
        return this.variableNames;
    }
}

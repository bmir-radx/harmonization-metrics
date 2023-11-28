package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashSet;

public class DataFile {
    private int version;
    private HashSet<String> variableNames;

    public int getVersion() {
        return version;
    }

    public HashSet<String> getVariableNames() {
        return this.variableNames;
    }
}

package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Set;

public interface DataFile {
    String fileName();
    int version();
    Set<String> variableNames();
}

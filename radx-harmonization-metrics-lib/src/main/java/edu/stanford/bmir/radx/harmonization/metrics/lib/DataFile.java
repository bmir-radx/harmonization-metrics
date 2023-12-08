package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.Set;

public interface DataFile {
    String fileName();
    int version();
    Set<String> variableNames();
}

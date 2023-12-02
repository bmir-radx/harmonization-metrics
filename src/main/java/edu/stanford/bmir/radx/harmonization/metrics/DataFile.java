package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Set;

public record DataFile(
        String fileName,
        int version,
        Set<String> variableNames) {
}

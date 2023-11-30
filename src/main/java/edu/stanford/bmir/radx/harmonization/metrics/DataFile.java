package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Set;

public record DataFile(
        int version,
        Set<String> variableNames) {
}

package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Set;

public record OrigFile(String fileName,
                       int version,
                       Set<String> variableNames)
        implements DataFile {
}

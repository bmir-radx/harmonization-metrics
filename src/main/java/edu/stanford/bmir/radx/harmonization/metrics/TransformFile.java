package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Set;

public record TransformFile(String fileName,
                            int version,
                            Set<String> variableNames)
        implements DataFile {
}

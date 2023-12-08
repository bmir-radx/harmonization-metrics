package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.Set;

public record TransformFile(String fileName,
                            int version,
                            Set<String> variableNames)
        implements DataFile {
}

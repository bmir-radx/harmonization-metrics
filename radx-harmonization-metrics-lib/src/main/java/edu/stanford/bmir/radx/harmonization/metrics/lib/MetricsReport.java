package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.List;

public record MetricsReport(
        List<OrigTransformFilePairMetrics> pairMetrics,
        List<StudyMetrics> studyMetrics) {
}

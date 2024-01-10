package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.time.LocalDate;
import java.util.List;

public record MetricsReport(
        LocalDate date,
        List<OrigTransformFilePairMetrics> pairMetrics,
        List<StudyMetrics> studyMetrics) {
}

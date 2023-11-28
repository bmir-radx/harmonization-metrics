package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Optional;

public record StudyMetrics(
        Optional<Integer> nOrigDEs, Optional<Integer> nTransformDEs,
        Optional<Integer> nHarmonizableOrigDEs, Optional<Integer> nHarmonizableTransformDEs,
        Optional<Integer> nHarmonizedDEs, Optional<Integer> nNonHarmonizableOrigDEs,
        Optional<Integer> nNonHarmonizableTransformDEs) {
}
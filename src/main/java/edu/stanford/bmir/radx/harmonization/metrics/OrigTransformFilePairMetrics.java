package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Optional;
import java.util.Set;

public record OrigTransformFilePairMetrics(
        ReducedFileName pairName,
        ProgramIdentifier programIdentifier,
        StudyId studyId,
        Optional<Integer> versionOrig,
        Optional<Integer> nDataElementsOrig,
        Optional<Integer> nHarmonizableDataElementsOrig,
        Optional<Integer> nHarmonizedDataElementsOrig,
        Optional<Integer> versionTransform,
        Optional<Integer> nDataElementsTransform,
        Optional<Integer> nHarmonizableDataElementsTransform,
        Optional<Integer> nHarmonizedDataElementsTransform,
        Integer nHarmonizableDataElements,
        Integer nHarmonizedDataElements,
        Integer nNonHarmonizableDataElements) {

    public boolean hasHarmonizableElements() {
        return nHarmonizableDataElements > 0;
    }

    public boolean hasNoHarmonizableElements() {
        return nHarmonizableDataElements == 0;
    }

    public boolean hasHarmonizedElements() {
        return nHarmonizedDataElements > 0;
    }

    public boolean hasNoHarmonizedElements() {
        return nHarmonizedDataElements == 0;
    }

    public boolean isHarmonizable() {
        return hasHarmonizableElements();
    }

    public boolean isPartiallyHarmonized() {
        return hasHarmonizableElements() && hasHarmonizedElements();
    }

    public boolean isHarmonized() {
        return hasNoHarmonizableElements();
    }

    public boolean isTriviallyHarmonized() {
        return hasNoHarmonizableElements() && hasNoHarmonizedElements();
    }
}
package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.Optional;

/*
Metrics per OrigTransformFilePair. Each OrigTransformFilePair
should have one corresponding OrigTransformFilePairMetrics
generated by the OrigTransformFilePairMetricsGenerator.
 */
public record OrigTransformFilePairMetrics(
        ReducedFileName pairName,
        ProgramId programId,
        StudyId studyId,
        Optional<String> origFileName,
        Optional<String> transformFileName,
        Integer nDataElementsOrig,
        Integer nDataElementsTransform,
        Integer nHarmonizableDataElementsTier1,
        Integer nHarmonizableDataElementsTier2,
        Integer nHarmonizableDataElementsTier3,
        Integer nHarmonizedDataElementsTier1,
        Integer nHarmonizedDataElementsTier2,
        Integer nHarmonizedDataElementsTier3) {

    public boolean hasHarmonizedDataElement() {
        return nHarmonizedDataElementsTier1 + nHarmonizedDataElementsTier2 + nHarmonizedDataElementsTier3 > 0;
    }
}
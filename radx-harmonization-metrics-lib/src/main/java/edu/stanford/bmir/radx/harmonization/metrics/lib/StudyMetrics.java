package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.Set;

/*
aggregates OrigTransformFilePairMetrics by StudyId
 */
public record StudyMetrics(
        StudyId studyId,
        ProgramId programId,
        Integer nOrigTransformFilePairs,
        Integer nUniqueDataElements,
        Integer nUniqueHarmonizableDataElementsTier1,
        Integer nUniqueHarmonizableDataElementsTier2,
        Integer nUniqueHarmonizableDataElementsTier3,
        Integer totalHarmonizable,
        Integer nUniqueHarmonizedDataElementsTier1,
        Integer nUniqueHarmonizedDataElementsTier2,
        Integer nUniqueHarmonizedDataElementsTier3,
        Integer totalHarmonized,
        Set<String> harmonizableDataElementsTier1,
        Set<String> harmonizableDataElementsTier2,
        Set<String> harmonizableDataElementsTier3,
        Set<String> harmonizableDataElements,
        Set<String> harmonizedDataElementsTier1,
        Set<String> harmonizedDataElementsTier2,
        Set<String> harmonizedDataElementsTier3,
        Set<String> harmonizedDataElements) {
}

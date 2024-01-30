package edu.stanford.bmir.radx.harmonization.metrics.lib;

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
        Integer totalHarmonized) {
}

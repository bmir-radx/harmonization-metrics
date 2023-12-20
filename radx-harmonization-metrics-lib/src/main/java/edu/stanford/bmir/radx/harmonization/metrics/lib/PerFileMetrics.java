package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.Set;

public record PerFileMetrics(
    int nHarmonizableDataElementsTier1,
    int nHarmonizableDataElementsTier2,
    int nHarmonizableDataElementsTier3,
    int nHarmonizedDataElementsTier1,
    int nHarmonizedDataElementsTier2,
    int nHarmonizedDataElementsTier3,
    int nDataElements) {

    public static PerFileMetrics generatePerFileMetrics(
            HarmonizationChecker harmonizationChecker, ProgramId programId,
            Set<String> variableNames)
            throws InvalidHarmonizationTierException, InvalidProgramIdException {
        int nDataElements = variableNames.size();
        int nHarmonizableDataElementsTier1 = harmonizationChecker.countHarmonizableElements(
                programId, variableNames, HarmonizationTier.TIER1);
        int nHarmonizableDataElementsTier2 = harmonizationChecker.countHarmonizableElements(
                programId, variableNames, HarmonizationTier.TIER2);
        int nHarmonizableDataElementsTier3 = harmonizationChecker.countHarmonizableElements(
                programId, variableNames, HarmonizationTier.TIER3);
        int nHarmonizedDataElementsTier1 = harmonizationChecker.countHarmonizedElements(
                programId, variableNames, HarmonizationTier.TIER1);
        int nHarmonizedDataElementsTier2 = harmonizationChecker.countHarmonizedElements(
                programId, variableNames, HarmonizationTier.TIER2);
        int nHarmonizedDataElementsTier3 = harmonizationChecker.countHarmonizedElements(
                programId, variableNames, HarmonizationTier.TIER3);
        return new PerFileMetrics(
                nHarmonizableDataElementsTier1,
                nHarmonizableDataElementsTier2,
                nHarmonizableDataElementsTier3,
                nHarmonizedDataElementsTier1,
                nHarmonizedDataElementsTier2,
                nHarmonizedDataElementsTier3,
                nDataElements);
    }
}

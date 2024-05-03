package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.Set;

abstract class InternalMetricsGenerator {

    protected final HarmonizationChecker harmonizationChecker;

    public InternalMetricsGenerator(HarmonizationChecker harmonizationChecker) {
        this.harmonizationChecker = harmonizationChecker;
    }

    protected VariableSetMetrics generateVariableSetMetrics(
            ProgramId programId, Set<String> variableNames)
            throws InvalidHarmonizationTierException, InvalidProgramIdException {
        int nDataElements = variableNames.size();
        Set<String> harmonizableElementsTier1 = harmonizationChecker.filterHarmonizableElements(
                programId, variableNames, HarmonizationTier.TIER1);
        int nHarmonizableDataElementsTier1 = harmonizableElementsTier1.size();
        Set<String> harmonizableElementsTier2 = harmonizationChecker.filterHarmonizableElements(
                programId, variableNames, HarmonizationTier.TIER2);
        int nHarmonizableDataElementsTier2 = harmonizableElementsTier2.size();
        Set<String> harmonizableElementsTier3 = harmonizationChecker.filterHarmonizableElements(
                programId, variableNames, HarmonizationTier.TIER3);
        int nHarmonizableDataElementsTier3 = harmonizableElementsTier3.size();
        Set<String> harmonizedElementsTier1 = harmonizationChecker.filterHarmonizedElements(
                programId, variableNames, HarmonizationTier.TIER1);
        int nHarmonizedDataElementsTier1 = harmonizedElementsTier1.size();
        Set<String> harmonizedElementsTier2 = harmonizationChecker.filterHarmonizedElements(
                programId, variableNames, HarmonizationTier.TIER2);
        int nHarmonizedDataElementsTier2 = harmonizedElementsTier2.size();
        Set<String> harmonizedElementsTier3 = harmonizationChecker.filterHarmonizedElements(
                programId, variableNames, HarmonizationTier.TIER3);
        int nHarmonizedDataElementsTier3 = harmonizedElementsTier3.size();
        return new VariableSetMetrics(
                nHarmonizableDataElementsTier1,
                nHarmonizableDataElementsTier2,
                nHarmonizableDataElementsTier3,
                nHarmonizedDataElementsTier1,
                nHarmonizedDataElementsTier2,
                nHarmonizedDataElementsTier3,
                nDataElements);
    }
}

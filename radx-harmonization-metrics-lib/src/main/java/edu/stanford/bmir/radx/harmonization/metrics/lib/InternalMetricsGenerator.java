package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.Set;

abstract class InternalMetricsGenerator {

    protected final HarmonizationChecker harmonizationChecker;

    public InternalMetricsGenerator(HarmonizationChecker harmonizationChecker) {
        this.harmonizationChecker = harmonizationChecker;
    }

    protected HarmonizedVariableSets filterVariablesByHarmonization(
            ProgramId programId, Set<String> variableNames)
            throws InvalidHarmonizationTierException, InvalidProgramIdException {
        Set<String> harmonizableElementsTier1 = harmonizationChecker.filterHarmonizableElements(
                programId, variableNames, HarmonizationTier.TIER1);
        Set<String> harmonizableElementsTier2 = harmonizationChecker.filterHarmonizableElements(
                programId, variableNames, HarmonizationTier.TIER2);
        Set<String> harmonizableElementsTier3 = harmonizationChecker.filterHarmonizableElements(
                programId, variableNames, HarmonizationTier.TIER3);
        Set<String> harmonizedElementsTier1 = harmonizationChecker.filterHarmonizedElements(
                programId, variableNames, HarmonizationTier.TIER1);
        Set<String> harmonizedElementsTier2 = harmonizationChecker.filterHarmonizedElements(
                programId, variableNames, HarmonizationTier.TIER2);
        Set<String> harmonizedElementsTier3 = harmonizationChecker.filterHarmonizedElements(
                programId, variableNames, HarmonizationTier.TIER3);
        return new HarmonizedVariableSets(
                harmonizableElementsTier1,
                harmonizableElementsTier2,
                harmonizableElementsTier3,
                harmonizedElementsTier1,
                harmonizedElementsTier2,
                harmonizedElementsTier3,
                variableNames);
    }
}

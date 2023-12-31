package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.springframework.stereotype.Component;

import java.util.Set;

/*
This is a helper that uses the provided HarmonizationRules
to count harmonizable data elements.
 */
@Component
public class HarmonizationChecker {

    private final HarmonizationRules harmonizationRules;

    public HarmonizationChecker(HarmonizationRules rules) {
        harmonizationRules = rules;
    }

    public int countHarmonizableElements(
            ProgramId programId, Set<String> elements, HarmonizationTier tier)
            throws InvalidProgramIdException, InvalidHarmonizationTierException {
        var nHarmonizableElements = 0;
        for (var element: elements) {
            if (harmonizationRules.isHarmonizable(programId, element, tier)) {
                nHarmonizableElements++;
            }
        }
        return nHarmonizableElements;
    }

    public int countHarmonizedElements(
            ProgramId programId, Set<String> elements, HarmonizationTier tier)
            throws InvalidProgramIdException, InvalidHarmonizationTierException {
        int nHarmonizedElements = 0;
        for (String element: elements) {
            if (harmonizationRules.isHarmonized(programId, element, tier)) {
                nHarmonizedElements++;
            }
        }
        return nHarmonizedElements;
    }
}

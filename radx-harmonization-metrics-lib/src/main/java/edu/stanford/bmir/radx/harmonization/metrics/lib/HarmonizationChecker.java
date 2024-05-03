package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.springframework.stereotype.Component;

import java.util.HashSet;
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

    public Set<String> filterHarmonizableElements(
            ProgramId programId, Set<String> elements, HarmonizationTier tier)
            throws InvalidProgramIdException, InvalidHarmonizationTierException {
        Set<String> harmonizableElements = new HashSet<>();
        for (var element: elements) {
            if (harmonizationRules.isHarmonizable(programId, element, tier)) {
                harmonizableElements.add(element);
            }
        }
        return harmonizableElements;
    }

    public Set<String> filterHarmonizedElements(
            ProgramId programId, Set<String> elements, HarmonizationTier tier)
            throws InvalidProgramIdException, InvalidHarmonizationTierException {
        Set<String> harmonizedElements = new HashSet<>();
        for (String element: elements) {
            if (harmonizationRules.isHarmonized(programId, element, tier)) {
                harmonizedElements.add(element);
            }
        }
        return harmonizedElements;
    }
}

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

    public int countHarmonizableElements(ProgramIdentifier programIdentifier, Set<String> elements) throws InvalidProgramIdentifierException {
        var nHarmonizableElements = 0;
        for (var element: elements) {
            if (harmonizationRules.isHarmonizable(programIdentifier, element)) {
                nHarmonizableElements++;
            }
        }
        return nHarmonizableElements;
    }

    public int countHarmonizedElements(ProgramIdentifier programIdentifier, Set<String> elements) throws InvalidProgramIdentifierException {
        int nHarmonizedElements = 0;
        for (String element: elements) {
            if (harmonizationRules.isHarmonized(programIdentifier, element)) {
                nHarmonizedElements++;
            }
        }
        return nHarmonizedElements;
    }
}

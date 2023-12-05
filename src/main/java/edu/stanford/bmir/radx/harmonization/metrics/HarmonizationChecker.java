package edu.stanford.bmir.radx.harmonization.metrics;

import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class HarmonizationChecker {

    private HarmonizationRules harmonizationRules;

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

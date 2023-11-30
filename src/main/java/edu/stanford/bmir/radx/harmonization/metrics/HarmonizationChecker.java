package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Set;

public class HarmonizationChecker {

    private HarmonizationRules harmonizationRules;

    public HarmonizationChecker(HarmonizationRules rules) {
        harmonizationRules = rules;
    }

    public int countHarmonizableElements(String program, Set<String> elements) {
        int nHarmonizableElements = 0;
        for (String element: elements) {
            if (harmonizationRules.isHarmonizable(program, element)) {
                nHarmonizableElements++;
            }
        }
        return nHarmonizableElements;
    }

    public int countHarmonizedElements(String program, Set<String> elements) {
        int nHarmonizedElements = 0;
        for (String element: elements) {
            if (harmonizationRules.isHarmonized(program, element)) {
                nHarmonizedElements++;
            }
        }
        return nHarmonizedElements;
    }
}

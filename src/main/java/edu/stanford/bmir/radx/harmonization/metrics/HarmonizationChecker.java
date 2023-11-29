package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashSet;

public class HarmonizationChecker {

    private HarmonizationRules harmonizationRules;

    public HarmonizationChecker(HarmonizationRules rules) {
        harmonizationRules = rules;
    }

    public int countHarmonizableElements(HashSet<String> elements) {
        int nHarmonizableElements = 0;
        for (String element: elements) {
            if (harmonizationRules.isHarmonizable(element)) {
                nHarmonizableElements++;
            }
        }
        return nHarmonizableElements;
    }

    public int countHarmonizedElements(HashSet<String> elements) {
        int nHarmonizedElements = 0;
        for (String element: elements) {
            if (harmonizationRules.isHarmonized(element)) {
                nHarmonizedElements++;
            }
        }
        return nHarmonizedElements;
    }
}

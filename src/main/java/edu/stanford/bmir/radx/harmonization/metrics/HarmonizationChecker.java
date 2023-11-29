package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashSet;

public class HarmonizationChecker {

    private HarmonizationRules harmonizationRules;

    public HarmonizationChecker(HarmonizationRules rules) {
        harmonizationRules = rules;
    }

    public int countHarmonizableElements(HashSet<String> elements) {
        return elements.size();
    }

    public int countHarmonizedElements(HashSet<String> elements) {
        return elements.size();
    }
}

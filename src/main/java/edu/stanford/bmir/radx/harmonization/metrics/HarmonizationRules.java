package edu.stanford.bmir.radx.harmonization.metrics;

public interface HarmonizationRules {
    boolean isHarmonized(String element);
    boolean isHarmonizable(String element);
}

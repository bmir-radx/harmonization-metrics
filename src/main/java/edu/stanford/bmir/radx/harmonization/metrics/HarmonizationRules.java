package edu.stanford.bmir.radx.harmonization.metrics;

public interface HarmonizationRules {
    boolean isHarmonized(String program, String element);
    boolean isHarmonizable(String program, String element);
}

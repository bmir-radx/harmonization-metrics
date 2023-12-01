package edu.stanford.bmir.radx.harmonization.metrics;

public interface HarmonizationRules {
    boolean isHarmonized(Program program, String element);
    boolean isHarmonizable(Program program, String element);
}

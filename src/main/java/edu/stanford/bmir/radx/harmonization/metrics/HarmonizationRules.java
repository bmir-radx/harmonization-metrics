package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Map;

public interface HarmonizationRules {
    boolean isHarmonized(Program program, String element);
    boolean isHarmonizable(Program program, String element);
}

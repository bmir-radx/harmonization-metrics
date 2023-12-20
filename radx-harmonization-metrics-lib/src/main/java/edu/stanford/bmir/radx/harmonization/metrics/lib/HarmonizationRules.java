package edu.stanford.bmir.radx.harmonization.metrics.lib;

public interface HarmonizationRules {
    boolean isHarmonized(ProgramId programId, String element, HarmonizationTier tier)
            throws InvalidProgramIdException, InvalidHarmonizationTierException;
    boolean isHarmonizable(ProgramId programId, String element, HarmonizationTier tier)
            throws InvalidProgramIdException, InvalidHarmonizationTierException;
}

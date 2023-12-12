package edu.stanford.bmir.radx.harmonization.metrics.lib;

public interface HarmonizationRules {
    boolean isHarmonized(ProgramId programId, String element) throws InvalidProgramIdException;
    boolean isHarmonizable(ProgramId programId, String element) throws InvalidProgramIdException;
}

package edu.stanford.bmir.radx.harmonization.metrics.lib;

public interface HarmonizationRules {
    boolean isHarmonized(ProgramIdentifier programIdentifier, String element) throws InvalidProgramIdentifierException;
    boolean isHarmonizable(ProgramIdentifier programIdentifier, String element) throws InvalidProgramIdentifierException;
}

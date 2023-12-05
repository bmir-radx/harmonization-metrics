package edu.stanford.bmir.radx.harmonization.metrics;

public interface HarmonizationRules {
    boolean isHarmonized(ProgramIdentifier programIdentifier, String element) throws InvalidProgramIdentifierException;
    boolean isHarmonizable(ProgramIdentifier programIdentifier, String element) throws InvalidProgramIdentifierException;
}

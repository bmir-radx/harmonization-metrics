package edu.stanford.bmir.radx.harmonization.metrics.lib;

public class InvalidHarmonizationTierException extends Exception {

    private final String harmonizationTier;

    public InvalidHarmonizationTierException(String tier) {
        this.harmonizationTier = tier;
    }

    @Override
    public String getMessage() {
        return String.format("%s is not a valid harmonization tier. Valid tiers: %s, %s, %s",
                harmonizationTier, HarmonizationTier.TIER1, HarmonizationTier.TIER2,
                HarmonizationTier.TIER3);
    }
}

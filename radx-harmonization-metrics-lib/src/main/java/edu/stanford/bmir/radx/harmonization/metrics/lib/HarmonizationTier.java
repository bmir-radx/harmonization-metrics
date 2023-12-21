package edu.stanford.bmir.radx.harmonization.metrics.lib;

public enum HarmonizationTier {
    TIER1("1"),
    TIER2("2"),
    TIER3("3");

    private final String tier;

    HarmonizationTier(String tier) {
        this.tier = tier;
    }

    public String toString() {
        return this.tier;
    }
}

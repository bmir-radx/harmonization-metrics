package edu.stanford.bmir.radx.harmonization.metrics.lib;

public record VariableSetMetrics(
    int nHarmonizableDataElementsTier1,
    int nHarmonizableDataElementsTier2,
    int nHarmonizableDataElementsTier3,
    int nHarmonizedDataElementsTier1,
    int nHarmonizedDataElementsTier2,
    int nHarmonizedDataElementsTier3,
    int nDataElements) {
}

package edu.stanford.bmir.radx.harmonization.metrics;

public record AggregateMetricsExternal(
        int nDataSets,
        int nHarmonizableDataSets,
        int nPartiallyHarmonizedDataSets,
        int nHarmonizedDataSets) {

    public static AggregateMetricsExternal createFromInternalMetrics(AggregateMetricsInternal internalMetrics) {
        return new AggregateMetricsExternal(internalMetrics.getTotalDataSets(),
                internalMetrics.getTotalHarmonizableDataSets(),
                internalMetrics.getTotalPartiallyHarmonizedDataSets(),
                internalMetrics.getTotalHarmonizedDataSets());
    }
}

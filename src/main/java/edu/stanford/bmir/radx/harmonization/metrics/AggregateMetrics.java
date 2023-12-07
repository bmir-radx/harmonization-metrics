package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.List;

public record AggregateMetrics(
    int nOrigTransfromFilePairs,
    int nHarmonizableOrigTransformFilePairs,
    int nPartiallyHarmonizedOrigTransformFilePairs,
    int nHarmonizedOrigTransformFilePairs,
    int nTriviallyHarmonizedOrigTransformFilePairs,
    int nMissedHarmonizableDataElements,
    int nHarmonizedDataElements,
    int nNonHarmonizableDataElements) {

    public static AggregateMetrics aggregateMetricsFromFilePairMetrics(
            List<OrigTransformFilePairMetrics> metricsList) {
        int nOrigTransfromFilePairs = metricsList.size();
        int nHarmonizableOrigTransformFilePairs = 0;
        int nPartiallyHarmonizedOrigTransformFilePairs = 0;
        int nHarmonizedOrigTransformFilePairs = 0;
        int nTriviallyHarmonizedOrigTransformFilePairs = 0;
        int nMissedHarmonizableDataElements = 0;
        int nHarmonizedDataElements = 0;
        int nNonHarmonizableDataElements = 0;

        for (OrigTransformFilePairMetrics metrics: metricsList) {
            if (metrics.isHarmonizable()) {
                nHarmonizableOrigTransformFilePairs++;
            }
            if (metrics.isPartiallyHarmonized()) {
                nPartiallyHarmonizedOrigTransformFilePairs++;
            }
            if (metrics.isHarmonized()) {
                nHarmonizedOrigTransformFilePairs++;
            }
            if (metrics.isTriviallyHarmonized()) {
                nTriviallyHarmonizedOrigTransformFilePairs++;
            }
            nMissedHarmonizableDataElements = nMissedHarmonizableDataElements + metrics.nMissedHarmonizableDataElements();
            nHarmonizedDataElements = nHarmonizedDataElements + metrics.nHarmonizedDataElements();
            nNonHarmonizableDataElements = nNonHarmonizableDataElements + metrics.nNonHarmonizableDataElements();
        }

        return new AggregateMetrics(
                nOrigTransfromFilePairs,
                nHarmonizableOrigTransformFilePairs,
                nPartiallyHarmonizedOrigTransformFilePairs,
                nHarmonizedOrigTransformFilePairs,
                nTriviallyHarmonizedOrigTransformFilePairs,
                nMissedHarmonizableDataElements,
                nHarmonizedDataElements,
                nNonHarmonizableDataElements);
    }
}

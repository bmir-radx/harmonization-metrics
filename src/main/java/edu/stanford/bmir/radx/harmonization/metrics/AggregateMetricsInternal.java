package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.List;

public record AggregateMetricsInternal(
    int numFilesOrig,
    int numFilesTransform,
    int numFilesMatched,
    int numHarmonizableOrig,
    int numPartiallyHarmonizedOrig,
    int numHarmonizedOrig,
    int numHarmonizableTransform,
    int numPartiallyHarmonizedTransform,
    int numHarmonizedTransform,
    int numHarmonizableMatched,
    int numPartiallyHarmonizedMatched,
    int numHarmonizedMatched) {

    public int getTotalDataSets() {
        return numFilesOrig + numFilesTransform - numFilesMatched;
    }

    public int getTotalHarmonizableDataSets() {
        return numHarmonizableOrig + numHarmonizableTransform + numHarmonizableMatched;
    }

    public int getTotalPartiallyHarmonizedDataSets() {
        return numPartiallyHarmonizedOrig + numPartiallyHarmonizedTransform + numPartiallyHarmonizedMatched;
    }

    public int getTotalHarmonizedDataSets() {
        return numHarmonizedOrig + numHarmonizedTransform + numHarmonizedMatched;
    }

    public static AggregateMetricsInternal aggregateMetricsFromDataSetMetrics(List<DataFilePairMetrics> metricsList) {
        int numFilesOrig = 0;
        int numFilesTransform = 0;
        int numFilesMatched = 0;
        int numHarmonizableOrig = 0;
        int numPartiallyHarmonizedOrig = 0;
        int numHarmonizedOrig = 0;
        int numHarmonizableTransform = 0;
        int numPartiallyHarmonizedTransform = 0;
        int numHarmonizedTransform = 0;
        int numHarmonizableMatched = 0;
        int numPartiallyHarmonizedMatched = 0;
        int numHarmonizedMatched = 0;

        for (var metrics: metricsList) {
            if (metrics.hasTransform() && metrics.hasOrig()) {
                numFilesOrig++;
                numFilesTransform++;
                numFilesMatched++;
                if (metrics.isHarmonizableTransform()) {
                    numHarmonizableMatched++;
                }
                if (metrics.isPartiallyHarmonizedTransform()) {
                    numPartiallyHarmonizedMatched++;
                }
                if (metrics.isHarmonizedTransform()) {
                    numHarmonizedMatched++;
                }
            } else if (metrics.hasTransform()) {
                numFilesTransform++;
                if (metrics.isHarmonizableTransform()) {
                    numHarmonizableTransform++;
                }
                if (metrics.isPartiallyHarmonizedTransform()) {
                    numPartiallyHarmonizedTransform++;
                }
                if (metrics.isHarmonizedTransform()) {
                    numHarmonizedTransform++;
                }
            } else if (metrics.hasOrig()) {
                numFilesOrig++;
                if (metrics.isHarmonizableOrig()) {
                    numHarmonizableOrig++;
                }
                if (metrics.isPartiallyHarmonizedOrig()) {
                    numPartiallyHarmonizedOrig++;
                }
                if (metrics.isHarmonizedOrig()) {
                    numHarmonizedOrig++;
                }
            }
        }
        return new AggregateMetricsInternal(
                numFilesOrig,
                numFilesTransform,
                numFilesMatched,
                numHarmonizableOrig,
                numPartiallyHarmonizedOrig,
                numHarmonizedOrig,
                numHarmonizableTransform,
                numPartiallyHarmonizedTransform,
                numHarmonizedTransform,
                numHarmonizableMatched,
                numPartiallyHarmonizedMatched,
                numHarmonizedMatched);
    }
}

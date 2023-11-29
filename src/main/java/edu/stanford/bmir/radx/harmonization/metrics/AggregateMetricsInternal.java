package edu.stanford.bmir.radx.harmonization.metrics;

public class AggregateMetricsInternal {

    private int nFilesOrig = 0;
    private int nFilesTransform = 0;
    private int nFilesMatched = 0;
    private int nHarmonizableOrig = 0;
    private int nPartiallyHarmonizedOrig = 0;
    private int nHarmonizedOrig = 0;
    private int nHarmonizableTransform = 0;
    private int nPartiallyHarmonizedTransform = 0;
    private int nHarmonizedTransform = 0;
    private int nHarmonizableMatched = 0;
    private int nPartiallyHarmonizedMatched = 0;
    private int nHarmonizedMatched = 0;

    public int getnFilesOrig() {
        return nFilesOrig;
    }

    public int getnFilesTransform() {
        return nFilesTransform;
    }

    public int getnFilesMatched() {
        return nFilesMatched;
    }

    public int getnHarmonizableOrig() {
        return nHarmonizableOrig;
    }

    public int getnPartiallyHarmonizedOrig() {
        return nPartiallyHarmonizedOrig;
    }

    public int getnHarmonizedOrig() {
        return nHarmonizedOrig;
    }

    public int getnHarmonizableTransform() {
        return nHarmonizableTransform;
    }

    public int getnPartiallyHarmonizedTransform() {
        return nPartiallyHarmonizedTransform;
    }

    public int getnHarmonizedTransform() {
        return nHarmonizedTransform;
    }

    public int getnHarmonizableMatched() {
        return nHarmonizableMatched;
    }

    public int getnPartiallyHarmonizedMatched() {
        return nPartiallyHarmonizedMatched;
    }

    public int getnHarmonizedMatched() {
        return nHarmonizedMatched;
    }

    public int getTotalDataSets() {
        return nFilesOrig + nFilesTransform - nFilesMatched;
    }

    public int getTotalHarmonizableDataSets() {
        return nHarmonizableOrig + nHarmonizableTransform + nHarmonizableMatched;
    }

    public int getTotalPartiallyHarmonizedDataSets() {
        return nPartiallyHarmonizedOrig + nPartiallyHarmonizedTransform + nPartiallyHarmonizedMatched;
    }

    public int getTotalHarmonizedDataSets() {
        return nHarmonizedOrig + nHarmonizedTransform + nHarmonizedMatched;
    }

    public void incrementCountsWithDataSetMetrics(DataSetMetrics dataSetMetrics) {
        if (dataSetMetrics.hasOrig() && dataSetMetrics.hasTransform()) {
            nFilesMatched++;
            nFilesTransform++;
            nFilesOrig++;
            if (dataSetMetrics.isHarmonizableTransform()) {
                nHarmonizableMatched++;
            } else if (dataSetMetrics.isPartiallyHarmonizedTransform()) {
                nPartiallyHarmonizedMatched++;
            } else if (dataSetMetrics.isHarmonizedTransform()) {
                nHarmonizedMatched++;
            }
        } else if (dataSetMetrics.hasTransform()) {
            nFilesTransform++;
            if (dataSetMetrics.isHarmonizableTransform()) {
                nHarmonizableTransform++;
            } else if (dataSetMetrics.isPartiallyHarmonizedTransform()) {
                nPartiallyHarmonizedTransform++;
            } else if (dataSetMetrics.isHarmonizedTransform()) {
                nHarmonizedTransform++;
            }
        } else if (dataSetMetrics.hasOrig()) {
            nFilesOrig++;
            if (dataSetMetrics.isHarmonizableOrig()) {
                nHarmonizableOrig++;
            } else if (dataSetMetrics.isPartiallyHarmonizedOrig()) {
                nPartiallyHarmonizedOrig++;
            } else if (dataSetMetrics.isHarmonizedOrig()) {
                nHarmonizedOrig++;
            }
        }
    }
}

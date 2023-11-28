package edu.stanford.bmir.radx.harmonization.metrics;

public class AggregateMetrics {
    private int nOrigFiles = 0;
    private int nUnmatchedOrigFiles = 0;
    private int nMisnamedOrigFiles = 0;
    private int nTransformFiles = 0;
    private int nUnmatechedTransformFiles = 0;
    private int nMisnamedTransformFiles = 0;
    private int nMatched = 0;
    private int nTotalFiles = 0;

    /*
    These counts are reported for studies that only have either the
    transformcopy or origcopy but not both.
     */
    private int nHarmonizableOrigFiles = 0;
    private int nHarmonizableTransformFiles = 0;
    private int nPartiallyHarmonizedTransformFiles = 0;
    private int nFullyHarmonizedTransformFiles = 0;

    /*
    Track metrics only for subset of studies with matched orig/transform copies
     */
    private int nHarmonizableMatched = 0;
    private int nPartiallyHarmonizedMatched = 0;
    private int nFullyHarmonizedMatched = 0;
}

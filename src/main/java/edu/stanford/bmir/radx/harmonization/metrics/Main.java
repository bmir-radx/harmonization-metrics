package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        // get harmonization rules from somewhere
        HarmonizationRules rules = new SimpleRules();

        // generate a checker from these rules
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        // crawl over the data hub to generate DataSet objects
        ArrayList<DataSet> dataSets = new ArrayList<>();

        // generate metrics on each data set
        // generate aggregate internal harmonization metrics from metrics per data set
        HashMap<String, DataSetMetrics> metrics = new HashMap<>();
        AggregateMetricsInternal internalMetrics = new AggregateMetricsInternal();
        for (DataSet dataSet: dataSets) {
            String name = dataSet.getName();
            DataSetMetrics dataSetMetrics = DataSetMetrics.createMetricsFromDataSet(dataSet, checker);
            metrics.put(name, dataSetMetrics);
            internalMetrics.incrementCountsWithDataSetMetrics(dataSetMetrics);
        }

        // produce final summary metrics
        AggregateMetricsExternal externalMetrics = AggregateMetricsExternal.createFromInternalMetrics(internalMetrics);
    }
}
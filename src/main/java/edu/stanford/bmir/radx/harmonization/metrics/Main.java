package edu.stanford.bmir.radx.harmonization.metrics;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws IOException, InvalidProgramException,
            InvalidDataFileCategoryException, NoVersionNumberException {
        // get harmonization rules from somewhere
        HarmonizationRules rules = new SimpleGlobalCodebookRules();

        // generate a checker from these rules
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        // crawl over the data hub to generate DataSet objects
        TrialDataProcessor trialDataFiles = new TrialDataProcessor();
        List<DataFileExternal> externalDataFiles = trialDataFiles.getDataFiles();

        DataFileProcessor processor = new DataFileProcessor();
        Map<ReducedFileName, DataFilePair> dataFilePairMap = processor.processDataFiles(externalDataFiles);

        // generate metrics on each data set
        // generate aggregate internal harmonization metrics from metrics per data set
        HashMap<ReducedFileName, DataSetMetrics> metrics = new HashMap<>();
        for (DataFilePair dataSet: dataFilePairMap.values()) {
            ReducedFileName name = dataSet.name();
            DataSetMetrics dataSetMetrics = DataSetMetrics.createMetricsFromDataSet(dataSet, checker);
            metrics.put(name, dataSetMetrics);
        }

        AggregateMetricsInternal internalMetrics = AggregateMetricsInternal.aggregateMetricsFromDataSetMetrics(new ArrayList<>(metrics.values()));
        System.out.println(internalMetrics);

        // produce final summary metrics
        AggregateMetricsExternal externalMetrics = AggregateMetricsExternal.createFromInternalMetrics(internalMetrics);
        System.out.println(externalMetrics);
    }
}
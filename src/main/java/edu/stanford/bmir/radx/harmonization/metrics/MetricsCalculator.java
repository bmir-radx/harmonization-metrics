package edu.stanford.bmir.radx.harmonization.metrics;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class MetricsCalculator {
    private final HarmonizationChecker harmonizationChecker; // this is set up to have rules injected
    private final DataFileProcessor dataFileProcessor; // component with no additional bean dependencies

    public MetricsCalculator(HarmonizationChecker harmonizationChecker,
                             DataFileProcessor dataFileProcessor) {
        this.harmonizationChecker = harmonizationChecker;
        this.dataFileProcessor = dataFileProcessor;
    }

    public AggregateMetricsInternal computeInternalHarmonizationMetrics(List<DataFileExternal> dataFiles)
            throws InvalidProgramException, InvalidDataFileCategoryException, NoVersionNumberException {
        Map<ReducedFileName, DataFilePair> dataFilePairMap = dataFileProcessor.processDataFiles(dataFiles);
        List<DataSetMetrics> metricsPerDataSet = new ArrayList<>();
        for (DataFilePair dataFilePair: dataFilePairMap.values()) {
            DataSetMetrics dataSetMetrics = DataSetMetrics.createMetricsFromDataSet(dataFilePair, harmonizationChecker);
            metricsPerDataSet.add(dataSetMetrics);
        }
        return AggregateMetricsInternal.aggregateMetricsFromDataSetMetrics(metricsPerDataSet);
    }

    public AggregateMetricsExternal computeHarmonizationMetrics(List<DataFileExternal> dataFiles)
            throws InvalidProgramException, InvalidDataFileCategoryException, NoVersionNumberException {
        AggregateMetricsInternal internalMetrics = computeInternalHarmonizationMetrics(dataFiles);
        return AggregateMetricsExternal.createFromInternalMetrics(internalMetrics);
    }
}

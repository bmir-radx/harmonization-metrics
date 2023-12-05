package edu.stanford.bmir.radx.harmonization.metrics;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
This is the main worker for this library. MetricsCalculator uses the
DataFileProcessor to process information about data files into
DataFilePair objects, which are then checked against the
HarmonizationChecker. Metrics regarding the extent of harmonization
are computed per DataFilePair and then aggregated for reporting.
 */
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
            throws InvalidProgramException, InvalidOrigTransformIdentifierException, NoVersionNumberException {
        Map<ReducedFileName, DataFilePair> dataFilePairMap = dataFileProcessor.processDataFiles(dataFiles);
        List<DataFilePairMetrics> metricsPerDataFilePair = new ArrayList<>();
        for (DataFilePair dataFilePair: dataFilePairMap.values()) {
            DataFilePairMetrics dataSetMetrics = DataFilePairMetrics.createMetricsFromDataSet(dataFilePair, harmonizationChecker);
            metricsPerDataFilePair.add(dataSetMetrics);
        }
        return AggregateMetricsInternal.aggregateMetricsFromDataSetMetrics(metricsPerDataFilePair);
    }

    public AggregateMetricsExternal computeHarmonizationMetrics(List<DataFileExternal> dataFiles)
            throws InvalidProgramException, InvalidOrigTransformIdentifierException, NoVersionNumberException {
        AggregateMetricsInternal internalMetrics = computeInternalHarmonizationMetrics(dataFiles);
        return AggregateMetricsExternal.createFromInternalMetrics(internalMetrics);
    }
}

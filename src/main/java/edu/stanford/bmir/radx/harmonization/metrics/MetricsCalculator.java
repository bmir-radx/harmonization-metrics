package edu.stanford.bmir.radx.harmonization.metrics;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
This is the main worker for this library. MetricsCalculator uses the
DataFileProcessor to process information about data files into
OrigTransformFilePair objects, which are then checked against the
HarmonizationChecker. Metrics regarding the extent of harmonization
are computed per OrigTransformFilePair and then aggregated for reporting.
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
            throws InvalidProgramIdentifierException, InvalidOrigTransformIdentifierException, NoVersionNumberException {
        Map<ReducedFileName, OrigTransformFilePair> dataFilePairMap = dataFileProcessor.processDataFiles(dataFiles);
        List<OrigTransformFilePairMetrics> metricsPerDataFilePair = new ArrayList<>();
        for (OrigTransformFilePair origTransformFilePair : dataFilePairMap.values()) {
            OrigTransformFilePairMetrics dataSetMetrics = OrigTransformFilePairMetrics.createMetricsFromDataSet(origTransformFilePair, harmonizationChecker);
            metricsPerDataFilePair.add(dataSetMetrics);
        }
        return AggregateMetricsInternal.aggregateMetricsFromDataSetMetrics(metricsPerDataFilePair);
    }

    public AggregateMetricsExternal computeHarmonizationMetrics(List<DataFileExternal> dataFiles)
            throws InvalidProgramIdentifierException, InvalidOrigTransformIdentifierException, NoVersionNumberException {
        AggregateMetricsInternal internalMetrics = computeInternalHarmonizationMetrics(dataFiles);
        return AggregateMetricsExternal.createFromInternalMetrics(internalMetrics);
    }
}

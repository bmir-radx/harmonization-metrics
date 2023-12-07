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
    private final DataFileProcessor dataFileProcessor; // component with no additional bean dependencies
    private final OrigTransformFilePairMetricsGenerator metricsGenerator; // has a harmonization checker as a dependency

    public MetricsCalculator(DataFileProcessor dataFileProcessor,
                             OrigTransformFilePairMetricsGenerator metricsGenerator) {
        this.dataFileProcessor = dataFileProcessor;
        this.metricsGenerator = metricsGenerator;
    }

    public AggregateMetrics computeHarmonizationMetrics(List<DataFileExternal> dataFiles)
            throws InvalidProgramIdentifierException, InvalidOrigTransformIdentifierException, NoVersionNumberException {
        Map<ReducedFileName, OrigTransformFilePair> filePairMap = dataFileProcessor.processDataFiles(dataFiles);
        List<OrigTransformFilePairMetrics> metricsPerFilePair = new ArrayList<>();
        for (OrigTransformFilePair origTransformFilePair : filePairMap.values()) {
            OrigTransformFilePairMetrics filePairMetrics = metricsGenerator.createMetricsFromFilePair(origTransformFilePair);
            metricsPerFilePair.add(filePairMetrics);
        }
        return AggregateMetrics.aggregateMetricsFromFilePairMetrics(metricsPerFilePair);
    }
}

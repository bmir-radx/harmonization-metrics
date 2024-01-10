package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*
This is the main worker for this library. MetricsCalculator uses the
DataFileProcessor to process information about data files into
OrigTransformFilePair objects. Metrics regarding the extent of harmonization
are generated for each OrigTransformFilePair and then aggregated for reporting.
 */
@Component
public class MetricsCalculator {
    private final DataFileProcessor dataFileProcessor; // component with no additional bean dependencies
    private final OrigTransformFilePairMetricsGenerator pairMetricsGenerator; // has a harmonization checker as a dependency
    private final StudyMetricsGenerator studyMetricsGenerator;

    public MetricsCalculator(DataFileProcessor dataFileProcessor,
                             OrigTransformFilePairMetricsGenerator pairMetricsGenerator,
                             StudyMetricsGenerator studyMetricsGenerator) {
        this.dataFileProcessor = dataFileProcessor;
        this.pairMetricsGenerator = pairMetricsGenerator;
        this.studyMetricsGenerator = studyMetricsGenerator;
    }

    public MetricsReport computeHarmonizationMetrics(List<DataFileExternal> dataFiles)
            throws InvalidProgramIdException, InvalidOrigTransformCategoryException,
            NoVersionNumberException, InvalidHarmonizationTierException {
        LocalDate date = LocalDate.now();
        // per file pair metrics
        Map<ReducedFileName, OrigTransformFilePair> filePairMap = dataFileProcessor.processDataFiles(dataFiles);
        List<OrigTransformFilePairMetrics> metricsPerFilePair = new ArrayList<>();
        for (var origTransformFilePair : filePairMap.values()) {
            var filePairMetrics = pairMetricsGenerator.createMetricsFromFilePair(origTransformFilePair);
            metricsPerFilePair.add(filePairMetrics);
        }

        // per study metrics
        Map<StudyId, Study> studyMap = dataFileProcessor.organizeFilePairsByStudy(filePairMap);
        List<StudyMetrics> studyMetrics = new ArrayList<>();
        for (var entry: studyMap.entrySet()) {
            Study study = entry.getValue();
            var metrics = studyMetricsGenerator.createMetricsFromStudy(study);
            studyMetrics.add(metrics);
        }

        return new MetricsReport(date, metricsPerFilePair, studyMetrics);
    }
}

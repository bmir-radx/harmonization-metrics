package edu.stanford.bmir.radx.harmonization.metrics.app;

import edu.stanford.bmir.radx.harmonization.metrics.lib.CsvWriter;
import edu.stanford.bmir.radx.harmonization.metrics.lib.DataFileExternal;
import edu.stanford.bmir.radx.harmonization.metrics.lib.MetricsCalculator;
import edu.stanford.bmir.radx.harmonization.metrics.lib.MetricsReport;
import edu.stanford.bmir.radx.harmonization.metrics.lib.TrialDataProcessor;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

/*
This implements a command line interface for the Harmonization Metrics Library.
 */
@Component
@CommandLine.Command(name = "calculate-metrics")
public class CalculateMetricsCommand implements Callable<Integer> {

    private final MetricsCalculator metricsCalculator;
    private final TrialDataProcessor trialDataProcessor;
    private final CsvWriter csvWriter;

    @CommandLine.Option(names = {"-f", "--file"}, required = true, description = "Path to trial data to read in.")
    protected String fileName;

    @CommandLine.Option(
            names = {"-os", "--output-studies"}, required = true,
            description = "Path of CSV to write study metrics.")
    protected String studyCsv;

    @CommandLine.Option(
            names = {"-op", "--output-pairs"}, required = true,
            description = "Path of CSV to write orig-transform file pair metrics.")
    protected String pairCsv;

    public CalculateMetricsCommand(MetricsCalculator metricsCalculator, TrialDataProcessor trialDataProcessor, CsvWriter csvWriter) {
        this.metricsCalculator = metricsCalculator;
        this.trialDataProcessor = trialDataProcessor;
        this.csvWriter = csvWriter;
    }

    @Override
    public Integer call() throws Exception {
        List<DataFileExternal> externalData = trialDataProcessor.readExternalData(fileName);
        MetricsReport metrics = metricsCalculator.computeHarmonizationMetrics(externalData);
        csvWriter.writeStudyReport(metrics.studyMetrics(), studyCsv);
        csvWriter.writeFilePairReport(metrics.pairMetrics(), pairCsv);
        return 0;
    }
}

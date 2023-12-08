package edu.stanford.bmir.radx.harmonization.metrics.app;

import edu.stanford.bmir.radx.harmonization.metrics.lib.AggregateMetrics;
import edu.stanford.bmir.radx.harmonization.metrics.lib.DataFileExternal;
import edu.stanford.bmir.radx.harmonization.metrics.lib.MetricsCalculator;
import edu.stanford.bmir.radx.harmonization.metrics.lib.TrialDataProcessor;
import org.springframework.stereotype.Component;
import picocli.CommandLine;

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

    @CommandLine.Option(names = {"-f", "--file"}, required = true, description = "Path to trial data to read in.")
    protected String fileName;

    public CalculateMetricsCommand(MetricsCalculator metricsCalculator, TrialDataProcessor trialDataProcessor) {
        this.metricsCalculator = metricsCalculator;
        this.trialDataProcessor = trialDataProcessor;
    }

    @Override
    public Integer call() throws Exception {
        List<DataFileExternal> externalData = trialDataProcessor.readExternalData(fileName);
        AggregateMetrics metrics = metricsCalculator.computeHarmonizationMetrics(externalData);
        System.out.println(metrics);
        return 0;
    }
}

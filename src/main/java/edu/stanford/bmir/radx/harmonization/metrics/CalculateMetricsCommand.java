package edu.stanford.bmir.radx.harmonization.metrics;

import org.springframework.stereotype.Component;
import picocli.CommandLine;

import java.util.List;
import java.util.concurrent.Callable;

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
        AggregateMetricsExternal metrics = metricsCalculator.computeHarmonizationMetrics(externalData);
        System.out.println(metrics);
        return 0;
    }
}

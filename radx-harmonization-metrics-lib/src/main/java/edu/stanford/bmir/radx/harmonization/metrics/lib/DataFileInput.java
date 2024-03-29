package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.List;

/*
This class contains the information expected for each data file
to have harmonization metrics computed. See MetricsCalculator.

Everything is a string here, but the library will convert values
into the relevant types once the data from the input is processed.
 */
public record DataFileInput(
        String fileName,
        String program,
        String studyId,
        String category,
        List<String> variableNames) {
}

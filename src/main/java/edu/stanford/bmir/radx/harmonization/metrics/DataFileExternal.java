package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.List;

/*
This class contains the information expected for each data file
to have harmonization metrics computed. See MetricsCalculator.

Everything is a string here, but the library will convert values
into the relevant types once the "external" data is processed.
 */
public record DataFileExternal(
        String fileName,
        String program,
        String studyId,
        String category,
        List<String> variableNames) {
}

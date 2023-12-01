package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.List;

/*
This is a data structure that holds information about a data file
the way that I think it will be sent to this library from the Data Hub.
This is not a functional part of the library.

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

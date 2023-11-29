package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashSet;
import java.util.Optional;

public record DataSetMetrics(
        String name,
        String program,
        String studyId,
        Optional<Integer> versionOrig,
        Optional<Integer> numDataElementsOrig,
        Optional<Integer> numHarmonizableDataElementsOrig,
        Optional<Integer> numHarmonizedDataElementsOrig,
        Optional<Integer> versionTransform,
        Optional<Integer> numDataElementsTransform,
        Optional<Integer> numHarmonizableDataElementsTransform,
        Optional<Integer> numHarmonizedDataElementsTransform) {

    public static DataSetMetrics createMetricsFromDataSet(DataSet dataSet, HarmonizationChecker harmonizationChecker) {
        String name = dataSet.getName();
        String program = dataSet.getProgram();
        String studyId = dataSet.getStudyId();
        Optional<DataFile> origData = dataSet.getOrigData();
        Optional<DataFile> transformData = dataSet.getTransformData();

        Optional<Integer> versionOrig;
        Optional<Integer> numDataElementsOrig;
        Optional<Integer> numHarmonizableDataElementsOrig;
        Optional<Integer> numHarmonizedDataElementsOrig;

        if (origData.isPresent()) {
            versionOrig = Optional.of(origData.get().getVersion());
            HashSet<String> variableNames = origData.get().getVariableNames();
            numDataElementsOrig = Optional.of(variableNames.size());
            numHarmonizableDataElementsOrig = Optional.of(harmonizationChecker.countHarmonizableElements(variableNames));
            numHarmonizedDataElementsOrig = Optional.of(harmonizationChecker.countHarmonizedElements(variableNames));
        } else {
            versionOrig = Optional.empty();
            numDataElementsOrig = Optional.empty();
            numHarmonizableDataElementsOrig = Optional.empty();
            numHarmonizedDataElementsOrig = Optional.empty();
        }

        Optional<Integer> versionTransform;
        Optional<Integer> numDataElementsTransform;
        Optional<Integer> numHarmonizableDataElementsTransform;
        Optional<Integer> numHarmonizedDataElementsTransform;

        if (transformData.isPresent()) {
            versionTransform = Optional.of(transformData.get().getVersion());
            HashSet<String> variableNames = transformData.get().getVariableNames();
            numDataElementsTransform = Optional.of(variableNames.size());
            numHarmonizableDataElementsTransform = Optional.of(harmonizationChecker.countHarmonizableElements(variableNames));
            numHarmonizedDataElementsTransform = Optional.of(harmonizationChecker.countHarmonizedElements(variableNames));
        } else {
            versionTransform = Optional.empty();
            numDataElementsTransform = Optional.empty();
            numHarmonizableDataElementsTransform = Optional.empty();
            numHarmonizedDataElementsTransform = Optional.empty();
        }

        return new DataSetMetrics(name, program, studyId,
                versionOrig, numDataElementsOrig, numHarmonizableDataElementsOrig,
                numHarmonizedDataElementsOrig, versionTransform, numDataElementsTransform,
                numHarmonizableDataElementsTransform, numHarmonizedDataElementsTransform);
    }
}
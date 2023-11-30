package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Optional;
import java.util.Set;

public record DataSetMetrics(
        String name,
        String program,
        String studyId,
        Optional<Integer> versionOrig,
        Optional<Integer> nDataElementsOrig,
        Optional<Integer> nHarmonizableDataElementsOrig,
        Optional<Integer> nHarmonizedDataElementsOrig,
        Optional<Integer> versionTransform,
        Optional<Integer> nDataElementsTransform,
        Optional<Integer> nHarmonizableDataElementsTransform,
        Optional<Integer> nHarmonizedDataElementsTransform) {

    public boolean hasOrig() {
        return versionOrig.isPresent();
    }

    public boolean hasTransform() {
        return versionTransform.isPresent();
    }

    public boolean hasHarmonizableElementsOrig() {
        return nHarmonizableDataElementsOrig.isPresent() && nHarmonizableDataElementsOrig.get() > 0;
    }

    public boolean hasNoHarmonizableElementsOrig() {
        return nHarmonizableDataElementsOrig.isPresent() && nHarmonizableDataElementsOrig.get() == 0;
    }

    public boolean hasHarmonizedElementsOrig() {
        return nHarmonizedDataElementsOrig.isPresent() && nHarmonizedDataElementsOrig.get() > 0;
    }

    public boolean isHarmonizableOrig() {
        return hasHarmonizableElementsOrig();
    }

    public boolean isPartiallyHarmonizedOrig() {
        return hasHarmonizableElementsOrig() && hasHarmonizedElementsOrig();
    }

    public boolean isHarmonizedOrig() {
        return hasNoHarmonizableElementsOrig();
    }

    public boolean hasHarmonizableElementsTransform() {
        return nHarmonizableDataElementsTransform.isPresent() && nHarmonizableDataElementsTransform.get() > 0;
    }

    public boolean hasNoHarmonizableElementsTransform() {
        return nHarmonizableDataElementsTransform.isPresent() && nHarmonizableDataElementsTransform.get() == 0;
    }

    public boolean hasHarmonizedElementsTransform() {
        return nHarmonizedDataElementsTransform.isPresent() && nHarmonizedDataElementsTransform.get() > 0;
    }

    public boolean isHarmonizableTransform() {
        return hasHarmonizableElementsTransform();
    }

    public boolean isPartiallyHarmonizedTransform() {
        return hasHarmonizableElementsTransform() && hasHarmonizedElementsTransform();
    }

    public boolean isHarmonizedTransform() {
        return hasNoHarmonizableElementsTransform();
    }

    public static DataSetMetrics createMetricsFromDataSet(DataSet dataSet, HarmonizationChecker harmonizationChecker) {
        String name = dataSet.getName();
        String program = dataSet.getProgram();
        String studyId = dataSet.getStudyId();
        Optional<DataFile> origData = dataSet.getOrigData();
        Optional<DataFile> transformData = dataSet.getTransformData();

        Optional<Integer> versionOrig;
        Optional<Integer> nDataElementsOrig;
        Optional<Integer> nHarmonizableDataElementsOrig;
        Optional<Integer> nHarmonizedDataElementsOrig;

        if (origData.isPresent()) {
            versionOrig = Optional.of(origData.get().getVersion());
            Set<String> variableNames = origData.get().getVariableNames();
            nDataElementsOrig = Optional.of(variableNames.size());
            nHarmonizableDataElementsOrig = Optional.of(harmonizationChecker.countHarmonizableElements(variableNames));
            nHarmonizedDataElementsOrig = Optional.of(harmonizationChecker.countHarmonizedElements(variableNames));
        } else {
            versionOrig = Optional.empty();
            nDataElementsOrig = Optional.empty();
            nHarmonizableDataElementsOrig = Optional.empty();
            nHarmonizedDataElementsOrig = Optional.empty();
        }

        Optional<Integer> versionTransform;
        Optional<Integer> nDataElementsTransform;
        Optional<Integer> nHarmonizableDataElementsTransform;
        Optional<Integer> nHarmonizedDataElementsTransform;

        if (transformData.isPresent()) {
            versionTransform = Optional.of(transformData.get().getVersion());
            Set<String> variableNames = transformData.get().getVariableNames();
            nDataElementsTransform = Optional.of(variableNames.size());
            nHarmonizableDataElementsTransform = Optional.of(harmonizationChecker.countHarmonizableElements(variableNames));
            nHarmonizedDataElementsTransform = Optional.of(harmonizationChecker.countHarmonizedElements(variableNames));
        } else {
            versionTransform = Optional.empty();
            nDataElementsTransform = Optional.empty();
            nHarmonizableDataElementsTransform = Optional.empty();
            nHarmonizedDataElementsTransform = Optional.empty();
        }

        return new DataSetMetrics(name, program, studyId,
                versionOrig, nDataElementsOrig, nHarmonizableDataElementsOrig,
                nHarmonizedDataElementsOrig, versionTransform, nDataElementsTransform,
                nHarmonizableDataElementsTransform, nHarmonizedDataElementsTransform);
    }
}
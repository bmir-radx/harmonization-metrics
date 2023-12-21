package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/*
Helper class to generate one OrigTransformFilePairMetrics
object for a given OrigTransformFilePair.
 */
@Component
public class StudyMetricsGenerator extends InternalMetricsGenerator {

    public StudyMetricsGenerator(HarmonizationChecker harmonizationChecker) {
        super(harmonizationChecker);
    }

    private void updateUniqueVariables(
            OrigTransformFilePair pair,
            Set<String> uniqueVariablesOrig,
            Set<String> uniqueVariablesTransform) {
        Optional<OrigFile> origData = pair.origFile();
        Optional<TransformFile> transformData = pair.transformFile();
        origData.ifPresent(orig -> uniqueVariablesOrig.addAll(orig.variableNames()));
        transformData.ifPresent(transform -> uniqueVariablesTransform.addAll(transform.variableNames()));
    }

    public StudyMetrics createMetricsFromStudy(Study study)
            throws InvalidHarmonizationTierException, InvalidProgramIdException {
        StudyId studyId = study.studyId();
        ProgramId programId = study.programId();

        Set<String> uniqueVariablesOrig = new HashSet<>();
        Set<String> uniqueVariablesTransform = new HashSet<>();
        int nPairs = study.origTransformFilePairList().size();
        for (var pair: study.origTransformFilePairList()) {
            updateUniqueVariables(pair, uniqueVariablesOrig, uniqueVariablesTransform);
        }

        VariableSetMetrics metricsOrig = generateVariableSetMetrics(
                programId, uniqueVariablesOrig);
        VariableSetMetrics metricsTransform = generateVariableSetMetrics(
                programId, uniqueVariablesTransform);


        int nUniqueDataElements;
        int nUniqueHarmonizableDataElementsTier1;
        int nUniqueHarmonizableDataElementsTier2;
        int nUniqueHarmonizableDataElementsTier3;
        int nUniqueHarmonizedDataElementsTier1;
        int nUniqueHarmonizedDataElementsTier2;
        int nUniqueHarmonizedDataElementsTier3;

        // there can be discrepancies in the number of data elements
        // between the two files in the pair, so pick the larger one
        // metrics for harmonizable data elements should be the same
        // (or as close to identical as possible), but metrics for
        // the harmonized data elements can vary. If the transform
        // file exists, this number should be higher. If it does not,
        // an origcopy can still contain some harmonized data elements,
        nUniqueDataElements = Math.max(
                metricsOrig.nDataElements(), metricsTransform.nDataElements());
        nUniqueHarmonizableDataElementsTier1 = Math.max(
                metricsOrig.nHarmonizableDataElementsTier1(),
                metricsTransform.nHarmonizableDataElementsTier1());
        nUniqueHarmonizableDataElementsTier2 = Math.max(
                metricsOrig.nHarmonizableDataElementsTier2(),
                metricsTransform.nHarmonizableDataElementsTier2());
        nUniqueHarmonizableDataElementsTier3 = Math.max(
                metricsOrig.nHarmonizableDataElementsTier3(),
                metricsTransform.nHarmonizableDataElementsTier3());
        nUniqueHarmonizedDataElementsTier1 = Math.max(
                metricsOrig.nHarmonizedDataElementsTier1(),
                metricsTransform.nHarmonizedDataElementsTier1());
        nUniqueHarmonizedDataElementsTier2 = Math.max(
                metricsOrig.nHarmonizedDataElementsTier2(),
                metricsTransform.nHarmonizedDataElementsTier2());
        nUniqueHarmonizedDataElementsTier3 = Math.max(
                metricsOrig.nHarmonizedDataElementsTier3(),
                metricsTransform.nHarmonizedDataElementsTier3());

        return new StudyMetrics(
                studyId,
                programId,
                nPairs,
                nUniqueDataElements,
                nUniqueHarmonizableDataElementsTier1,
                nUniqueHarmonizableDataElementsTier2,
                nUniqueHarmonizableDataElementsTier3,
                nUniqueHarmonizedDataElementsTier1,
                nUniqueHarmonizedDataElementsTier2,
                nUniqueHarmonizedDataElementsTier3);
    }
}

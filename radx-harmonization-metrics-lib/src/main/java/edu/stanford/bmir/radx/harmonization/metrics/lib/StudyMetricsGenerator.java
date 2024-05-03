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

        HarmonizedVariableSets origVariables = filterVariablesByHarmonization(
                programId, uniqueVariablesOrig);
        HarmonizedVariableSets transformVariables = filterVariablesByHarmonization(
                programId, uniqueVariablesTransform);


        // there can be discrepancies in the number of data elements
        // between the two files in the pair, so pick the larger one
        // metrics for harmonizable data elements should be the same
        // (or as close to identical as possible), but metrics for
        // the harmonized data elements can vary. If the transform
        // file exists, this number should be higher. If it does not,
        // an origcopy can still contain some harmonized data elements,
        int nUniqueDataElements = Math.max(
                origVariables.dataElements().size(),
                transformVariables.dataElements().size());

        Set<String> harmonizableDataElementsTier1 = new HashSet<>();
        harmonizableDataElementsTier1.addAll(origVariables.harmonizableDataElementsTier1());
        harmonizableDataElementsTier1.addAll(transformVariables.harmonizableDataElementsTier1());
        int nUniqueHarmonizableDataElementsTier1 = harmonizableDataElementsTier1.size();

        Set<String> harmonizableDataElementsTier2 = new HashSet<>();
        harmonizableDataElementsTier2.addAll(origVariables.harmonizableDataElementsTier2());
        harmonizableDataElementsTier2.addAll(transformVariables.harmonizableDataElementsTier2());
        int nUniqueHarmonizableDataElementsTier2 = harmonizableDataElementsTier2.size();

        Set<String> harmonizableDataElementsTier3 = new HashSet<>();
        harmonizableDataElementsTier3.addAll(origVariables.harmonizableDataElementsTier3());
        harmonizableDataElementsTier3.addAll(transformVariables.harmonizableDataElementsTier3());
        int nUniqueHarmonizableDataElementsTier3 = harmonizableDataElementsTier3.size();

        Set<String> harmonizedDataElementsTier1 = new HashSet<>();
        harmonizedDataElementsTier1.addAll(origVariables.harmonizedDataElementsTier1());
        harmonizedDataElementsTier1.addAll(transformVariables.harmonizedDataElementsTier1());
        int nUniqueHarmonizedDataElementsTier1 = harmonizedDataElementsTier1.size();

        Set<String> harmonizedDataElementsTier2 = new HashSet<>();
        harmonizedDataElementsTier2.addAll(origVariables.harmonizedDataElementsTier2());
        harmonizedDataElementsTier2.addAll(transformVariables.harmonizedDataElementsTier2());
        int nUniqueHarmonizedDataElementsTier2 = harmonizedDataElementsTier2.size();

        Set<String> harmonizedDataElementsTier3 = new HashSet<>();
        harmonizedDataElementsTier3.addAll(origVariables.harmonizedDataElementsTier3());
        harmonizedDataElementsTier3.addAll(transformVariables.harmonizedDataElementsTier3());
        int nUniqueHarmonizedDataElementsTier3 = harmonizedDataElementsTier3.size();

        Set<String> harmonizableDataElements = new HashSet<>();
        harmonizableDataElements.addAll(harmonizableDataElementsTier1);
        harmonizableDataElements.addAll(harmonizableDataElementsTier2);
        harmonizableDataElements.addAll(harmonizableDataElementsTier3);
        int totalHarmonizable = harmonizableDataElements.size();
        Set<String> harmonizedDataElements = new HashSet<>();
        harmonizedDataElements.addAll(harmonizedDataElementsTier1);
        harmonizedDataElements.addAll(harmonizedDataElementsTier2);
        harmonizedDataElements.addAll(harmonizedDataElementsTier3);
        int totalHarmonized = harmonizedDataElements.size();

        return new StudyMetrics(
                studyId,
                programId,
                nPairs,
                nUniqueDataElements,
                nUniqueHarmonizableDataElementsTier1,
                nUniqueHarmonizableDataElementsTier2,
                nUniqueHarmonizableDataElementsTier3,
                totalHarmonizable,
                nUniqueHarmonizedDataElementsTier1,
                nUniqueHarmonizedDataElementsTier2,
                nUniqueHarmonizedDataElementsTier3,
                totalHarmonized,
                harmonizableDataElementsTier1,
                harmonizableDataElementsTier2,
                harmonizableDataElementsTier3,
                harmonizableDataElements,
                harmonizedDataElementsTier1,
                harmonizedDataElementsTier2,
                harmonizedDataElementsTier3,
                harmonizedDataElements);
    }
}

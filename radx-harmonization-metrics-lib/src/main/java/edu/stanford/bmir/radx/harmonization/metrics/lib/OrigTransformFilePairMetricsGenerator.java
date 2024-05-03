package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

/*
Helper class to generate one OrigTransformFilePairMetrics
object for a given OrigTransformFilePair.
 */
@Component
public class OrigTransformFilePairMetricsGenerator extends InternalMetricsGenerator {

    public OrigTransformFilePairMetricsGenerator(HarmonizationChecker harmonizationChecker) {
        super(harmonizationChecker);
    }

    public OrigTransformFilePairMetrics createMetricsFromFilePair(OrigTransformFilePair filePair)
            throws InvalidProgramIdException, InvalidHarmonizationTierException {
        ReducedFileName pairName = filePair.pairName();
        ProgramId programId = filePair.programId();
        StudyId studyId = filePair.studyId();
        Optional<OrigFile> origData = filePair.origFile();
        Optional<TransformFile> transformData = filePair.transformFile();

        Optional<String> origFileName;
        Optional<HarmonizedVariableSets> origVariables;
        if (origData.isPresent()) {
            origFileName = Optional.of(origData.get().fileName());
            Set<String> variableNames = origData.get().variableNames();
            origVariables = Optional.of(filterVariablesByHarmonization(
                    programId, variableNames));
        } else {
            origFileName = Optional.empty();
            origVariables = Optional.empty();
        }

        Optional<String> transformFileName;
        Optional<HarmonizedVariableSets> transformVariables;
        if (transformData.isPresent()) {
            transformFileName = Optional.of(transformData.get().fileName());
            Set<String> variableNames = transformData.get().variableNames();
            transformVariables = Optional.of(filterVariablesByHarmonization(
                    programId, variableNames));
        } else {
            transformFileName = Optional.empty();
            transformVariables = Optional.empty();
        }

        Set<String> dataElementsOrig;
        Set<String> dataElementsTransform;
        Set<String> harmonizableDataElementsTier1;
        Set<String> harmonizableDataElementsTier2;
        Set<String> harmonizableDataElementsTier3;
        Set<String> harmonizedDataElementsTier1;
        Set<String> harmonizedDataElementsTier2;
        Set<String> harmonizedDataElementsTier3;
        if (origVariables.isPresent() && transformVariables.isPresent()) {
            dataElementsOrig = origVariables.get().dataElements();
            dataElementsTransform = transformVariables.get().dataElements();
            harmonizableDataElementsTier1 = origVariables.get().harmonizableDataElementsTier1();
            harmonizableDataElementsTier2 = origVariables.get().harmonizableDataElementsTier2();
            harmonizableDataElementsTier3 = origVariables.get().harmonizableDataElementsTier3();
            harmonizedDataElementsTier1 = transformVariables.get().harmonizedDataElementsTier1();
            harmonizedDataElementsTier2 = transformVariables.get().harmonizedDataElementsTier2();
            harmonizedDataElementsTier3 = transformVariables.get().harmonizedDataElementsTier3();
        } else if (origVariables.isPresent()) {
            dataElementsOrig = origVariables.get().dataElements();
            dataElementsTransform = new HashSet<>();
            harmonizableDataElementsTier1 = origVariables.get().harmonizableDataElementsTier1();
            harmonizableDataElementsTier2 = origVariables.get().harmonizableDataElementsTier2();
            harmonizableDataElementsTier3 = origVariables.get().harmonizableDataElementsTier3();
            harmonizedDataElementsTier1 = origVariables.get().harmonizedDataElementsTier1();
            harmonizedDataElementsTier2 = origVariables.get().harmonizedDataElementsTier2();
            harmonizedDataElementsTier3 = origVariables.get().harmonizedDataElementsTier3();
        } else { // only have transform metrics.
            dataElementsOrig = new HashSet<>();
            dataElementsTransform = transformVariables.get().dataElements();
            harmonizableDataElementsTier1 = transformVariables.get().harmonizableDataElementsTier1();
            harmonizableDataElementsTier2 = transformVariables.get().harmonizableDataElementsTier2();
            harmonizableDataElementsTier3 = transformVariables.get().harmonizableDataElementsTier3();
            harmonizedDataElementsTier1 = transformVariables.get().harmonizedDataElementsTier1();
            harmonizedDataElementsTier2 = transformVariables.get().harmonizedDataElementsTier2();
            harmonizedDataElementsTier3 = transformVariables.get().harmonizedDataElementsTier3();
        }

        Set<String> harmonizableDataElements = new HashSet<>();
        harmonizableDataElements.addAll(harmonizableDataElementsTier1);
        harmonizableDataElements.addAll(harmonizableDataElementsTier2);
        harmonizableDataElements.addAll(harmonizableDataElementsTier3);
        Set<String> harmonizedDataElements = new HashSet<>();
        harmonizedDataElements.addAll(harmonizedDataElementsTier1);
        harmonizedDataElements.addAll(harmonizedDataElementsTier2);
        harmonizedDataElements.addAll(harmonizedDataElementsTier3);

        int nDataElementsOrig = dataElementsOrig.size();
        int nDataElementsTransform = dataElementsTransform.size();
        int nHarmonizableDataElementsTier1 = harmonizableDataElementsTier1.size();
        int nHarmonizableDataElementsTier2 = harmonizableDataElementsTier2.size();
        int nHarmonizableDataElementsTier3 = harmonizableDataElementsTier3.size();
        int nHarmonizedDataElementsTier1 = harmonizedDataElementsTier1.size();
        int nHarmonizedDataElementsTier2 = harmonizedDataElementsTier2.size();
        int nHarmonizedDataElementsTier3 = harmonizedDataElementsTier3.size();
        int totalHarmonizable = harmonizableDataElements.size();
        int totalHarmonized = harmonizedDataElements.size();

        return new OrigTransformFilePairMetrics(
                pairName,
                programId,
                studyId,
                origFileName,
                transformFileName,
                nDataElementsOrig,
                nDataElementsTransform,
                nHarmonizableDataElementsTier1,
                nHarmonizableDataElementsTier2,
                nHarmonizableDataElementsTier3,
                totalHarmonizable,
                nHarmonizedDataElementsTier1,
                nHarmonizedDataElementsTier2,
                nHarmonizedDataElementsTier3,
                totalHarmonized,
                dataElementsOrig,
                dataElementsTransform,
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

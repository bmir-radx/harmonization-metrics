package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

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
        Optional<VariableSetMetrics> origMetrics;
        if (origData.isPresent()) {
            origFileName = Optional.of(origData.get().fileName());
            Set<String> variableNames = origData.get().variableNames();
            origMetrics = Optional.of(generateVariableSetMetrics(
                    programId, variableNames));
        } else {
            origFileName = Optional.empty();
            origMetrics = Optional.empty();
        }

        Optional<String> transformFileName;
        Optional<VariableSetMetrics> transformMetrics;
        if (transformData.isPresent()) {
            transformFileName = Optional.of(transformData.get().fileName());
            Set<String> variableNames = transformData.get().variableNames();
            transformMetrics = Optional.of(generateVariableSetMetrics(
                    programId, variableNames));
        } else {
            transformFileName = Optional.empty();
            transformMetrics = Optional.empty();
        }

        int nDataElementsOrig;
        int nDataElementsTransform;
        int nHarmonizableDataElementsTier1;
        int nHarmonizableDataElementsTier2;
        int nHarmonizableDataElementsTier3;
        int nHarmonizedDataElementsTier1;
        int nHarmonizedDataElementsTier2;
        int nHarmonizedDataElementsTier3;
        if (origMetrics.isPresent() && transformMetrics.isPresent()) {
            nDataElementsOrig = origMetrics.get().nDataElements();
            nDataElementsTransform = transformMetrics.get().nDataElements();
            nHarmonizableDataElementsTier1 = Math.max(
                    origMetrics.get().nHarmonizableDataElementsTier1(),
                    transformMetrics.get().nHarmonizableDataElementsTier1());
            nHarmonizableDataElementsTier2 = Math.max(
                    origMetrics.get().nHarmonizableDataElementsTier2(),
                    transformMetrics.get().nHarmonizableDataElementsTier2());
            nHarmonizableDataElementsTier3 = Math.max(
                    origMetrics.get().nHarmonizableDataElementsTier3(),
                    transformMetrics.get().nHarmonizableDataElementsTier3());
            nHarmonizedDataElementsTier1 = Math.max(
                    origMetrics.get().nHarmonizedDataElementsTier1(),
                    transformMetrics.get().nHarmonizedDataElementsTier1());
            nHarmonizedDataElementsTier2 = Math.max(
                    origMetrics.get().nHarmonizedDataElementsTier2(),
                    transformMetrics.get().nHarmonizedDataElementsTier2());
            nHarmonizedDataElementsTier3 = Math.max(
                    origMetrics.get().nHarmonizedDataElementsTier3(),
                    transformMetrics.get().nHarmonizedDataElementsTier3());
        } else if (origMetrics.isPresent()) {
            nDataElementsOrig = origMetrics.get().nDataElements();
            nDataElementsTransform = 0;
            nHarmonizableDataElementsTier1 = origMetrics.get().nHarmonizableDataElementsTier1();
            nHarmonizableDataElementsTier2 = origMetrics.get().nHarmonizableDataElementsTier2();
            nHarmonizableDataElementsTier3 = origMetrics.get().nHarmonizableDataElementsTier3();
            nHarmonizedDataElementsTier1 = origMetrics.get().nHarmonizedDataElementsTier1();
            nHarmonizedDataElementsTier2 = origMetrics.get().nHarmonizedDataElementsTier2();
            nHarmonizedDataElementsTier3 = origMetrics.get().nHarmonizedDataElementsTier3();
        } else { // only have transform metrics.
            nDataElementsOrig = 0;
            nDataElementsTransform = transformMetrics.get().nDataElements();
            nHarmonizableDataElementsTier1 = transformMetrics.get().nHarmonizableDataElementsTier1();
            nHarmonizableDataElementsTier2 = transformMetrics.get().nHarmonizableDataElementsTier2();
            nHarmonizableDataElementsTier3 = transformMetrics.get().nHarmonizableDataElementsTier3();
            nHarmonizedDataElementsTier1 = transformMetrics.get().nHarmonizedDataElementsTier1();
            nHarmonizedDataElementsTier2 = transformMetrics.get().nHarmonizedDataElementsTier2();
            nHarmonizedDataElementsTier3 = transformMetrics.get().nHarmonizedDataElementsTier3();
        }

        Double percentHarmonizable = ((double) (nHarmonizableDataElementsTier1
                + nHarmonizableDataElementsTier2
                + nHarmonizableDataElementsTier3)
                / Math.max(nDataElementsOrig, nDataElementsTransform));
        Double percentHarmonized = ((double) (nHarmonizedDataElementsTier1
                + nHarmonizedDataElementsTier2
                + nHarmonizedDataElementsTier3)
                / Math.max(nDataElementsOrig, nDataElementsTransform));

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
                percentHarmonizable,
                nHarmonizedDataElementsTier1,
                nHarmonizedDataElementsTier2,
                nHarmonizedDataElementsTier3,
                percentHarmonized);
    }
}

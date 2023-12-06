package edu.stanford.bmir.radx.harmonization.metrics;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class OrigTransformFilePairMetricsGenerator {

    private final HarmonizationChecker harmonizationChecker;

    public OrigTransformFilePairMetricsGenerator(HarmonizationChecker harmonizationChecker) {
        this.harmonizationChecker = harmonizationChecker;
    }

    public OrigTransformFilePairMetrics createMetricsFromFilePair(OrigTransformFilePair filePair)
            throws InvalidProgramIdentifierException {
        ReducedFileName pairName = filePair.pairName();
        ProgramIdentifier programIdentifier = filePair.programIdentifier();
        StudyId studyId = filePair.studyId();
        Optional<OrigFile> origData = filePair.origFile();
        Optional<TransformFile> transformData = filePair.transformFile();

        Optional<Integer> versionOrig;
        Optional<Integer> nDataElementsOrig;
        Optional<Integer> nHarmonizableDataElementsOrig;
        Optional<Integer> nHarmonizedDataElementsOrig;

        if (origData.isPresent()) {
            versionOrig = Optional.of(origData.get().version());
            Set<String> variableNames = origData.get().variableNames();
            nDataElementsOrig = Optional.of(variableNames.size());
            nHarmonizableDataElementsOrig = Optional.of(harmonizationChecker.countHarmonizableElements(programIdentifier, variableNames));
            nHarmonizedDataElementsOrig = Optional.of(harmonizationChecker.countHarmonizedElements(programIdentifier, variableNames));
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
            versionTransform = Optional.of(transformData.get().version());
            Set<String> variableNames = transformData.get().variableNames();
            nDataElementsTransform = Optional.of(variableNames.size());
            nHarmonizableDataElementsTransform = Optional.of(harmonizationChecker.countHarmonizableElements(programIdentifier, variableNames));
            nHarmonizedDataElementsTransform = Optional.of(harmonizationChecker.countHarmonizedElements(programIdentifier, variableNames));
        } else {
            versionTransform = Optional.empty();
            nDataElementsTransform = Optional.empty();
            nHarmonizableDataElementsTransform = Optional.empty();
            nHarmonizedDataElementsTransform = Optional.empty();
        }

        Integer nHarmonizableDataElements;
        Integer nHarmonizedDataElements;
        Integer nNonHarmonizableDataElements;
        if (transformData.isPresent()) {
            // this includes the case in which only the transformcopy exists
            // and the case in which both the transformcopy and origcopy exist
            nHarmonizableDataElements = nHarmonizableDataElementsTransform.get();
            nHarmonizedDataElements = nHarmonizedDataElementsTransform.get();
            nNonHarmonizableDataElements = nDataElementsTransform.get() - nHarmonizableDataElements - nHarmonizedDataElements;
        } else {
            // this covers the case in which only the origcopy exists, since
            // it is impossible for an OrigTransformFilePair to exist without
            // at least one of the origcopy or transformcopy
            nHarmonizableDataElements = nHarmonizableDataElementsOrig.get();
            nHarmonizedDataElements = nHarmonizedDataElementsOrig.get();
            nNonHarmonizableDataElements = nDataElementsOrig.get() - nHarmonizableDataElements - nHarmonizedDataElements;
        }

        return new OrigTransformFilePairMetrics(pairName, programIdentifier, studyId,
                versionOrig, nDataElementsOrig, nHarmonizableDataElementsOrig,
                nHarmonizedDataElementsOrig, versionTransform, nDataElementsTransform,
                nHarmonizableDataElementsTransform, nHarmonizedDataElementsTransform,
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
    }
}

package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Optional;

public record OrigTransformFilePair(
        ReducedFileName pairName,
        ProgramIdentifier programIdentifier,
        StudyId studyId,
        Optional<OrigFile> origFile,
        Optional<TransformFile> transformFile) {

    public OrigTransformFilePair(ReducedFileName name, ProgramIdentifier programIdentifier, StudyId studyId) {
        this(name, programIdentifier, studyId, Optional.empty(), Optional.empty());
    }

    /*
    Keep data from origcopy if it is newer than the current data.
     */
    public OrigTransformFilePair updateOrigData(OrigFile newOrigData) {
        if (origFile.isEmpty() ||
                (origFile.get().version() < newOrigData.version())) {
            return new OrigTransformFilePair(pairName, programIdentifier, studyId,
                    Optional.of(newOrigData), transformFile);
        } else {
            return this;
        }
    }

    /*
    Keep data from transformcopy if it is newer than the current data.
     */
    public OrigTransformFilePair updateTransformData(TransformFile newTransformData) {
        if (transformFile.isEmpty() ||
                (transformFile.get().version() < newTransformData.version())) {
            return new OrigTransformFilePair(pairName, programIdentifier, studyId,
                    origFile, Optional.of(newTransformData));
        } else {
            return this;
        }
    }
}

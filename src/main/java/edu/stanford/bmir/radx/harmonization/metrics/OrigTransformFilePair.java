package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Optional;

public record OrigTransformFilePair(
        ReducedFileName name,
        ProgramIdentifier programIdentifier,
        StudyId studyId,
        Optional<DataFile> origData,
        Optional<DataFile> transformData) {

    public OrigTransformFilePair(ReducedFileName name, ProgramIdentifier programIdentifier, StudyId studyId) {
        this(name, programIdentifier, studyId, Optional.empty(), Optional.empty());
    }

    /*
    Keep data from origcopy if it is newer than the current data.
     */
    public OrigTransformFilePair updateOrigData(OrigFile newOrigData) {
        if (origData.isEmpty() ||
                (origData.get().version() < newOrigData.version())) {
            return new OrigTransformFilePair(name, programIdentifier, studyId,
                    Optional.of(newOrigData), transformData);
        } else {
            return this;
        }
    }

    /*
    Keep data from transformcopy if it is newer than the current data.
     */
    public OrigTransformFilePair updateTransformData(TransformFile newTransformData) {
        if (transformData.isEmpty() ||
                (transformData.get().version() < newTransformData.version())) {
            return new OrigTransformFilePair(name, programIdentifier, studyId,
                    origData, Optional.of(newTransformData));
        } else {
            return this;
        }
    }
}

package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.Optional;

/*
This class pairs an origFile with its corresponding transformFile
if possible. It is possible that one of the pair may not exist.
 */
public record OrigTransformFilePair(
        ReducedFileName pairName,
        ProgramId programId,
        StudyId studyId,
        Optional<OrigFile> origFile,
        Optional<TransformFile> transformFile) {

    public OrigTransformFilePair(ReducedFileName name, ProgramId programId, StudyId studyId) {
        this(name, programId, studyId, Optional.empty(), Optional.empty());
    }

    /*
    Keep data from origcopy if it is newer than the current data.
     */
    public OrigTransformFilePair updateOrigData(OrigFile newOrigData) {
        if (origFile.isEmpty() ||
                (origFile.get().version() < newOrigData.version())) {
            return new OrigTransformFilePair(pairName, programId, studyId,
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
            return new OrigTransformFilePair(pairName, programId, studyId,
                    origFile, Optional.of(newTransformData));
        } else {
            return this;
        }
    }
}

package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Optional;

public record DataFilePair(
        String name,
        Program program,
        StudyId studyId,
        Optional<DataFile> origData,
        Optional<DataFile> transformData) {

    public DataFilePair(String name, Program program, StudyId studyId) {
        this(name, program, studyId, Optional.empty(), Optional.empty());
    }

    /*
    Keep data from origcopy if it is newer than the current data.
     */
    public DataFilePair updateOrigData(DataFile newOrigData) {
        if (origData.isEmpty() ||
                (origData.get().version() < newOrigData.version())) {
            return new DataFilePair(name, program, studyId,
                    Optional.of(newOrigData), transformData);
        } else {
            return this;
        }
    }

    /*
    Keep data from transformcopy if it is newer than the current data.
     */
    public DataFilePair updateTransformData(DataFile newTransformData) {
        if (transformData.isEmpty() ||
                (transformData.get().version() < newTransformData.version())) {
            return new DataFilePair(name, program, studyId,
                    origData, Optional.of(newTransformData));
        } else {
            return this;
        }
    }
}

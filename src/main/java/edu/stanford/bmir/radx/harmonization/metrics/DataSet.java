package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Optional;

public class DataSet {
    private String name;
    private Program program;
    private StudyId studyId;
    private Optional<DataFile> transformData;
    private Optional<DataFile> origData;

    public DataSet(String name, Program program, StudyId studyId) {
        this(name, program, studyId, Optional.empty(), Optional.empty());
    }

    public DataSet(String name, String program, String studyId) {
        this(name, Program.fromString(program), StudyId.valueOf(studyId),
                Optional.empty(), Optional.empty());
    }

    public DataSet(String name, Program program, StudyId studyId,
                   Optional<DataFile> transformData,
                   Optional<DataFile> origData) {
        this.name = name;
        this.program = program;
        this.studyId = studyId;
        this.transformData = transformData;
        this.origData = origData;
    }

    public String getName() {
        return name;
    }

    public Program getProgram() {
        return program;
    }

    public StudyId getStudyId() {
        return studyId;
    }

    public Optional<DataFile> getOrigData() {
        return origData;
    }

    public Optional<DataFile> getTransformData() {
        return transformData;
    }

    /*
    Keep data from transformcopy if it is newer than the current data.
     */
    public void setTransformData(DataFile newTransformData) {
        if (transformData.isEmpty() ||
                (transformData.get().getVersion() < newTransformData.getVersion())) {
            transformData = Optional.of(newTransformData);
        }
    }

    /*
    Keep data from origcopy if it is newer than the current data.
     */
    public void setOrigData(DataFile newOrigData) {
        if (origData.isEmpty() ||
                (origData.get().getVersion() < newOrigData.getVersion())) {
            origData = Optional.of(newOrigData);
        }
    }
}

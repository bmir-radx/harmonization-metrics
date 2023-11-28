package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Optional;

public class Study {
    private String program;
    private String studyId;
    private Optional<DataFile> transformData;
    private Optional<DataFile> origData;

    public Study(String program, String studyId,
                 Optional<DataFile> transformData,
                 Optional<DataFile> origData) {
        this.program = program;
        this.studyId = studyId;
        this.transformData = transformData;
        this.origData = origData;
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

    public StudyMetrics collectMetrics() {
        Optional<Integer> nOrigDEs = Optional.empty();
        Optional<Integer> nTransformDEs = Optional.empty();
        Optional<Integer> nHarmonizableOrigDEs = Optional.empty();
        Optional<Integer> nHarmonizableTransformDEs = Optional.empty();
        Optional<Integer> nHarmonizedDEs = Optional.empty();
        Optional<Integer> nNonHarmonizableOrigDEs = Optional.empty();
        Optional<Integer> nNonHarmonizableTransformDEs = Optional.empty();
        if (origData.isPresent()) {
            nOrigDEs = Optional.of(origData.get().getVariableNames().size());
        }
        if (transformData.isPresent()) {
            nTransformDEs = Optional.of(transformData.get().getVariableNames().size());
        }
        return new StudyMetrics(nOrigDEs, nTransformDEs, nHarmonizableOrigDEs,
                nHarmonizableTransformDEs, nHarmonizedDEs, nNonHarmonizableOrigDEs,
                nNonHarmonizableTransformDEs);
    }
}

package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.List;

public record Study(
        StudyId studyId,
        ProgramId programId,
        List<OrigTransformFilePair> origTransformFilePairList) {
}

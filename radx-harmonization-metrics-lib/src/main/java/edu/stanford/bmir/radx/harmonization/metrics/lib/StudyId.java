package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.Objects;

public record StudyId(String value) {
    public StudyId(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static StudyId valueOf(String value) {
        return new StudyId(value);
    }
}

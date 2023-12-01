package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.Objects;

public record ReducedFileName(String value) {
    public ReducedFileName(String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static ReducedFileName valueOf(String value) {
        return new ReducedFileName(value);
    }
}

package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

class OrigTransformFilePairMetricsTest {

    ReducedFileName testName = ReducedFileName.valueOf("testName");
    ProgramId testProgramId = ProgramId.RADXUP;
    StudyId testStudyId = StudyId.valueOf("testStudyId");

    @Test
    public void hasHarmonizableElements_true() {
        int nHarmonizableDataElements = 1;
        int nHarmonizedDataElements = 2;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertTrue(metrics.hasMissedHarmonizableElements());
    }

    @Test
    public void hasHarmonizableElements_false() {
        int nHarmonizableDataElements = 0;
        int nHarmonizedDataElements = 2;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertFalse(metrics.hasMissedHarmonizableElements());
    }

    @Test
    public void hasHarmonizedElements_true() {
        int nHarmonizableDataElements = 1;
        int nHarmonizedDataElements = 2;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertTrue(metrics.hasHarmonizedElements());
    }

    @Test
    public void hasHarmonizedElements_false() {
        int nHarmonizableDataElements = 1;
        int nHarmonizedDataElements = 0;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertFalse(metrics.hasHarmonizedElements());
    }

    @Test
    public void isHarmonizable_trueBecausePartiallyHarmonized() {
        int nHarmonizableDataElements = 1;
        int nHarmonizedDataElements = 2;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertTrue(metrics.isHarmonizable());
        assertTrue(metrics.isPartiallyHarmonized());
    }

    @Test
    public void isHarmonizable_trueBecauseNotHarmonized() {
        int nHarmonizableDataElements = 1;
        int nHarmonizedDataElements = 0;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertTrue(metrics.isHarmonizable());
        assertFalse(metrics.isHarmonized());
    }

    @Test
    public void isHarmonizable_false() {
        int nHarmonizableDataElements = 0;
        int nHarmonizedDataElements = 2;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertFalse(metrics.isHarmonizable());
    }

    @Test
    public void isPartiallyHarmonized_true() {
        int nHarmonizableDataElements = 1;
        int nHarmonizedDataElements = 2;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertTrue(metrics.isPartiallyHarmonized());
    }

    @Test
    public void isPartiallyHarmonized_falseBecauseFullyHarmonized() {
        int nHarmonizableDataElements = 0;
        int nHarmonizedDataElements = 2;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertFalse(metrics.isPartiallyHarmonized());
        assertTrue(metrics.isHarmonized());
    }

    @Test
    public void isPartiallyHarmonized_falseBecauseTriviallyHarmonized() {
        int nHarmonizableDataElements = 0;
        int nHarmonizedDataElements = 0;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertFalse(metrics.isPartiallyHarmonized());
        assertTrue(metrics.isTriviallyHarmonized());
    }

    @Test
    public void isHarmonized_true() {
        int nHarmonizableDataElements = 0;
        int nHarmonizedDataElements = 1;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertTrue(metrics.isHarmonized());
    }

    @Test
    public void isHarmonized_false() {
        int nHarmonizableDataElements = 1;
        int nHarmonizedDataElements = 2;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertFalse(metrics.isHarmonized());
    }

    @Test
    public void isTriviallyHarmonized_true() {
        int nHarmonizableDataElements = 0;
        int nHarmonizedDataElements = 0;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertTrue(metrics.isTriviallyHarmonized());
        assertTrue(metrics.isHarmonized());
    }

    @Test
    public void isTriviallyHarmonized_false() {
        int nHarmonizableDataElements = 0;
        int nHarmonizedDataElements = 1;
        int nNonHarmonizableDataElements = 3;
        OrigTransformFilePairMetrics metrics = new OrigTransformFilePairMetrics(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                nHarmonizableDataElements, nHarmonizedDataElements, nNonHarmonizableDataElements);
        assertFalse(metrics.isTriviallyHarmonized());
        assertTrue(metrics.isHarmonized());
    }
}
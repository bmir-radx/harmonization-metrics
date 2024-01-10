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
    public void testHasHamonizedDataElement_true() {
        Optional<String> origFileName = Optional.of("testOrig");
        Optional<String> transformFileName = Optional.of("testTransform");
        int nHarmonizableDataElementsTier1 = 1;
        int nHarmonizableDataElementsTier2 = 1;
        int nHarmonizableDataElementsTier3 = 1;
        int nHarmonizedDataElementsTier1 = 1;
        int nHarmonizedDataElementsTier2 = 1;
        int nHarmonizedDataElementsTier3 = 1;
        int nDataElements = 3;
        Double percentHarmonizable = 100.;
        Double percentHarmonized = 100.;
        var metrics = new OrigTransformFilePairMetrics(
                testName,
                testProgramId,
                testStudyId,
                origFileName,
                transformFileName,
                nDataElements,
                nDataElements,
                nHarmonizableDataElementsTier1,
                nHarmonizableDataElementsTier2,
                nHarmonizableDataElementsTier3,
                percentHarmonizable,
                nHarmonizedDataElementsTier1,
                nHarmonizedDataElementsTier2,
                nHarmonizedDataElementsTier3,
                percentHarmonized);
        assertTrue(metrics.hasHarmonizedDataElement());
    }

    @Test
    public void testHasHamonizedDataElement_false() {
        Optional<String> origFileName = Optional.of("testOrig");
        Optional<String> transformFileName = Optional.of("testTransform");
        int nHarmonizableDataElementsTier1 = 1;
        int nHarmonizableDataElementsTier2 = 1;
        int nHarmonizableDataElementsTier3 = 1;
        int nHarmonizedDataElementsTier1 = 0;
        int nHarmonizedDataElementsTier2 = 0;
        int nHarmonizedDataElementsTier3 = 0;
        int nDataElements = 3;
        Double percentHarmonizable = 100.;
        Double percentHarmonized = 0.;
        var metrics = new OrigTransformFilePairMetrics(
                testName,
                testProgramId,
                testStudyId,
                origFileName,
                transformFileName,
                nDataElements,
                nDataElements,
                nHarmonizableDataElementsTier1,
                nHarmonizableDataElementsTier2,
                nHarmonizableDataElementsTier3,
                percentHarmonizable,
                nHarmonizedDataElementsTier1,
                nHarmonizedDataElementsTier2,
                nHarmonizedDataElementsTier3,
                percentHarmonized);
        assertFalse(metrics.hasHarmonizedDataElement());
    }
}
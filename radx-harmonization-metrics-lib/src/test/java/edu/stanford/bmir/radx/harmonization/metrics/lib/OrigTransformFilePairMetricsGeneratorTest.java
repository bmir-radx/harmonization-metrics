package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OrigTransformFilePairMetricsGeneratorTest {

    StudyId studyId = StudyId.valueOf("testStudyId");
    ProgramId programId = ProgramId.DHT;

    /*
    Mock an orig file. Harmonization statistics will be mocked.
     */
    private OrigFile generateOrigFile(int i) {
        String origName = String.format("%d_origcopy_v%d.csv", i, i);
        HashSet<String> origVariableNames = new HashSet<>();
        origVariableNames.add(String.format("%d", i));
        return new OrigFile(origName, i, origVariableNames);
    }

    /*
    Mock a transform file. Harmonization statistics will be mocked.
     */
    private TransformFile generateTransformFile(int i) {
        String transformName = String.format("%d_transformcopy_v%d.csv", i, i);
        HashSet<String> transformVariableNames = new HashSet<>();
        transformVariableNames.add(String.format("%d", i));
        return new TransformFile(transformName, i, transformVariableNames);
    }

    private OrigTransformFilePair createPairFromFiles(
            ReducedFileName name, Optional<OrigFile> orig, Optional<TransformFile> transform) {
        return new OrigTransformFilePair(name, programId, studyId, orig, transform);
    }

    @Test
    public void createMetricsFromFilePair_transformOnly()
            throws InvalidHarmonizationTierException, InvalidProgramIdException {
        // generate a file pair that contains only the origfile
        ReducedFileName name = ReducedFileName.valueOf("test");
        TransformFile transform = generateTransformFile(1);
        OrigTransformFilePair pair = createPairFromFiles(name, Optional.empty(), Optional.of(transform));

        // mock the harmonization checker for the metrics generator
        HarmonizationChecker mockChecker = Mockito.mock(HarmonizationChecker.class);
        int nHarmonizableT1 = 10;
        int nHarmonizableT2 = 9;
        int nHarmonizableT3 = 8;
        int nHarmonizedT1 = 1;
        int nHarmonizedT2 = 2;
        int nHarmonizedT3 = 3;
        Mockito.doReturn(nHarmonizableT1).when(mockChecker).countHarmonizableElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER1));
        Mockito.doReturn(nHarmonizableT2).when(mockChecker).countHarmonizableElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER2));
        Mockito.doReturn(nHarmonizableT3).when(mockChecker).countHarmonizableElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER3));
        Mockito.doReturn(nHarmonizedT1).when(mockChecker).countHarmonizedElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER1));
        Mockito.doReturn(nHarmonizedT2).when(mockChecker).countHarmonizedElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER2));
        Mockito.doReturn(nHarmonizedT3).when(mockChecker).countHarmonizedElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER3));

        // generate metrics with mocked checker
        var metricsGenerator = new OrigTransformFilePairMetricsGenerator(mockChecker);
        OrigTransformFilePairMetrics metrics = metricsGenerator.createMetricsFromFilePair(pair);

        // verify that the harmonization checker counter methods were called once
        // for each harmonization tier, only for the transform file
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER1));
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER2));
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER3));
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER1));
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER2));
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER3));

        // verify metrics have correct values
        assertEquals(name, metrics.pairName());
        assertEquals(programId, metrics.programId());
        assertEquals(studyId, metrics.studyId());
        assertTrue(metrics.origFileName().isEmpty());
        assertTrue(metrics.transformFileName().isPresent());
        assertEquals(transform.fileName(), metrics.transformFileName().get());
        assertEquals(0, metrics.nDataElementsOrig());
        assertEquals(transform.variableNames().size(), metrics.nDataElementsTransform());
        assertEquals(nHarmonizableT1, metrics.nHarmonizableDataElementsTier1());
        assertEquals(nHarmonizableT2, metrics.nHarmonizableDataElementsTier2());
        assertEquals(nHarmonizableT3, metrics.nHarmonizableDataElementsTier3());
        assertEquals(nHarmonizedT1, metrics.nHarmonizedDataElementsTier1());
        assertEquals(nHarmonizedT2, metrics.nHarmonizedDataElementsTier2());
        assertEquals(nHarmonizedT3, metrics.nHarmonizedDataElementsTier3());
    }

    @Test
    public void createMetricsFromFilePair_origOnly()
            throws InvalidHarmonizationTierException, InvalidProgramIdException {
        // generate a file pair that contains only the origfile
        ReducedFileName name = ReducedFileName.valueOf("test");
        OrigFile orig = generateOrigFile(1);
        OrigTransformFilePair pair = createPairFromFiles(name, Optional.of(orig), Optional.empty());

        // mock the harmonization checker for the metrics generator
        HarmonizationChecker mockChecker = Mockito.mock(HarmonizationChecker.class);
        int nHarmonizableT1 = 10;
        int nHarmonizableT2 = 9;
        int nHarmonizableT3 = 8;
        int nHarmonizedT1 = 1;
        int nHarmonizedT2 = 2;
        int nHarmonizedT3 = 3;
        Mockito.doReturn(nHarmonizableT1).when(mockChecker).countHarmonizableElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER1));
        Mockito.doReturn(nHarmonizableT2).when(mockChecker).countHarmonizableElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER2));
        Mockito.doReturn(nHarmonizableT3).when(mockChecker).countHarmonizableElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER3));
        Mockito.doReturn(nHarmonizedT1).when(mockChecker).countHarmonizedElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER1));
        Mockito.doReturn(nHarmonizedT2).when(mockChecker).countHarmonizedElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER2));
        Mockito.doReturn(nHarmonizedT3).when(mockChecker).countHarmonizedElements(
                Mockito.any(ProgramId.class), Mockito.anySet(),
                Mockito.eq(HarmonizationTier.TIER3));

        // generate metrics with mocked checker
        var metricsGenerator = new OrigTransformFilePairMetricsGenerator(mockChecker);
        OrigTransformFilePairMetrics metrics = metricsGenerator.createMetricsFromFilePair(pair);

        // verify that the harmonization checker counter methods were called once
        // for each harmonization tier, only for the origfile
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER1));
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER2));
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER3));
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER1));
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER2));
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER3));

        // verify metrics have correct values
        assertEquals(name, metrics.pairName());
        assertEquals(programId, metrics.programId());
        assertEquals(studyId, metrics.studyId());
        assertTrue(metrics.origFileName().isPresent());
        assertEquals(orig.fileName(), metrics.origFileName().get());
        assertTrue(metrics.transformFileName().isEmpty());
        assertEquals(orig.variableNames().size(), metrics.nDataElementsOrig());
        assertEquals(0, metrics.nDataElementsTransform());
        assertEquals(nHarmonizableT1, metrics.nHarmonizableDataElementsTier1());
        assertEquals(nHarmonizableT2, metrics.nHarmonizableDataElementsTier2());
        assertEquals(nHarmonizableT3, metrics.nHarmonizableDataElementsTier3());
        assertEquals(nHarmonizedT1, metrics.nHarmonizedDataElementsTier1());
        assertEquals(nHarmonizedT2, metrics.nHarmonizedDataElementsTier2());
        assertEquals(nHarmonizedT3, metrics.nHarmonizedDataElementsTier3());
    }
    @Test
    public void createMetricsFromFilePair_completePair()
            throws InvalidHarmonizationTierException, InvalidProgramIdException {
        // generate a file pair that contains the orig and transform files
        ReducedFileName name = ReducedFileName.valueOf("test");
        OrigFile orig = generateOrigFile(1);
        TransformFile transform = generateTransformFile(1);
        OrigTransformFilePair pair = createPairFromFiles(
                name, Optional.of(orig), Optional.of(transform));

        // mock the harmonization checker for the metrics generator
        // sometimes variables can be lost or gained when generating the
        // transform file if harmonization rules were applied incorrectly
        HarmonizationChecker mockChecker = Mockito.mock(HarmonizationChecker.class);
        int nHarmonizableOrigT1 = 10;
        int nHarmonizableOrigT2 = 9;
        int nHarmonizableOrigT3 = 8;
        int nHarmonizableTransformT1 = 8;
        int nHarmonizableTransformT2 = 7;
        int nHarmonizableTransformT3 = 9;
        int nHarmonizedOrigT1 = 1;
        int nHarmonizedOrigT2 = 2;
        int nHarmonizedOrigT3 = 0;
        int nHarmonizedTransformT1 = 7;
        int nHarmonizedTransformT2 = 5;
        int nHarmonizedTransformT3 = 9;
        // first time is for the orig file. second is for the transform.
        Mockito.doReturn(nHarmonizableOrigT1).doReturn(nHarmonizableTransformT1)
                .when(mockChecker).countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER1));
        Mockito.doReturn(nHarmonizableOrigT2).doReturn(nHarmonizableTransformT2)
                .when(mockChecker).countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER2));
        Mockito.doReturn(nHarmonizableOrigT3).doReturn(nHarmonizableTransformT3)
                .when(mockChecker).countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER3));
        Mockito.doReturn(nHarmonizedOrigT1).doReturn(nHarmonizedTransformT1)
                .when(mockChecker).countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER1));
        Mockito.doReturn(nHarmonizedOrigT2).doReturn(nHarmonizedTransformT2)
                .when(mockChecker).countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER2));
        Mockito.doReturn(nHarmonizedOrigT3).doReturn(nHarmonizedTransformT3)
                .when(mockChecker).countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER3));

        // generate metrics with mocked checker
        var metricsGenerator = new OrigTransformFilePairMetricsGenerator(mockChecker);
        OrigTransformFilePairMetrics metrics = metricsGenerator.createMetricsFromFilePair(pair);

        // verify that the harmonization checker counter methods were called twice
        // for each harmonization tier, once for the orig and once for the transform
        Mockito.verify(mockChecker, Mockito.times(2))
                .countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER1));
        Mockito.verify(mockChecker, Mockito.times(2))
                .countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER2));
        Mockito.verify(mockChecker, Mockito.times(2))
                .countHarmonizableElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER3));
        Mockito.verify(mockChecker, Mockito.times(2))
                .countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER1));
        Mockito.verify(mockChecker, Mockito.times(2))
                .countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER2));
        Mockito.verify(mockChecker, Mockito.times(2))
                .countHarmonizedElements(
                        Mockito.any(ProgramId.class), Mockito.anySet(),
                        Mockito.eq(HarmonizationTier.TIER3));

        // verify metrics have correct values
        assertEquals(name, metrics.pairName());
        assertEquals(programId, metrics.programId());
        assertEquals(studyId, metrics.studyId());
        assertTrue(metrics.origFileName().isPresent());
        assertEquals(orig.fileName(), metrics.origFileName().get());
        assertTrue(metrics.transformFileName().isPresent());
        assertEquals(transform.fileName(), metrics.transformFileName().get());
        assertEquals(orig.variableNames().size(), metrics.nDataElementsOrig());
        assertEquals(transform.variableNames().size(), metrics.nDataElementsTransform());
        assertEquals(nHarmonizableOrigT1, metrics.nHarmonizableDataElementsTier1());
        assertEquals(nHarmonizableOrigT2, metrics.nHarmonizableDataElementsTier2());
        assertEquals(nHarmonizableTransformT3, metrics.nHarmonizableDataElementsTier3());
        assertEquals(nHarmonizedTransformT1, metrics.nHarmonizedDataElementsTier1());
        assertEquals(nHarmonizedTransformT2, metrics.nHarmonizedDataElementsTier2());
        assertEquals(nHarmonizedTransformT3, metrics.nHarmonizedDataElementsTier3());
    }
}
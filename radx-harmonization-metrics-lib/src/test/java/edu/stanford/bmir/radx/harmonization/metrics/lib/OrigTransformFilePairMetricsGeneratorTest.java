package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class OrigTransformFilePairMetricsGeneratorTest {

    @Test
    public void createMetricsFromFilePair_origOnly() throws InvalidProgramIdException {
        String fileName = "filename";
        ReducedFileName testName = ReducedFileName.valueOf("testName");
        ProgramId testProgramId = ProgramId.RADXUP;
        StudyId testStudyId = StudyId.valueOf("testStudyId");
        Integer testVersion = 1;
        // variableNames is a dummy. the result of the harmonization checker will be mocked
        HashSet<String> variableNames = new HashSet<>(Arrays.asList("a", "b", "c", "d"));
        OrigFile origData = new OrigFile(fileName, testVersion, variableNames);
        OrigTransformFilePair filePair = new OrigTransformFilePair(
                testName, testProgramId, testStudyId,
                Optional.of(origData), Optional.empty());

        // mock the harmonization checker for the metrics generator
        HarmonizationChecker mockChecker = Mockito.mock(HarmonizationChecker.class);
        Integer nHarmonizableDataElementsOrig = 2;
        Integer nHarmonizedDataElementsOrig = 1;
        Mockito.doReturn(nHarmonizableDataElementsOrig).when(mockChecker)
                .countHarmonizableElements(Mockito.any(ProgramId.class), Mockito.anySet());
        Mockito.doReturn(nHarmonizedDataElementsOrig).when(mockChecker)
                .countHarmonizedElements(Mockito.any(ProgramId.class), Mockito.anySet());

        // generate metrics with mocked checker
        var metricsGenerator = new OrigTransformFilePairMetricsGenerator(mockChecker);
        OrigTransformFilePairMetrics metrics = metricsGenerator.createMetricsFromFilePair(filePair);

        // verify that the harmonization checker counter methods were called once each
        // only for the origfile and not for the transformfile
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizableElements(Mockito.any(ProgramId.class), Mockito.anySet());
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizedElements(Mockito.any(ProgramId.class), Mockito.anySet());

        // verify metrics have correct values
        assertEquals(testName, metrics.pairName());
        assertEquals(testProgramId, metrics.programId());
        assertEquals(testStudyId, metrics.studyId());
        assertEquals(testVersion, metrics.versionOrig().get());
        assertEquals(variableNames.size(), metrics.nDataElementsOrig().get());
        assertEquals(nHarmonizableDataElementsOrig, metrics.nHarmonizableDataElementsOrig().get());
        assertEquals(nHarmonizedDataElementsOrig, metrics.nHarmonizedDataElementsOrig().get());
        assertFalse(metrics.versionTransform().isPresent());
        assertFalse(metrics.nDataElementsTransform().isPresent());
        assertFalse(metrics.nHarmonizableDataElementsTransform().isPresent());
        assertFalse(metrics.nHarmonizedDataElementsTransform().isPresent());
        assertEquals(nHarmonizableDataElementsOrig, metrics.nMissedHarmonizableDataElements());
        assertEquals(nHarmonizedDataElementsOrig, metrics.nHarmonizedDataElements());
        Integer nNonHarmonizableDataElements = variableNames.size() - nHarmonizableDataElementsOrig - nHarmonizedDataElementsOrig;
        assertEquals(nNonHarmonizableDataElements, metrics.nNonHarmonizableDataElements());
    }

    @Test
    public void createMetricsFromFilePair_transformOnly() throws InvalidProgramIdException {
        String fileName = "filename";
        ReducedFileName testName = ReducedFileName.valueOf("testName");
        ProgramId testProgramId = ProgramId.RADXUP;
        StudyId testStudyId = StudyId.valueOf("testStudyId");
        Integer testVersion = 1;
        // variableNames is a dummy. the result of the harmonization checker will be mocked
        HashSet<String> variableNames = new HashSet<>(Arrays.asList("a", "b", "c", "d"));
        TransformFile transformData = new TransformFile(fileName, testVersion, variableNames);
        OrigTransformFilePair filePair = new OrigTransformFilePair(
                testName, testProgramId, testStudyId,
                Optional.empty(), Optional.of(transformData));

        // mock the harmonization checker for the metrics generator
        HarmonizationChecker mockChecker = Mockito.mock(HarmonizationChecker.class);
        Integer nHarmonizableDataElementsTransform = 2;
        Integer nHarmonizedDataElementsTransform = 1;
        Mockito.doReturn(nHarmonizableDataElementsTransform).when(mockChecker)
                .countHarmonizableElements(Mockito.any(ProgramId.class), Mockito.anySet());
        Mockito.doReturn(nHarmonizedDataElementsTransform).when(mockChecker)
                .countHarmonizedElements(Mockito.any(ProgramId.class), Mockito.anySet());

        // generate metrics with mocked checker
        var metricsGenerator = new OrigTransformFilePairMetricsGenerator(mockChecker);
        OrigTransformFilePairMetrics metrics = metricsGenerator.createMetricsFromFilePair(filePair);

        // verify that the harmonization checker counter methods were called once each
        // only for the transformfile and not for the origfile
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizableElements(Mockito.any(ProgramId.class), Mockito.anySet());
        Mockito.verify(mockChecker, Mockito.times(1))
                .countHarmonizedElements(Mockito.any(ProgramId.class), Mockito.anySet());

        // verify metrics have correct values
        assertEquals(testName, metrics.pairName());
        assertEquals(testProgramId, metrics.programId());
        assertEquals(testStudyId, metrics.studyId());
        assertFalse(metrics.versionOrig().isPresent());
        assertFalse(metrics.nDataElementsOrig().isPresent());
        assertFalse(metrics.nHarmonizableDataElementsOrig().isPresent());
        assertFalse(metrics.nHarmonizedDataElementsOrig().isPresent());
        assertEquals(testVersion, metrics.versionTransform().get());
        assertEquals(variableNames.size(), metrics.nDataElementsTransform().get());
        assertEquals(nHarmonizableDataElementsTransform, metrics.nHarmonizableDataElementsTransform().get());
        assertEquals(nHarmonizedDataElementsTransform, metrics.nHarmonizedDataElementsTransform().get());
        assertEquals(nHarmonizableDataElementsTransform, metrics.nMissedHarmonizableDataElements());
        assertEquals(nHarmonizedDataElementsTransform, metrics.nHarmonizedDataElements());
        Integer nNonHarmonizableDataElements = variableNames.size() - nHarmonizableDataElementsTransform - nHarmonizedDataElementsTransform;
        assertEquals(nNonHarmonizableDataElements, metrics.nNonHarmonizableDataElements());
    }

    @Test
    public void createMetricsFromFilePair_origAndTransform() throws InvalidProgramIdException {
        String fileName = "filename";
        ReducedFileName testName = ReducedFileName.valueOf("testName");
        ProgramId testProgramId = ProgramId.RADXUP;
        StudyId testStudyId = StudyId.valueOf("testStudyId");
        Integer origVersion = 1;
        Integer transformVersion = 2;
        // the values in  the below sets are meaningless except to give them the correct size
        HashSet<String> origVariableNames = new HashSet<>(Arrays.asList("a", "b", "c", "d", "e"));
        HashSet<String> transformVariableNames = new HashSet<>(Arrays.asList("a", "b", "x", "y", "z"));
        OrigFile origData = new OrigFile(fileName, origVersion, origVariableNames);
        TransformFile transformData = new TransformFile(fileName, transformVersion, transformVariableNames);
        OrigTransformFilePair filePair = new OrigTransformFilePair(
                testName, testProgramId, testStudyId,
                Optional.of(origData), Optional.of(transformData));

        // mock the harmonization checker for the metrics generator
        HarmonizationChecker mockChecker = Mockito.mock(HarmonizationChecker.class);
        // mocked behavior says that there are 3 harmonizable data elements
        // and one harmonized data element in the origcopy
        Integer nHarmonizableDataElementsOrig = 3;
        Integer nHarmonizedDataElementsOrig = 1;
        Mockito.doReturn(nHarmonizableDataElementsOrig).when(mockChecker)
                .countHarmonizableElements(Mockito.any(ProgramId.class), Mockito.eq(origVariableNames));
        Mockito.doReturn(nHarmonizedDataElementsOrig).when(mockChecker)
                .countHarmonizedElements(Mockito.any(ProgramId.class), Mockito.eq(origVariableNames));
        // mocked behavior says that there are 4 harmonized data elements in the transformcopy
        Integer nHarmonizableDataElementsTransform = 0;
        Integer nHarmonizedDataElementsTransform = 4;
        Mockito.doReturn(nHarmonizableDataElementsTransform).when(mockChecker)
                .countHarmonizableElements(Mockito.any(ProgramId.class), Mockito.eq(transformVariableNames));
        Mockito.doReturn(nHarmonizedDataElementsTransform).when(mockChecker)
                .countHarmonizedElements(Mockito.any(ProgramId.class), Mockito.eq(transformVariableNames));

        // generate metrics with mocked checker
        var metricsGenerator = new OrigTransformFilePairMetricsGenerator(mockChecker);
        OrigTransformFilePairMetrics metrics = metricsGenerator.createMetricsFromFilePair(filePair);

        // verify that the harmonization checker counter methods were called twice each
        Mockito.verify(mockChecker, Mockito.times(2))
                .countHarmonizableElements(Mockito.any(ProgramId.class), Mockito.anySet());
        Mockito.verify(mockChecker, Mockito.times(2))
                .countHarmonizedElements(Mockito.any(ProgramId.class), Mockito.anySet());

        // verify metrics have correct values
        assertEquals(testName, metrics.pairName());
        assertEquals(testProgramId, metrics.programId());
        assertEquals(testStudyId, metrics.studyId());
        assertEquals(origVersion, metrics.versionOrig().get());
        assertEquals(origVariableNames.size(), metrics.nDataElementsOrig().get());
        assertEquals(nHarmonizableDataElementsOrig, metrics.nHarmonizableDataElementsOrig().get());
        assertEquals(nHarmonizedDataElementsOrig, metrics.nHarmonizedDataElementsOrig().get());
        assertEquals(transformVersion, metrics.versionTransform().get());
        assertEquals(transformVariableNames.size(), metrics.nDataElementsTransform().get());
        assertEquals(nHarmonizableDataElementsTransform, metrics.nHarmonizableDataElementsTransform().get());
        assertEquals(nHarmonizedDataElementsTransform, metrics.nHarmonizedDataElementsTransform().get());
        assertEquals(nHarmonizableDataElementsTransform, metrics.nMissedHarmonizableDataElements());
        assertEquals(nHarmonizedDataElementsTransform, metrics.nHarmonizedDataElements());
        Integer nNonHarmonizableDataElements = transformVariableNames.size() - nHarmonizableDataElementsTransform - nHarmonizedDataElementsTransform;
        assertEquals(nNonHarmonizableDataElements, metrics.nNonHarmonizableDataElements());
    }
}
package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class MetricsCalculatorTest {

    private OrigTransformFilePair generateTestPair(int i, ProgramId programId, StudyId studyId) {
        String origName = String.format("%d_origcopy_v%d.csv", i, i);
        String transformName = String.format("%d_transformcopy_v%d.csv", i, i);
        int version = i;
        ReducedFileName reducedName = ReducedFileName.valueOf(String.format("%d", i));
        Set<String> origVariableNames = new HashSet<>();
        Set<String> transformVariableNames = new HashSet<>();
        OrigFile orig = new OrigFile(origName, version, origVariableNames);
        TransformFile transform = new TransformFile(transformName, version, transformVariableNames);
        OrigTransformFilePair pair = new OrigTransformFilePair(reducedName, programId, studyId,
                Optional.of(orig), Optional.of(transform));
        return pair;
    }

    @Test
    public void computeHarmonizationMetrics()
            throws InvalidOrigTransformCategoryException, NoVersionNumberException,
            InvalidProgramIdException, InvalidHarmonizationTierException {
        DataFileProcessor mockFileProcessor = Mockito.mock(DataFileProcessor.class);
        HarmonizationRules rules = new MockRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);
        var pairMetricsGenerator = new OrigTransformFilePairMetricsGenerator(checker);
        var studyMetricsGenerator = new StudyMetricsGenerator(checker);
        OrigTransformFilePairMetricsGenerator mockPairMetricsGenerator = Mockito.spy(pairMetricsGenerator);
        StudyMetricsGenerator mockStudyMetricsGenerator = Mockito.spy(studyMetricsGenerator);
        MetricsCalculator calculator = new MetricsCalculator(
                mockFileProcessor, mockPairMetricsGenerator, mockStudyMetricsGenerator);

        // mock the output of the DataFileProcessor
        Map<ReducedFileName, OrigTransformFilePair> mockPairMap = new HashMap<>();
        ProgramId program = ProgramId.RADXUP;
        StudyId studyId1 = StudyId.valueOf("sad");
        StudyId studyId2 = StudyId.valueOf("mad");
        OrigTransformFilePair pair1 = generateTestPair(1, program, studyId1);
        OrigTransformFilePair pair2 = generateTestPair(2, program, studyId1);
        OrigTransformFilePair pair3 = generateTestPair(3, program, studyId2);
        OrigTransformFilePair pair4 = generateTestPair(4, program, studyId2);
        ReducedFileName name1 = ReducedFileName.valueOf("1");
        ReducedFileName name2 = ReducedFileName.valueOf("2");
        ReducedFileName name3 = ReducedFileName.valueOf("3");
        ReducedFileName name4 = ReducedFileName.valueOf("4");
        mockPairMap.put(name1, pair1);
        mockPairMap.put(name2, pair2);
        mockPairMap.put(name3, pair3);
        mockPairMap.put(name4, pair4);

        Map<StudyId, Study> mockStudyMap = new HashMap<>();
        Study study1 = new Study(studyId1, program, Arrays.asList(pair1, pair2));
        Study study2 = new Study(studyId2, program, Arrays.asList(pair1, pair2));
        mockStudyMap.put(studyId1, study1);
        mockStudyMap.put(studyId2, study2);

        Mockito.doReturn(mockPairMap).when(mockFileProcessor).processDataFiles(Mockito.anyList());
        Mockito.doReturn(mockStudyMap).when(mockFileProcessor).organizeFilePairsByStudy(Mockito.eq(mockPairMap));

        // run the calculator with a fake input (empty list)
        List<DataFileExternal> fakeInput = new ArrayList<>();
        MetricsReport report = calculator.computeHarmonizationMetrics(fakeInput);

        // validate that the right number of entries appear for the pair metrics
        // and study metrics based on the mocked maps above
        assertEquals(4, report.pairMetrics().size());
        assertEquals(2, report.studyMetrics().size());

        // verify that the generators are called the right number of times
        Mockito.verify(mockPairMetricsGenerator, Mockito.times(4))
                .createMetricsFromFilePair(Mockito.any());
        Mockito.verify(mockStudyMetricsGenerator, Mockito.times(2))
                .createMetricsFromStudy(Mockito.any());
    }
}
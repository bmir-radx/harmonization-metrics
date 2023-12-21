package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudyMetricsGeneratorTest {

    StudyId studyId = StudyId.valueOf("testStudyId");
    ProgramId programId = ProgramId.DHT;

    private OrigTransformFilePair generateTestPair(int i) {
        String origName = String.format("%d_origcopy_v%d.csv", i, i);
        String transformName = String.format("%d_transformcopy_v%d.csv", i, i);
        int version = i;
        ReducedFileName reducedName = ReducedFileName.valueOf(String.format("%d", i));
        Set<String> origVariableNames = new HashSet<>();
        origVariableNames.add("t1Var1");
        origVariableNames.add("t1Var2");
        origVariableNames.add("t1Var3");
        origVariableNames.add("t2Var1");
        origVariableNames.add("t2Var2");
        origVariableNames.add("t3Var1");
        origVariableNames.add(String.format("%d", i));
        Set<String> transformVariableNames = new HashSet<>();
        transformVariableNames.add("harmonizedT1Var1");
        transformVariableNames.add("harmonizedT1Var2");
        transformVariableNames.add("harmonizedT1Var3");
        transformVariableNames.add("harmonizedT2Var1");
        transformVariableNames.add("harmonizedT2Var2");
        transformVariableNames.add("harmonizedT3Var3");
        transformVariableNames.add(String.format("%d", i));
        OrigFile orig = new OrigFile(origName, version, origVariableNames);
        TransformFile transform = new TransformFile(transformName, version, transformVariableNames);
        OrigTransformFilePair pair = new OrigTransformFilePair(reducedName, programId, studyId,
                Optional.of(orig), Optional.of(transform));
        return pair;
    }

    private Study generateTestStudy(int nPairs) {
        List<OrigTransformFilePair> pairs = new ArrayList<>();
        for (int i = 0; i < nPairs; i++) {
            pairs.add(generateTestPair(i));
        }
        return new Study(studyId, programId, pairs);
    }

    @Test
    public void createMetricsFromStudy()
            throws InvalidHarmonizationTierException, InvalidProgramIdException {
        int nPairs = 3;
        Study study = generateTestStudy(nPairs);

        // use a spy to track a real harmonization checker built on mocked rules
        HarmonizationRules rules = new MockRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);
        HarmonizationChecker mockChecker = Mockito.spy(checker);

        var metricsGenerator = new StudyMetricsGenerator(mockChecker);
        StudyMetrics metrics = metricsGenerator.createMetricsFromStudy(study);

        // verify that the harmonization checker counter methods were called six
        // times. six times because once for each tier of variables
        // and once for each of the orig and transform files.
        Mockito.verify(mockChecker, Mockito.times(6))
                .countHarmonizableElements(
                        Mockito.any(ProgramId.class),
                        Mockito.anySet(),
                        Mockito.any(HarmonizationTier.class));
        Mockito.verify(mockChecker, Mockito.times(6))
                .countHarmonizedElements(
                        Mockito.any(ProgramId.class),
                        Mockito.anySet(),
                        Mockito.any(HarmonizationTier.class));

        // verify metrics have correct values
        assertEquals(studyId, metrics.studyId());
        assertEquals(programId, metrics.programId());
        assertEquals(nPairs, metrics.nOrigTransformFilePairs());
        // 6 variables + 1 for each pair
        assertEquals(9, metrics.nUniqueDataElements());
        assertEquals(3, metrics.nUniqueHarmonizableDataElementsTier1());
        assertEquals(2, metrics.nUniqueHarmonizableDataElementsTier2());
        assertEquals(1, metrics.nUniqueHarmonizableDataElementsTier3());
        assertEquals(3, metrics.nUniqueHarmonizedDataElementsTier1());
        assertEquals(2, metrics.nUniqueHarmonizedDataElementsTier2());
        assertEquals(1, metrics.nUniqueHarmonizedDataElementsTier3());
//        assertEquals(testName, metrics.pairName());
//        assertEquals(testProgramId, metrics.programId());
//        assertEquals(testStudyId, metrics.studyId());
//        assertEquals(origVersion, metrics.versionOrig().get());
//        assertEquals(origVariableNames.size(), metrics.nDataElementsOrig().get());
//        assertEquals(nHarmonizableDataElementsOrig, metrics.nHarmonizableDataElementsOrig().get());
//        assertEquals(nHarmonizedDataElementsOrig, metrics.nHarmonizedDataElementsOrig().get());
//        assertEquals(transformVersion, metrics.versionTransform().get());
//        assertEquals(transformVariableNames.size(), metrics.nDataElementsTransform().get());
//        assertEquals(nHarmonizableDataElementsTransform, metrics.nHarmonizableDataElementsTransform().get());
//        assertEquals(nHarmonizedDataElementsTransform, metrics.nHarmonizedDataElementsTransform().get());
//        assertEquals(nHarmonizableDataElementsTransform, metrics.nMissedHarmonizableDataElements());
//        assertEquals(nHarmonizedDataElementsTransform, metrics.nHarmonizedDataElements());
//        Integer nNonHarmonizableDataElements = transformVariableNames.size() - nHarmonizableDataElementsTransform - nHarmonizedDataElementsTransform;
//        assertEquals(nNonHarmonizableDataElements, metrics.nNonHarmonizableDataElements());
    }
}
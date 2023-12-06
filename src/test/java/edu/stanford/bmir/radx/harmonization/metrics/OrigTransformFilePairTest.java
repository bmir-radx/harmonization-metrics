package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class OrigTransformFilePairTest {
    @Test
    public void testNoDataFileConstructor() {
        ReducedFileName testName = ReducedFileName.valueOf("testName");
        ProgramIdentifier testProgramIdentifier = ProgramIdentifier.RADXUP;
        StudyId testStudyId = StudyId.valueOf("testStudyId");
        OrigTransformFilePair dataSet = new OrigTransformFilePair(testName, testProgramIdentifier, testStudyId);

        assertEquals(testName, dataSet.pairName());
        assertEquals(testProgramIdentifier, dataSet.programIdentifier());
        assertEquals(testStudyId, dataSet.studyId());
        assertTrue(dataSet.origFile().isEmpty());
        assertTrue(dataSet.transformFile().isEmpty());
    }

    @Test
    public void testUpdateOrigData() {
        String fileName = "filename";
        ReducedFileName testName = ReducedFileName.valueOf("testName");
        ProgramIdentifier testProgramIdentifier = ProgramIdentifier.RADXUP;
        StudyId testStudyId = StudyId.valueOf("testStudyId");
        OrigTransformFilePair origTransformFilePair = new OrigTransformFilePair(testName, testProgramIdentifier, testStudyId);

        int testVersion1 = 1;
        HashSet<String> variableNames1 = new HashSet<>(Arrays.asList("var1", "var2"));
        OrigFile origData1 = new OrigFile(fileName, testVersion1, variableNames1);
        OrigTransformFilePair updatedOrigTransformFilePair1 = origTransformFilePair.updateOrigData(origData1);

        assertNotEquals(origTransformFilePair, updatedOrigTransformFilePair1);
        assertTrue(origTransformFilePair.origFile().isEmpty());
        assertTrue(updatedOrigTransformFilePair1.origFile().isPresent());
        assertEquals(origData1, updatedOrigTransformFilePair1.origFile().get());

        int testVersion2 = 2;
        HashSet<String> variableNames2 = new HashSet<>(Arrays.asList("var3", "var4"));
        OrigFile origData2 = new OrigFile(fileName, testVersion2, variableNames2);
        OrigTransformFilePair updatedOrigTransformFilePair2 = updatedOrigTransformFilePair1.updateOrigData(origData2);

        assertNotEquals(updatedOrigTransformFilePair1, updatedOrigTransformFilePair2);
        assertTrue(updatedOrigTransformFilePair2.origFile().isPresent());
        assertEquals(origData2, updatedOrigTransformFilePair2.origFile().get());
    }

    @Test
    public void testSetTransformData() {
        String fileName = "fileName";
        ReducedFileName testName = ReducedFileName.valueOf("testName");
        ProgramIdentifier testProgramIdentifier = ProgramIdentifier.RADXUP;
        StudyId testStudyId = StudyId.valueOf("testStudyId");
        OrigTransformFilePair origTransformFilePair = new OrigTransformFilePair(testName, testProgramIdentifier, testStudyId);

        int testVersion1 = 1;
        HashSet<String> variableNames1 = new HashSet<>(Arrays.asList("var1", "var2"));
        TransformFile transformData1 = new TransformFile(fileName, testVersion1, variableNames1);
        OrigTransformFilePair updatedOrigTransformFilePair1 = origTransformFilePair.updateTransformData(transformData1);

        assertNotEquals(origTransformFilePair, updatedOrigTransformFilePair1);
        assertTrue(origTransformFilePair.transformFile().isEmpty());
        assertTrue(updatedOrigTransformFilePair1.transformFile().isPresent());
        assertEquals(transformData1, updatedOrigTransformFilePair1.transformFile().get());

        int testVersion2 = 2;
        HashSet<String> variableNames2 = new HashSet<>(Arrays.asList("var3", "var4"));
        TransformFile transformData2 = new TransformFile(fileName, testVersion2, variableNames2);
        OrigTransformFilePair updatedOrigTransformFilePair2 = updatedOrigTransformFilePair1.updateTransformData(transformData2);

        assertNotEquals(updatedOrigTransformFilePair1, updatedOrigTransformFilePair2);
        assertTrue(updatedOrigTransformFilePair2.transformFile().isPresent());
        assertEquals(transformData2, updatedOrigTransformFilePair2.transformFile().get());
    }
}

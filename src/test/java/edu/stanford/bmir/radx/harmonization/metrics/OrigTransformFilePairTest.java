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

        assertEquals(testName, dataSet.name());
        assertEquals(testProgramIdentifier, dataSet.programIdentifier());
        assertEquals(testStudyId, dataSet.studyId());
        assertTrue(dataSet.origData().isEmpty());
        assertTrue(dataSet.transformData().isEmpty());
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
        assertTrue(origTransformFilePair.origData().isEmpty());
        assertTrue(updatedOrigTransformFilePair1.origData().isPresent());
        assertEquals(origData1, updatedOrigTransformFilePair1.origData().get());

        int testVersion2 = 2;
        HashSet<String> variableNames2 = new HashSet<>(Arrays.asList("var3", "var4"));
        OrigFile origData2 = new OrigFile(fileName, testVersion2, variableNames2);
        OrigTransformFilePair updatedOrigTransformFilePair2 = updatedOrigTransformFilePair1.updateOrigData(origData2);

        assertNotEquals(updatedOrigTransformFilePair1, updatedOrigTransformFilePair2);
        assertTrue(updatedOrigTransformFilePair2.origData().isPresent());
        assertEquals(origData2, updatedOrigTransformFilePair2.origData().get());
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
        assertTrue(origTransformFilePair.transformData().isEmpty());
        assertTrue(updatedOrigTransformFilePair1.transformData().isPresent());
        assertEquals(transformData1, updatedOrigTransformFilePair1.transformData().get());

        int testVersion2 = 2;
        HashSet<String> variableNames2 = new HashSet<>(Arrays.asList("var3", "var4"));
        TransformFile transformData2 = new TransformFile(fileName, testVersion2, variableNames2);
        OrigTransformFilePair updatedOrigTransformFilePair2 = updatedOrigTransformFilePair1.updateTransformData(transformData2);

        assertNotEquals(updatedOrigTransformFilePair1, updatedOrigTransformFilePair2);
        assertTrue(updatedOrigTransformFilePair2.transformData().isPresent());
        assertEquals(transformData2, updatedOrigTransformFilePair2.transformData().get());
    }
}

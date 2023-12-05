package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TestDataFilePair {
    @Test
    public void testNoDataFileConstructor() {
        ReducedFileName testName = ReducedFileName.valueOf("testName");
        Program testProgram = Program.RADXUP;
        StudyId testStudyId = StudyId.valueOf("testStudyId");
        DataFilePair dataSet = new DataFilePair(testName, testProgram, testStudyId);

        assertEquals(testName, dataSet.name());
        assertEquals(testProgram, dataSet.program());
        assertEquals(testStudyId, dataSet.studyId());
        assertTrue(dataSet.origData().isEmpty());
        assertTrue(dataSet.transformData().isEmpty());
    }

    @Test
    public void testUpdateOrigData() {
        String fileName = "filename";
        ReducedFileName testName = ReducedFileName.valueOf("testName");
        Program testProgram = Program.RADXUP;
        StudyId testStudyId = StudyId.valueOf("testStudyId");
        DataFilePair dataFilePair = new DataFilePair(testName, testProgram, testStudyId);

        int testVersion1 = 1;
        HashSet<String> variableNames1 = new HashSet<>(Arrays.asList("var1", "var2"));
        OrigFile origData1 = new OrigFile(fileName, testVersion1, variableNames1);
        DataFilePair updatedDataFilePair1 = dataFilePair.updateOrigData(origData1);

        assertNotEquals(dataFilePair, updatedDataFilePair1);
        assertTrue(dataFilePair.origData().isEmpty());
        assertTrue(updatedDataFilePair1.origData().isPresent());
        assertEquals(origData1, updatedDataFilePair1.origData().get());

        int testVersion2 = 2;
        HashSet<String> variableNames2 = new HashSet<>(Arrays.asList("var3", "var4"));
        OrigFile origData2 = new OrigFile(fileName, testVersion2, variableNames2);
        DataFilePair updatedDataFilePair2 = updatedDataFilePair1.updateOrigData(origData2);

        assertNotEquals(updatedDataFilePair1, updatedDataFilePair2);
        assertTrue(updatedDataFilePair2.origData().isPresent());
        assertEquals(origData2, updatedDataFilePair2.origData().get());
    }

    @Test
    public void testSetTransformData() {
        String fileName = "fileName";
        ReducedFileName testName = ReducedFileName.valueOf("testName");
        Program testProgram = Program.RADXUP;
        StudyId testStudyId = StudyId.valueOf("testStudyId");
        DataFilePair dataFilePair = new DataFilePair(testName, testProgram, testStudyId);

        int testVersion1 = 1;
        HashSet<String> variableNames1 = new HashSet<>(Arrays.asList("var1", "var2"));
        TransformFile transformData1 = new TransformFile(fileName, testVersion1, variableNames1);
        DataFilePair updatedDataFilePair1 = dataFilePair.updateTransformData(transformData1);

        assertNotEquals(dataFilePair, updatedDataFilePair1);
        assertTrue(dataFilePair.transformData().isEmpty());
        assertTrue(updatedDataFilePair1.transformData().isPresent());
        assertEquals(transformData1, updatedDataFilePair1.transformData().get());

        int testVersion2 = 2;
        HashSet<String> variableNames2 = new HashSet<>(Arrays.asList("var3", "var4"));
        TransformFile transformData2 = new TransformFile(fileName, testVersion2, variableNames2);
        DataFilePair updatedDataFilePair2 = updatedDataFilePair1.updateTransformData(transformData2);

        assertNotEquals(updatedDataFilePair1, updatedDataFilePair2);
        assertTrue(updatedDataFilePair2.transformData().isPresent());
        assertEquals(transformData2, updatedDataFilePair2.transformData().get());
    }
}

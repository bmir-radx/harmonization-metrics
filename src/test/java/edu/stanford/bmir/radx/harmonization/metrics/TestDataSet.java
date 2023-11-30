package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class TestDataSet {
    @Test
    public void testNoDataFileConstructor() {
        String testName = "testName";
        Program testProgram = Program.RADXUP;
        StudyId testStudyId = StudyId.valueOf("testStudyId");
        DataSet dataSet = new DataSet(testName, testProgram, testStudyId);

        assertEquals(testName, dataSet.getName());
        assertEquals(testProgram, dataSet.getProgram());
        assertEquals(testStudyId, dataSet.getStudyId());
        assertTrue(dataSet.getOrigData().isEmpty());
        assertTrue(dataSet.getTransformData().isEmpty());
    }

    @Test
    public void testNoDataFileConstructorWithStrings() {
        String testName = "testName";
        String testProgram = "RADx-UP";
        String testStudyId = "testStudyId";
        DataSet dataSet = new DataSet(testName, testProgram, testStudyId);

        assertEquals(testName, dataSet.getName());
        assertEquals(Program.fromString(testProgram), dataSet.getProgram());
        assertEquals(testStudyId, dataSet.getStudyId().value());
        assertTrue(dataSet.getOrigData().isEmpty());
        assertTrue(dataSet.getTransformData().isEmpty());
    }

    @Test
    public void testSetOrigData() {
        String testName = "testName";
        String testProgram = "RADx-UP";
        String testStudyId = "testStudyId";
        DataSet dataSet = new DataSet(testName, testProgram, testStudyId);

        int testVersion1 = 1;
        HashSet<String> variableNames1 = new HashSet<>(Arrays.asList("var1", "var2"));
        DataFile origData1 = new DataFile(testVersion1, variableNames1);
        dataSet.setOrigData(origData1);

        assertTrue(dataSet.getOrigData().isPresent());
        assertEquals(origData1, dataSet.getOrigData().get());

        int testVersion2 = 2;
        HashSet<String> variableNames2 = new HashSet<>(Arrays.asList("var3", "var4"));
        DataFile origData2 = new DataFile(testVersion2, variableNames2);
        dataSet.setOrigData(origData2);

        assertTrue(dataSet.getOrigData().isPresent());
        assertEquals(origData2, dataSet.getOrigData().get());
        assertNotEquals(origData1, dataSet.getOrigData().get());
    }

    @Test
    public void testSetTransformData() {
        String testName = "testName";
        String testProgram = "RADx-UP";
        String testStudyId = "testStudyId";
        DataSet dataSet = new DataSet(testName, testProgram, testStudyId);

        int testVersion1 = 1;
        HashSet<String> variableNames1 = new HashSet<>(Arrays.asList("var1", "var2"));
        DataFile transformData1 = new DataFile(testVersion1, variableNames1);
        dataSet.setTransformData(transformData1);

        assertTrue(dataSet.getTransformData().isPresent());
        assertEquals(transformData1, dataSet.getTransformData().get());

        int testVersion2 = 2;
        HashSet<String> variableNames2 = new HashSet<>(Arrays.asList("var3", "var4"));
        DataFile transformData2 = new DataFile(testVersion2, variableNames2);
        dataSet.setTransformData(transformData2);

        assertTrue(dataSet.getTransformData().isPresent());
        assertEquals(transformData2, dataSet.getTransformData().get());
        assertNotEquals(transformData1, dataSet.getTransformData().get());
    }
}

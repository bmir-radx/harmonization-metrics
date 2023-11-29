package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertTrue;

public class TestDataSetMetrics {

    @Test
    public void testCreateMetricsMatched() {
        HarmonizationChecker checker = new HarmonizationChecker(new TestRules());

        String testName = "testName";
        String testProgram = "testProgram";
        String testStudyId = "testStudyId";
        DataSet dataSet = new DataSet(testName, testProgram, testStudyId);

        HashSet<String> origVariables = new HashSet<>(Arrays.asList("var1", "var2", "var4"));
        DataFile origData = new DataFile(1, origVariables);
        dataSet.setOrigData(origData);

        HashSet<String> transformVariables = new HashSet<>(Arrays.asList("harmonizedVar1", "harmonizedVar2", "var4"));
        DataFile transformData = new DataFile(1, transformVariables);
        dataSet.setTransformData(transformData);

        DataSetMetrics metrics = DataSetMetrics.createMetricsFromDataSet(dataSet, checker);
        assertTrue(metrics.hasOrig());
        assertTrue(metrics.hasTransform());
    }
}

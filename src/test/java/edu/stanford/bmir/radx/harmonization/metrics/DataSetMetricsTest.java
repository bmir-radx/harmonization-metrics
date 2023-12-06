package edu.stanford.bmir.radx.harmonization.metrics;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DataSetMetricsTest {

//    @Test
//    public void testCreateMetricsOrigOnly() {
//        HarmonizationChecker checker = new HarmonizationChecker(new TestRules());

//        String testName = "testName";
//        Program testProgram = Program.RADXUP;
//        StudyId testStudyId = StudyId.valueOf("testProgram");
//        OrigTransformFilePair dataSet = new OrigTransformFilePair(testName, testProgram, testStudyId);

//        HashSet<String> origVariables = new HashSet<>(Arrays.asList("var1", "var2", "var4"));
//        DataFile origFile = new DataFile(1, origVariables);
//        dataSet.setOrigData(origFile);

//        DataSetMetrics metrics = DataSetMetrics.createMetricsFromDataSet(dataSet, checker);
//        assertTrue(metrics.hasOrig());
//        assertFalse(metrics.hasTransform());
//        assertTrue(metrics.isHarmonizableOrig());
//        assertFalse(metrics.isPartiallyHarmonizedOrig());
//    }

//    @Test
//    public void testCreateMetricsMatched() {
//        HarmonizationChecker checker = new HarmonizationChecker(new TestRules());

//        String testName = "testName";
//        Program testProgram = Program.RADXUP;
//        StudyId testStudyId = StudyId.valueOf("testProgram");
//        OrigTransformFilePair dataSet = new OrigTransformFilePair(testName, testProgram, testStudyId);

//        HashSet<String> origVariables = new HashSet<>(Arrays.asList("var1", "var2", "var4"));
//        DataFile origFile = new DataFile(1, origVariables);
//        dataSet.setOrigData(origFile);

//        HashSet<String> transformVariables = new HashSet<>(Arrays.asList("harmonizedVar1", "harmonizedVar2", "var4"));
//        DataFile transformFile = new DataFile(1, transformVariables);
//        dataSet.setTransformData(transformFile);

//        DataSetMetrics metrics = DataSetMetrics.createMetricsFromDataSet(dataSet, checker);
//        assertTrue(metrics.hasOrig());
//        assertTrue(metrics.hasTransform());
//        assertTrue(metrics.isHarmonizedTransform());
//    }
}

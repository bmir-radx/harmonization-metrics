package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataFileProcessorTest {
    DataFileProcessor processor = new DataFileProcessor(
            new FileNameExtractor(),
            new VariableNameCleaner());

    private List<DataFileInput> generateDataInputForTest(String studyId) {
        String program = "RADx-UP";
        List<String> variables = Arrays.asList("var1", "var2", "var3");
        DataFileInput dataInput1 = new DataFileInput(
                String.format("%s_file1_DATA_origcopy_v1.csv", studyId),
                program, studyId, "orig", variables);
        DataFileInput dataInput2 = new DataFileInput(
                String.format("%s_file1_DATA_origcopy_v2.csv", studyId),
                program, studyId, "orig", variables);
        DataFileInput dataInput3 = new DataFileInput(
                String.format("%s_file1_DATA_transformcopy_v2.csv", studyId),
                program, studyId, "transform", variables);
        DataFileInput dataInput4 = new DataFileInput(
                String.format("%s_file1_DATA_transformcopy_v4.csv", studyId),
                program, studyId, "transform", variables);
        DataFileInput dataInput5 = new DataFileInput(
                String.format("%s_file5_DATA_transformcopy_v4.csv", studyId),
                program, studyId, "transform", variables);
        List<DataFileInput> dataFiles = Arrays.asList(dataInput1, dataInput2, dataInput3, dataInput4, dataInput5);
        return dataFiles;
    }

    @Test
    public void processDataFiles()
            throws InvalidOrigTransformCategoryException, InvalidProgramIdException,
            NoVersionNumberException {
        List<DataFileInput> inputDataFiles = generateDataInputForTest("study1");
        Map<ReducedFileName, OrigTransformFilePair> dataFilePairMap = processor.processDataFiles(inputDataFiles);
        ReducedFileName expectedKey1 = new ReducedFileName("study1_file1");
        ReducedFileName expectedKey2 = new ReducedFileName("study1_file5");
        assertEquals(2, dataFilePairMap.size());
        assertTrue(dataFilePairMap.containsKey(expectedKey1));
        assertTrue(dataFilePairMap.containsKey(expectedKey2));
    }

    @Test
    public void organizeFilePairsByStudy()
            throws NoVersionNumberException, InvalidOrigTransformCategoryException,
            InvalidProgramIdException {
        List<DataFileInput> inputDataFiles = new ArrayList<>();
        StudyId study1 = StudyId.valueOf("study1");
        StudyId study2 = StudyId.valueOf("study2");
        StudyId study3 = StudyId.valueOf("study3");
        inputDataFiles.addAll(generateDataInputForTest("study1"));
        inputDataFiles.addAll(generateDataInputForTest("study2"));
        inputDataFiles.addAll(generateDataInputForTest("study3"));
        Map<ReducedFileName, OrigTransformFilePair> dataFilePairMap = processor.processDataFiles(inputDataFiles);
        Map<StudyId, Study> studyMap = processor.organizeFilePairsByStudy(dataFilePairMap);
        assertEquals(3, studyMap.size());
        assertTrue(studyMap.containsKey(study1));
        assertTrue(studyMap.containsKey(study2));
        assertTrue(studyMap.containsKey(study3));
    }
}

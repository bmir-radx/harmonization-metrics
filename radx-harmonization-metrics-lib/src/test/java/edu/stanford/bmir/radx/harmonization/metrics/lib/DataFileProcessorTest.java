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

    private List<DataFileExternal> generateExternalDataForTest(String studyId) {
        String program = "RADx-UP";
        List<String> variables = Arrays.asList("var1", "var2", "var3");
        DataFileExternal dataExt1 = new DataFileExternal(
                String.format("%s_file1_DATA_origcopy_v1.csv", studyId),
                program, studyId, "orig", variables);
        DataFileExternal dataExt2 = new DataFileExternal(
                String.format("%s_file1_DATA_origcopy_v2.csv", studyId),
                program, studyId, "orig", variables);
        DataFileExternal dataExt3 = new DataFileExternal(
                String.format("%s_file1_DATA_transformcopy_v2.csv", studyId),
                program, studyId, "transform", variables);
        DataFileExternal dataExt4 = new DataFileExternal(
                String.format("%s_file1_DATA_transformcopy_v4.csv", studyId),
                program, studyId, "transform", variables);
        DataFileExternal dataExt5 = new DataFileExternal(
                String.format("%s_file5_DATA_transformcopy_v4.csv", studyId),
                program, studyId, "transform", variables);
        List<DataFileExternal> dataFiles = Arrays.asList(dataExt1, dataExt2, dataExt3, dataExt4, dataExt5);
        return dataFiles;
    }

    @Test
    public void processDataFiles()
            throws InvalidOrigTransformCategoryException, InvalidProgramIdException,
            NoVersionNumberException {
        List<DataFileExternal> externalDataFiles = generateExternalDataForTest("study1");
        Map<ReducedFileName, OrigTransformFilePair> dataFilePairMap = processor.processDataFiles(externalDataFiles);
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
        List<DataFileExternal> externalDataFiles = new ArrayList<>();
        StudyId study1 = StudyId.valueOf("study1");
        StudyId study2 = StudyId.valueOf("study2");
        StudyId study3 = StudyId.valueOf("study3");
        externalDataFiles.addAll(generateExternalDataForTest("study1"));
        externalDataFiles.addAll(generateExternalDataForTest("study2"));
        externalDataFiles.addAll(generateExternalDataForTest("study3"));
        Map<ReducedFileName, OrigTransformFilePair> dataFilePairMap = processor.processDataFiles(externalDataFiles);
        Map<StudyId, Study> studyMap = processor.organizeFilePairsByStudy(dataFilePairMap);
        assertEquals(3, studyMap.size());
        assertTrue(studyMap.containsKey(study1));
        assertTrue(studyMap.containsKey(study2));
        assertTrue(studyMap.containsKey(study3));

    }
}

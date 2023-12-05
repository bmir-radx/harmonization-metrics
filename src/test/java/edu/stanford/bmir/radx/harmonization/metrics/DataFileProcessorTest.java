package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DataFileProcessorTest {
    DataFileProcessor processor = new DataFileProcessor(
            new FileNameExtractor(),
            new VariableNameCleaner());

    private List<DataFileExternal> generateExternalDataForTest() {
        String program = "RADx-UP";
        String studyId = "study123";
        List<String> variables = Arrays.asList("var1", "var2", "var3");
        DataFileExternal dataExt1 = new DataFileExternal(
                "file1_DATA_origcopy_v1.csv", program, studyId, "orig", variables);
        DataFileExternal dataExt2 = new DataFileExternal(
                "file1_DATA_origcopy_v2.csv", program, studyId, "orig", variables);
        DataFileExternal dataExt3 = new DataFileExternal(
                "file1_DATA_transformcopy_v2.csv", program, studyId, "transform", variables);
        DataFileExternal dataExt4 = new DataFileExternal(
                "file1_DATA_transformcopy_v4.csv", program, studyId, "transform", variables);
        DataFileExternal dataExt5 = new DataFileExternal(
                "file5_DATA_transformcopy_v4.csv", program, studyId, "transform", variables);
        List<DataFileExternal> dataFiles = Arrays.asList(dataExt1, dataExt2, dataExt3, dataExt4, dataExt5);
        return dataFiles;
    }

    @Test
    public void testProcessDataFiles()
            throws InvalidOrigTransformIdentifierException, InvalidProgramIdentifierException, NoVersionNumberException {
        List<DataFileExternal> externalDataFiles = generateExternalDataForTest();
        Map<ReducedFileName, DataFilePair> dataFilePairMap = processor.processDataFiles(externalDataFiles);
        ReducedFileName expectedKey1 = new ReducedFileName("file1");
        ReducedFileName expectedKey2 = new ReducedFileName("file5");
        assertEquals(2, dataFilePairMap.size());
        assertTrue(dataFilePairMap.containsKey(expectedKey1));
        assertTrue(dataFilePairMap.containsKey(expectedKey2));
    }
}

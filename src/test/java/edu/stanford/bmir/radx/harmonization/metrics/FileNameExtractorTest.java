package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FileNameExtractorTest {

    private FileNameExtractor fileNameExtractor = new FileNameExtractor();

    @Test
    public void testExtractReducedFileName_orig() {
        ReducedFileName expectedName = ReducedFileName.valueOf("example");
        String test_file_name = "example_DATA_origcopy_v2.csv";
        ReducedFileName reducedName = fileNameExtractor.extractReducedFileName(test_file_name);
        assertEquals(expectedName, reducedName);
    }

    @Test
    public void testExtractReducedFileName_transform() {
        ReducedFileName expectedName = ReducedFileName.valueOf("example");
        String test_file_name = "example_DATA_transformcopy_v2.csv";
        ReducedFileName reducedName = fileNameExtractor.extractReducedFileName(test_file_name);
        assertEquals(expectedName, reducedName);
    }

    @Test
    public void testExtractReducedFileName_origAndTransform() {
        String test_transform_name = "example_DATA_transformcopy_v2.csv";
        ReducedFileName reducedNameTransform = fileNameExtractor.extractReducedFileName(test_transform_name);
        String test_orig_name = "example_DATA_origcopy_v2.csv";
        ReducedFileName reducedNameOrig = fileNameExtractor.extractReducedFileName(test_orig_name);
        assertEquals(reducedNameOrig, reducedNameTransform);
    }

    @Test
    public void testExtractReducedFileName_differentVersions() {
        String test_v3_name = "example_DATA_transformcopy_v3.csv";
        ReducedFileName reducedNameV3 = fileNameExtractor.extractReducedFileName(test_v3_name);
        String test_v7_name = "example_DATA_transformcopy_v7.csv";
        ReducedFileName reducedNameV7 = fileNameExtractor.extractReducedFileName(test_v7_name);
        assertEquals(reducedNameV7, reducedNameV3);
    }

    @Test
    public void testExtractVersion() throws NoVersionNumberException {
        int expectedVersion = 3;
        String test_file_name = "example_DATA_origcopy_v3.csv";
        int version = fileNameExtractor.extractVersion(test_file_name);
        assertEquals(expectedVersion, version);
    }

    @Test(expected = NoVersionNumberException.class)
    public void testExtractVersion_noVersion() throws NoVersionNumberException {
        String test_file_name = "example_DATA_origcopy.csv";
        fileNameExtractor.extractVersion(test_file_name);
    }
}
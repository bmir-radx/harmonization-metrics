package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestDataFileCategory {
    @Test
    public void testValidCategoriesFromString() throws InvalidDataFileCategoryException {
        assertEquals(DataFileCategory.ORIG, DataFileCategory.fromString("orig"));
        assertEquals(DataFileCategory.TRANSFORM, DataFileCategory.fromString("transform"));
    }

    @Test(expected = InvalidDataFileCategoryException.class)
    public void testInvalidProgramsFromString() throws InvalidDataFileCategoryException {
        DataFileCategory.fromString("test");
    }
}

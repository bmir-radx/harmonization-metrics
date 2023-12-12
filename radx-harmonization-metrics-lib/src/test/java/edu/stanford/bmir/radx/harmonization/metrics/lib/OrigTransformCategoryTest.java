package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrigTransformCategoryTest {
    @Test
    public void testValidCategoriesFromString() throws InvalidOrigTransformCategoryException {
        assertEquals(OrigTransformCategory.ORIG,
                OrigTransformCategory.fromString("orig"));
        assertEquals(OrigTransformCategory.TRANSFORM,
                OrigTransformCategory.fromString("transform"));
    }

    @Test(expected = InvalidOrigTransformCategoryException.class)
    public void testInvalidProgramsFromString() throws InvalidOrigTransformCategoryException {
        OrigTransformCategory.fromString("test");
    }
}

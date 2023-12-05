package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrigTransformIdentifierTest {
    @Test
    public void testValidCategoriesFromString() throws InvalidOrigTransformIdentifierException {
        assertEquals(OrigTransformIdentifier.ORIG,
                OrigTransformIdentifier.fromString("orig"));
        assertEquals(OrigTransformIdentifier.TRANSFORM,
                OrigTransformIdentifier.fromString("transform"));
    }

    @Test(expected = InvalidOrigTransformIdentifierException.class)
    public void testInvalidProgramsFromString() throws InvalidOrigTransformIdentifierException {
        OrigTransformIdentifier.fromString("test");
    }
}

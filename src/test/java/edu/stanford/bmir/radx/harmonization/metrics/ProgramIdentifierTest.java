package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProgramIdentifierTest {

    @Test
    public void testValidProgramsFromString() throws InvalidProgramIdentifierException {
        assertEquals(ProgramIdentifier.RADXUP, ProgramIdentifier.fromString("RADx-UP"));
        assertEquals(ProgramIdentifier.RADXRAD, ProgramIdentifier.fromString("RADx-rad"));
        assertEquals(ProgramIdentifier.RADXTECH, ProgramIdentifier.fromString("RADx-Tech"));
        assertEquals(ProgramIdentifier.DHT, ProgramIdentifier.fromString("Digital Health Technologies"));
    }

    @Test(expected = InvalidProgramIdentifierException.class)
    public void testInvalidProgramsFromString() throws InvalidProgramIdentifierException {
        ProgramIdentifier.fromString("test");
    }
}

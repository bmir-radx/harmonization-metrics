package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ProgramIdTest {

    @Test
    public void testValidProgramsFromString() throws InvalidProgramIdException {
        assertEquals(ProgramId.RADXUP, ProgramId.fromString("RADx-UP"));
        assertEquals(ProgramId.RADXRAD, ProgramId.fromString("RADx-rad"));
        assertEquals(ProgramId.RADXTECH, ProgramId.fromString("RADx Tech"));
        assertEquals(ProgramId.DHT, ProgramId.fromString("Digital Health Technologies"));
    }

    @Test(expected = InvalidProgramIdException.class)
    public void testInvalidProgramsFromString() throws InvalidProgramIdException {
        ProgramId.fromString("test");
    }
}

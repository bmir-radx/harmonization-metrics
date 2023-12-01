package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TestProgram {

    @Test
    public void testValidProgramsFromString() throws InvalidProgramException {
        assertEquals(Program.RADXUP, Program.fromString("RADx-UP"));
        assertEquals(Program.RADXRAD, Program.fromString("RADx-rad"));
        assertEquals(Program.RADXTECH, Program.fromString("RADx-Tech"));
        assertEquals(Program.DHT, Program.fromString("Digital Health Technologies"));
    }

    @Test(expected = InvalidProgramException.class)
    public void testInvalidProgramsFromString() throws InvalidProgramException {
        Program.fromString("test");
    }
}

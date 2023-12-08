package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class OrigFileTest {
    @Test
    public void testGetVersion() {
        int version = 1;
        HashSet<String> variableNames = new HashSet<>();
        OrigFile origFile = new OrigFile("filename", version, variableNames);

        assertEquals(version, origFile.version());
    }

    @Test
    public void testGetVariableNames() {
        int version = 1;
        HashSet<String> variableNames = new HashSet<>(Arrays.asList("var1", "var2"));
        OrigFile origFile = new OrigFile("filename", version, variableNames);

        assertEquals(variableNames, origFile.variableNames());
    }
}

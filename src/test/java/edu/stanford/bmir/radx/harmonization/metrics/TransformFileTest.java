package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class TransformFileTest {
    @Test
    public void testGetVersion() {
        int version = 1;
        HashSet<String> variableNames = new HashSet<>();
        TransformFile transformFile = new TransformFile("filename", version, variableNames);

        assertEquals(version, transformFile.version());
    }

    @Test
    public void testGetVariableNames() {
        int version = 1;
        HashSet<String> variableNames = new HashSet<>(Arrays.asList("var1", "var2"));
        TransformFile transformFile = new TransformFile("filename", version, variableNames);

        assertEquals(variableNames, transformFile.variableNames());
    }
}

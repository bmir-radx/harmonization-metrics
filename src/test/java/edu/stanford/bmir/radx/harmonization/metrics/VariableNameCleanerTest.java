package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class VariableNameCleanerTest {

    private VariableNameCleaner variableNameCleaner = new VariableNameCleaner();

    @Test
    public void testCleanVariableName_oneHotEncoding() {
        String expectedVariableName = "variable_name";
        String testVariableName = "variable_name___3";
        String cleanedVariableName = variableNameCleaner.cleanVariableName(testVariableName);
        assertEquals(expectedVariableName, cleanedVariableName);
    }

    @Test
    public void testCleanVariableName_version() {
        String expectedVariableName = "variable_name";
        String testVariableName = "variable_name_6";
        String cleanedVariableName = variableNameCleaner.cleanVariableName(testVariableName);
        assertEquals(expectedVariableName, cleanedVariableName);
    }

    @Test
    public void testCleanVariableName_versionAndOneHotEncoding() {
        String expectedVariableName = "variable_name";
        String testVariableName = "variable_name_6___3";
        String cleanedVariableName = variableNameCleaner.cleanVariableName(testVariableName);
        assertEquals(expectedVariableName, cleanedVariableName);
    }
}
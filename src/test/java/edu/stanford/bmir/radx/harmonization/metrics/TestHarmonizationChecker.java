package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestHarmonizationChecker {

    @Test
    public void testCountHarmonizableElements() {
        String program = "testProgram";
        HarmonizationRules rules = new TestRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> nonHarmonizedVariables = new HashSet<>(Arrays.asList("var1", "var2", "var4"));
        int nHarmonizableElements = checker.countHarmonizableElements(program, nonHarmonizedVariables);
        assertEquals(2, nHarmonizableElements);
        assertNotEquals(nonHarmonizedVariables.size(), nHarmonizableElements);
    }

    @Test
    public void testCountHarmonizedElements() {
        String program = "testProgram";
        HarmonizationRules rules = new TestRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> variables = new HashSet<>(Arrays.asList("var1", "harmonizedVar2", "harmonizedVar3"));
        int nHarmonizedElements = checker.countHarmonizedElements(program, variables);
        assertEquals(2, nHarmonizedElements);
        assertNotEquals(variables.size(), nHarmonizedElements);
    }

    @Test
    public void testHarmonizationCounts() {
        String program = "testProgram";
        HarmonizationRules rules = new TestRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> variables = new HashSet<>(Arrays.asList("var1", "harmonizedVar2", "harmonizedVar3", "var4"));
        int nHarmonizableElements = checker.countHarmonizableElements(program, variables);
        int nHarmonizedElements = checker.countHarmonizedElements(program, variables);
        assertEquals(1, nHarmonizableElements);
        assertEquals(2, nHarmonizedElements);
    }

    @Test
    public void testHarmonizationCountsInvalidProgram() {
        // testProgram2 is not a recognized program in the harmonization rules
        String program = "testProgram2";
        HarmonizationRules rules = new TestRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> variables = new HashSet<>(Arrays.asList("var1", "harmonizedVar2", "harmonizedVar3", "var4"));
        int nHarmonizableElements = checker.countHarmonizableElements(program, variables);
        int nHarmonizedElements = checker.countHarmonizedElements(program, variables);
        assertEquals(0, nHarmonizableElements);
        assertEquals(0, nHarmonizedElements);
    }
}

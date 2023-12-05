package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HarmonizationCheckerTest {

    @Test
    public void testCountHarmonizableElements() throws InvalidProgramIdentifierException {
        ProgramIdentifier programIdentifier = ProgramIdentifier.RADXUP;
        HarmonizationRules rules = new MockRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> nonHarmonizedVariables = new HashSet<>(Arrays.asList("var1", "var2", "var4"));
        int nHarmonizableElements = checker.countHarmonizableElements(programIdentifier, nonHarmonizedVariables);
        assertEquals(2, nHarmonizableElements);
        assertNotEquals(nonHarmonizedVariables.size(), nHarmonizableElements);
    }

    @Test
    public void testCountHarmonizedElements() throws InvalidProgramIdentifierException {
        ProgramIdentifier programIdentifier = ProgramIdentifier.RADXUP;
        HarmonizationRules rules = new MockRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> variables = new HashSet<>(Arrays.asList("var1", "harmonizedVar2", "harmonizedVar3"));
        int nHarmonizedElements = checker.countHarmonizedElements(programIdentifier, variables);
        assertEquals(2, nHarmonizedElements);
        assertNotEquals(variables.size(), nHarmonizedElements);
    }

    @Test
    public void testHarmonizationCounts() throws InvalidProgramIdentifierException {
        ProgramIdentifier programIdentifier = ProgramIdentifier.RADXUP;
        HarmonizationRules rules = new MockRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> variables = new HashSet<>(Arrays.asList("var1", "harmonizedVar2", "harmonizedVar3", "var4"));
        int nHarmonizableElements = checker.countHarmonizableElements(programIdentifier, variables);
        int nHarmonizedElements = checker.countHarmonizedElements(programIdentifier, variables);
        assertEquals(1, nHarmonizableElements);
        assertEquals(2, nHarmonizedElements);
    }
}

package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HarmonizationCheckerTest {

    @Test
    public void testCountHarmonizableElements() throws InvalidProgramIdException {
        ProgramId programId = ProgramId.RADXUP;
        HarmonizationRules rules = new MockRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> nonHarmonizedVariables = new HashSet<>(Arrays.asList("var1", "var2", "var4"));
        int nHarmonizableElements = checker.countHarmonizableElements(programId, nonHarmonizedVariables);
        assertEquals(2, nHarmonizableElements);
        assertNotEquals(nonHarmonizedVariables.size(), nHarmonizableElements);
    }

    @Test
    public void testCountHarmonizedElements() throws InvalidProgramIdException {
        ProgramId programId = ProgramId.RADXUP;
        HarmonizationRules rules = new MockRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> variables = new HashSet<>(Arrays.asList("var1", "harmonizedVar2", "harmonizedVar3"));
        int nHarmonizedElements = checker.countHarmonizedElements(programId, variables);
        assertEquals(2, nHarmonizedElements);
        assertNotEquals(variables.size(), nHarmonizedElements);
    }

    @Test
    public void testHarmonizationCounts() throws InvalidProgramIdException {
        ProgramId programId = ProgramId.RADXUP;
        HarmonizationRules rules = new MockRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> variables = new HashSet<>(Arrays.asList("var1", "harmonizedVar2", "harmonizedVar3", "var4"));
        int nHarmonizableElements = checker.countHarmonizableElements(programId, variables);
        int nHarmonizedElements = checker.countHarmonizedElements(programId, variables);
        assertEquals(1, nHarmonizableElements);
        assertEquals(2, nHarmonizedElements);
    }
}

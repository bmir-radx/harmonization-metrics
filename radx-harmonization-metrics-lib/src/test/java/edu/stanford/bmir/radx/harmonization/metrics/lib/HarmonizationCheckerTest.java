package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class HarmonizationCheckerTest {

    private Set<String> generateHarmonizableVariables() {
        return new HashSet<>(
                Arrays.asList("t1Var1", "t1Var2", "t1Var3", "t2Var1", "t2Var2", "t3Var1", "trash"));
    }

    private Set<String> generateHarmonizedVariables() {
        Set<String> variables = new HashSet<>();
        variables.add("harmonizedT1Var1");
        variables.add("harmonizedT1Var2");
        variables.add("harmonizedT1Var3");
        variables.add("harmonizedT2Var1");
        variables.add("harmonizedT2Var2");
        variables.add("harmonizedT3Var3");
        variables.add("trash");
        return variables;
    }

    private Set<String> generateMixedVariables() {
        Set<String> variables = generateHarmonizableVariables();
        variables.addAll(generateHarmonizedVariables());
        return variables;
    }

    @Test
    public void testCountHarmonizableElements()
            throws InvalidProgramIdException, InvalidHarmonizationTierException {
        ProgramId programId = ProgramId.DHT;
        HarmonizationRules rules = new MockRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        Set<String> variables = generateHarmonizableVariables();
        int nHarmonizableTier1 = checker.countHarmonizableElements(
                programId, variables, HarmonizationTier.TIER1);
        int nHarmonizableTier2 = checker.countHarmonizableElements(
                programId, variables, HarmonizationTier.TIER2);
        int nHarmonizableTier3 = checker.countHarmonizableElements(
                programId, variables, HarmonizationTier.TIER3);
        assertEquals(3, nHarmonizableTier1);
        assertEquals(2, nHarmonizableTier2);
        assertEquals(1, nHarmonizableTier3);
    }

    @Test
    public void testCountHarmonizedElements()
            throws InvalidProgramIdException, InvalidHarmonizationTierException {
        ProgramId programId = ProgramId.DHT;
        HarmonizationRules rules = new MockRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        Set<String> variables = generateHarmonizedVariables();
        int nHarmonizedTier1 = checker.countHarmonizedElements(
                programId, variables, HarmonizationTier.TIER1);
        int nHarmonizedTier2 = checker.countHarmonizedElements(
                programId, variables, HarmonizationTier.TIER2);
        int nHarmonizedTier3 = checker.countHarmonizedElements(
                programId, variables, HarmonizationTier.TIER3);
        assertEquals(3, nHarmonizedTier1);
        assertEquals(2, nHarmonizedTier2);
        assertEquals(1, nHarmonizedTier3);
    }

    @Test
    public void testHarmonizationCounts()
            throws InvalidProgramIdException, InvalidHarmonizationTierException {
        ProgramId programId = ProgramId.DHT;
        HarmonizationRules rules = new MockRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        Set<String> variables = generateMixedVariables();
        int nHarmonizableTier1 = checker.countHarmonizableElements(
                programId, variables, HarmonizationTier.TIER1);
        int nHarmonizableTier2 = checker.countHarmonizableElements(
                programId, variables, HarmonizationTier.TIER2);
        int nHarmonizableTier3 = checker.countHarmonizableElements(
                programId, variables, HarmonizationTier.TIER3);
        int nHarmonizedTier1 = checker.countHarmonizedElements(
                programId, variables, HarmonizationTier.TIER1);
        int nHarmonizedTier2 = checker.countHarmonizedElements(
                programId, variables, HarmonizationTier.TIER2);
        int nHarmonizedTier3 = checker.countHarmonizedElements(
                programId, variables, HarmonizationTier.TIER3);
        assertEquals(6, nHarmonizableTier1);
        assertEquals(4, nHarmonizableTier2);
        assertEquals(2, nHarmonizableTier3);
        assertEquals(3, nHarmonizedTier1);
        assertEquals(2, nHarmonizedTier2);
        assertEquals(1, nHarmonizedTier3);
    }
}

package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestHarmonizationChecker {

    /*
    Implementation of dumb HarmonizationRules based on a hash map
    used to test the HarmonizationChecker
     */
    private class TestRules implements HarmonizationRules {
        private HashMap<String, String> map;

        public TestRules() {
            map = new HashMap<>();
            map.put("var1", "harmonizedVar1");
            map.put("var2", "harmonizedVar2");
            map.put("var3", "harmonizedVar3");
        }

        public boolean isHarmonizable(String element) {
            return map.containsKey(element);
        }
        public boolean isHarmonized(String element) {
            return map.containsValue(element);
        }
    }

    @Test
    public void testCountHarmonizableElements() {
        HarmonizationRules rules = new TestRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> nonHarmonizedVariables = new HashSet<>(Arrays.asList("var1", "var2", "var4"));
        int nHarmonizableElements = checker.countHarmonizableElements(nonHarmonizedVariables);
        assertEquals(2, nHarmonizableElements);
        assertNotEquals(nonHarmonizedVariables.size(), nHarmonizableElements);
    }

    @Test
    public void testCountHarmonizedElements() {
        HarmonizationRules rules = new TestRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> variables = new HashSet<>(Arrays.asList("var1", "harmonizedVar2", "harmonizedVar3"));
        int nHarmonizedElements = checker.countHarmonizedElements(variables);
        assertEquals(2, nHarmonizedElements);
        assertNotEquals(variables.size(), nHarmonizedElements);
    }

    @Test
    public void testHarmonizationCounts() {
        HarmonizationRules rules = new TestRules();
        HarmonizationChecker checker = new HarmonizationChecker(rules);

        HashSet<String> variables = new HashSet<>(Arrays.asList("var1", "harmonizedVar2", "harmonizedVar3", "var4"));
        int nHarmonizableElements = checker.countHarmonizableElements(variables);
        int nHarmonizedElements = checker.countHarmonizedElements(variables);
        assertEquals(1, nHarmonizableElements);
        assertEquals(2, nHarmonizedElements);
    }
}

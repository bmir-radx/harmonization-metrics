package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashMap;

/*
Implementation of dumb HarmonizationRules based on a hash map
used to test the HarmonizationChecker
 */
public class TestRules implements HarmonizationRules {
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


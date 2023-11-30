package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashMap;
import java.util.Map;

/*
Implementation of dumb HarmonizationRules based on a hash map
used to test the HarmonizationChecker
 */
public class TestRules implements HarmonizationRules {
    private Map<String, Map<String, String>> map;

    public TestRules() {
        Map<String, String> programMap = new HashMap<>();
        programMap.put("var1", "harmonizedVar1");
        programMap.put("var2", "harmonizedVar2");
        programMap.put("var3", "harmonizedVar3");

        map = new HashMap<>();
        map.put("testProgram", programMap);
    }

    public boolean isHarmonizable(String program, String element) {
        if (map.containsKey(program)) {
            return map.get(program).containsKey(element);
        } else {
            return false;
        }
    }
    public boolean isHarmonized(String program, String element) {
        if (map.containsKey(program)) {
            return map.get(program).containsValue(element);
        } else {
            return false;
        }
    }
}


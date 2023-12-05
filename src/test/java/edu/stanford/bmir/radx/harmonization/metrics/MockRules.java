package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashMap;
import java.util.Map;

/*
Implementation of dumb HarmonizationRules based on a hash map
used to test the HarmonizationChecker
 */
public class MockRules implements HarmonizationRules {
    private Map<ProgramIdentifier, Map<String, String>> map;

    public MockRules() {
        Map<String, String> programMap = new HashMap<>();
        programMap.put("var1", "harmonizedVar1");
        programMap.put("var2", "harmonizedVar2");
        programMap.put("var3", "harmonizedVar3");

        map = new HashMap<>();
        map.put(ProgramIdentifier.RADXUP, programMap);
    }

    public boolean isHarmonizable(ProgramIdentifier programIdentifier, String element) {
        if (map.containsKey(programIdentifier)) {
            return map.get(programIdentifier).containsKey(element);
        } else {
            return false;
        }
    }

    public boolean isHarmonized(ProgramIdentifier programIdentifier, String element) {
        if (map.containsKey(programIdentifier)) {
            return map.get(programIdentifier).containsValue(element);
        } else {
            return false;
        }
    }
}


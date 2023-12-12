package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.HashMap;
import java.util.Map;

/*
Implementation of dumb HarmonizationRules based on a hash map
used to test the HarmonizationChecker
 */
public class MockRules implements HarmonizationRules {
    private Map<ProgramId, Map<String, String>> map;

    public MockRules() {
        Map<String, String> programMap = new HashMap<>();
        programMap.put("var1", "harmonizedVar1");
        programMap.put("var2", "harmonizedVar2");
        programMap.put("var3", "harmonizedVar3");

        map = new HashMap<>();
        map.put(ProgramId.RADXUP, programMap);
    }

    public boolean isHarmonizable(ProgramId programId, String element) {
        if (map.containsKey(programId)) {
            return map.get(programId).containsKey(element);
        } else {
            return false;
        }
    }

    public boolean isHarmonized(ProgramId programId, String element) {
        if (map.containsKey(programId)) {
            return map.get(programId).containsValue(element);
        } else {
            return false;
        }
    }
}


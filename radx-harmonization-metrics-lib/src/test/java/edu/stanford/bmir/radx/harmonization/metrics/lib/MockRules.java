package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.HashMap;
import java.util.Map;

/*
Implementation of dumb HarmonizationRules based on a hash map
used to test the HarmonizationChecker
 */
public class MockRules implements HarmonizationRules {
    private final Map<ProgramId, Map<String, String>> tier1Map;
    private final Map<ProgramId, Map<String, String>> tier2Map;
    private final Map<ProgramId, Map<String, String>> tier3Map;

    public MockRules() {
        Map<String, String> tier1 = new HashMap<>();
        tier1.put("t1Var1", "harmonizedT1Var1");
        tier1.put("t1Var2", "harmonizedT1Var2");
        tier1.put("t1Var3", "harmonizedT1Var3");
        Map<ProgramId, Map<String, String>> t1Map = new HashMap<>();
        t1Map.put(ProgramId.DHT, tier1);
        tier1Map = t1Map;

        Map<String, String> tier2 = new HashMap<>();
        tier2.put("t2Var1", "harmonizedT2Var1");
        tier2.put("t2Var2", "harmonizedT2Var2");
        tier2.put("t2Var3", "harmonizedT2Var3");
        Map<ProgramId, Map<String, String>> t2Map = new HashMap<>();
        t2Map.put(ProgramId.DHT, tier2);
        tier2Map = t2Map;

        Map<String, String> tier3 = new HashMap<>();
        tier3.put("t3Var1", "harmonizedT3Var1");
        tier3.put("t3Var2", "harmonizedT3Var2");
        tier3.put("t3Var3", "harmonizedT3Var3");
        Map<ProgramId, Map<String, String>> t3Map = new HashMap<>();
        t3Map.put(ProgramId.DHT, tier3);
        tier3Map = t3Map;
    }

    public boolean isHarmonizable(ProgramId programId, String element, HarmonizationTier tier) {
        Map<String, String> programMap;
        switch (tier) {
            case TIER1:
                programMap = tier1Map.get(programId);
                break;
            case TIER2:
                programMap = tier2Map.get(programId);
                break;
            case TIER3:
            default:
                programMap = tier3Map.get(programId);
        }
        return programMap.containsKey(element) || programMap.containsValue(element);
    }

    public boolean isHarmonized(ProgramId programId, String element, HarmonizationTier tier) {
        Map<String, String> programMap;
        switch (tier) {
            case TIER1:
                programMap = tier1Map.get(programId);
                break;
            case TIER2:
                programMap = tier2Map.get(programId);
                break;
            case TIER3:
            default:
                programMap = tier3Map.get(programId);
        }
        return programMap.containsValue(element);
    }
}


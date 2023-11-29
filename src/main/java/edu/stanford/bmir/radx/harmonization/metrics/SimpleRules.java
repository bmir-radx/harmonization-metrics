package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.HashMap;

public class SimpleRules implements HarmonizationRules {

    HashMap<String, String> map;

    public boolean isHarmonizable(String element) {
        return map.containsKey(element);
    }

    public boolean isHarmonized(String element) {
        return map.containsValue(element);
    }

    public SimpleRules() {

    }
}

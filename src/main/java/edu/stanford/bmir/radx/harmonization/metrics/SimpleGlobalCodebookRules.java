package edu.stanford.bmir.radx.harmonization.metrics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SimpleGlobalCodebookRules implements HarmonizationRules {

    private Map<Program, Map<String, String>> map;

    public boolean isHarmonizable(Program program, String element) {
        return map.containsKey(element);
    }

    public boolean isHarmonized(Program program, String element) {
        return map.containsValue(element);
    }

    public SimpleGlobalCodebookRules() throws IOException {
        map = readJsonToMap("global_codebook_rules.json");
    }

    public Map<Program, Map<String, String>> getRules() {
        return map;
    }

    private Map<Program, Map<String, String>> readJsonToMap(String fileName) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        String jsonString = new String(is.readAllBytes());
        JsonNode tree = mapper.readTree(jsonString);
        Map<String, Map<String, String>> map = mapper.convertValue(tree, Map.class);

        Map<Program, Map<String, String>> codebook = new HashMap<>();
        for (String key: map.keySet()) {
            System.out.println(Program.fromString(key));
            codebook.put(Program.fromString(key), map.get(key));
        }

        return codebook;
    }
}

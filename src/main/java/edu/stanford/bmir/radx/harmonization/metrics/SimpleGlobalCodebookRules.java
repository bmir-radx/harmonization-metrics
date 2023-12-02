package edu.stanford.bmir.radx.harmonization.metrics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/*
This is a simple implementation of Harmonization mappings to get this
library off the group. Further work on representing the Harmonization
rules programmatically will improve the accuracy of metrics computed.
 */
public class SimpleGlobalCodebookRules implements HarmonizationRules {

    private Map<Program, Map<String, String>> map;

    public boolean isHarmonizable(Program program, String element) throws InvalidProgramException {
        if (!map.containsKey(program)) {
            throw new InvalidProgramException(program.toString());
        }
        return map.get(program).containsKey(element);
    }

    public boolean isHarmonized(Program program, String element) throws InvalidProgramException {
        if (!map.containsKey(program)) {
            throw new InvalidProgramException(program.toString());
        }
        return map.get(program).containsValue(element);
    }

    public SimpleGlobalCodebookRules() throws IOException, InvalidProgramException {
        map = readJsonToMap("global_codebook_rules.json");
    }

    public Map<Program, Map<String, String>> getRules() {
        return map;
    }

    private Map<Program, Map<String, String>> readJsonToMap(String fileName)
            throws IOException, InvalidProgramException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        String jsonString = new String(is.readAllBytes());
        JsonNode tree = mapper.readTree(jsonString);
        Map<String, Map<String, String>> map = mapper.convertValue(tree, Map.class);

        Map<Program, Map<String, String>> codebook = new HashMap<>();
        for (String key: map.keySet()) {
            codebook.put(Program.fromString(key), map.get(key));
        }

        return codebook;
    }
}

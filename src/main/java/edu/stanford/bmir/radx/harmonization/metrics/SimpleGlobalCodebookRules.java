package edu.stanford.bmir.radx.harmonization.metrics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/*
This is a simple implementation of Harmonization mappings to get this
library off the group. Further work on representing the Harmonization
rules programmatically will improve the accuracy of metrics computed.
 */
@Component
public class SimpleGlobalCodebookRules implements HarmonizationRules {

    private final Map<ProgramIdentifier, Map<String, String>> map;

    public boolean isHarmonizable(ProgramIdentifier programIdentifier, String element) throws InvalidProgramIdentifierException {
        if (!map.containsKey(programIdentifier)) {
            throw new InvalidProgramIdentifierException(programIdentifier.toString());
        }
        return map.get(programIdentifier).containsKey(element);
    }

    public boolean isHarmonized(ProgramIdentifier programIdentifier, String element) throws InvalidProgramIdentifierException {
        if (!map.containsKey(programIdentifier)) {
            throw new InvalidProgramIdentifierException(programIdentifier.toString());
        }
        return map.get(programIdentifier).containsValue(element);
    }

    public SimpleGlobalCodebookRules() throws IOException, InvalidProgramIdentifierException {
        map = readJsonToMap("global_codebook_rules.json");
    }

    private Map<ProgramIdentifier, Map<String, String>> readJsonToMap(String fileName)
            throws IOException, InvalidProgramIdentifierException {
        ObjectMapper mapper = new ObjectMapper();
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        String jsonString = new String(is.readAllBytes());
        JsonNode tree = mapper.readTree(jsonString);
        Map<String, Map<String, String>> map = mapper.convertValue(tree, Map.class);

        Map<ProgramIdentifier, Map<String, String>> codebook = new HashMap<>();
        for (String key: map.keySet()) {
            codebook.put(ProgramIdentifier.fromString(key), map.get(key));
        }

        return codebook;
    }
}

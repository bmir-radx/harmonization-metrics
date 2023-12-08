package edu.stanford.bmir.radx.harmonization.metrics.lib;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
This is a simple implementation of Harmonization mappings to get this
library off the group. Further work on representing the Harmonization
rules programmatically will improve the accuracy of metrics computed.
 */
@Component
public class SimpleHashRules implements HarmonizationRules {

    private final Map<ProgramIdentifier, Map<String, String>> tier1Map;
    private final Map<ProgramIdentifier, Set<String>> tier2Map;
    private final ObjectMapper mapper = new ObjectMapper();

    public boolean isHarmonizable(ProgramIdentifier programIdentifier, String element) throws InvalidProgramIdentifierException {
        if (!(tier1Map.containsKey(programIdentifier) && tier2Map.containsKey(programIdentifier))) {
            throw new InvalidProgramIdentifierException(programIdentifier.toString());
        }
        return tier1Map.get(programIdentifier).containsKey(element);
    }

    public boolean isHarmonized(ProgramIdentifier programIdentifier, String element) throws InvalidProgramIdentifierException {
        if (!(tier1Map.containsKey(programIdentifier) && tier2Map.containsKey(programIdentifier))) {
            throw new InvalidProgramIdentifierException(programIdentifier.toString());
        }
        return tier1Map.get(programIdentifier).containsKey(element) ||
                tier2Map.get(programIdentifier).contains(element);
    }

    public SimpleHashRules() throws IOException, InvalidProgramIdentifierException {
        tier1Map = readJsonToTier1Map("global_codebook_rules.json");
        tier2Map = readJsonToTier2Map("tier2_elements.json");
    }

    private Map<ProgramIdentifier, Map<String, String>> readJsonToTier1Map(String fileName)
            throws IOException, InvalidProgramIdentifierException {
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

     private Map<ProgramIdentifier, Set<String>> readJsonToTier2Map(String fileName)
             throws IOException, InvalidProgramIdentifierException {
         InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
         String jsonString = new String(is.readAllBytes());
         JsonNode tree = mapper.readTree(jsonString);
         Map<String, List<String>> map = mapper.convertValue(tree, Map.class);

         Map<ProgramIdentifier, Set<String>> lookupTable = new HashMap<>();
         for (String key: map.keySet()) {
             lookupTable.put(ProgramIdentifier.fromString(key), new HashSet<>(map.get(key)));
         }

         return lookupTable;
     }
}

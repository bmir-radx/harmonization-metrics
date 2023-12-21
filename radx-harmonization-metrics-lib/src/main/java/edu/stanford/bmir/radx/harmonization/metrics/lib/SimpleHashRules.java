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

    private final Map<ProgramId, Map<String, String>> tier1Map;
    private final Map<ProgramId, Set<String>> tier2Map;
    private final Map<ProgramId, Set<String>> tier3Map;
    private final ObjectMapper mapper = new ObjectMapper();

    public boolean isHarmonizable(ProgramId programId, String element, HarmonizationTier tier)
            throws InvalidProgramIdException, InvalidHarmonizationTierException {
        // the set of harmonizable elements contains the set of harmonized elements
        switch (tier) {
            case TIER1:
                if (!tier1Map.containsKey(programId)) {
                    throw new InvalidProgramIdException(programId.toString());
                }
                Map<String, String> programMap = tier1Map.get(programId);
                return programMap.containsKey(element) || programMap.containsValue(element);
            case TIER2:
                if (!tier2Map.containsKey(programId)) {
                    throw new InvalidProgramIdException(programId.toString());
                }
                return tier2Map.get(programId).contains(element);
            case TIER3:
                return false;
            default:
                throw new InvalidHarmonizationTierException(tier.toString());
        }
    }

    public boolean isHarmonized(ProgramId programId, String element, HarmonizationTier tier)
            throws InvalidProgramIdException, InvalidHarmonizationTierException {
        switch (tier) {
            case TIER1:
                if (!tier1Map.containsKey(programId)) {
                    throw new InvalidProgramIdException(programId.toString());
                }
                return tier1Map.get(programId).containsValue(element);
            case TIER2:
                if (!tier2Map.containsKey(programId)) {
                    throw new InvalidProgramIdException(programId.toString());
                }
                return tier2Map.get(programId).contains(element);
            case TIER3:
                return false;
            default:
                throw new InvalidHarmonizationTierException(tier.toString());
        }
    }

    public SimpleHashRules() throws IOException, InvalidProgramIdException {
        tier1Map = readJsonToTier1Map("global_codebook_rules.json");
        tier2Map = readJsonToTier2Map("tier2_elements.json");
        tier3Map = new HashMap<>();
    }

    private Map<ProgramId, Map<String, String>> readJsonToTier1Map(String fileName)
            throws IOException, InvalidProgramIdException {
        InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
        String jsonString = new String(is.readAllBytes());
        JsonNode tree = mapper.readTree(jsonString);
        Map<String, Map<String, String>> map = mapper.convertValue(tree, Map.class);

        Map<ProgramId, Map<String, String>> codebook = new HashMap<>();
        for (String key: map.keySet()) {
            codebook.put(ProgramId.fromString(key), map.get(key));
        }

        return codebook;
    }

     private Map<ProgramId, Set<String>> readJsonToTier2Map(String fileName)
             throws IOException, InvalidProgramIdException {
         InputStream is = getClass().getClassLoader().getResourceAsStream(fileName);
         String jsonString = new String(is.readAllBytes());
         JsonNode tree = mapper.readTree(jsonString);
         Map<String, List<String>> map = mapper.convertValue(tree, Map.class);

         Map<ProgramId, Set<String>> lookupTable = new HashMap<>();
         for (String key: map.keySet()) {
             lookupTable.put(ProgramId.fromString(key), new HashSet<>(map.get(key)));
         }

         return lookupTable;
     }
}

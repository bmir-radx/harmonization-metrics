package edu.stanford.bmir.radx.harmonization.metrics;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class TrialDataProcessor {

    private final List<DataFileExternal> dataFiles;
    private final ObjectMapper mapper = new ObjectMapper();

    public TrialDataProcessor() throws IOException {
        dataFiles = readJsonToList("trial_data_files.json");
    }

    public List<DataFileExternal> getDataFiles() {
        return dataFiles;
    }

    private List<DataFileExternal> readJsonToList(String fileName) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
        String jsonString = new String(inputStream.readAllBytes());
        JsonNode tree = mapper.readTree(jsonString);

        List<DataFileExternal> trialDataFiles = new ArrayList<>();
        for (JsonNode node: tree) {
            trialDataFiles.add(createExternalDataFileFromNode(node));
        }

        return trialDataFiles;
    }

    private DataFileExternal createExternalDataFileFromNode(JsonNode node) {
        String fileName = node.get("filename").textValue();
        String program = node.get("program").textValue();
        String studyId = node.get("study_id").textValue();
        String category = node.get("category").textValue();
        List<String> variableNames = mapper.convertValue(node.get("variables"), List.class);

        return new DataFileExternal(fileName, program, studyId, category, variableNames);
    }
}

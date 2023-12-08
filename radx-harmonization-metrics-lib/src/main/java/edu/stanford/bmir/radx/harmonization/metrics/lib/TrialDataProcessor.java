package edu.stanford.bmir.radx.harmonization.metrics.lib;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*
This is a helper class to support testing. Information about data files,
including the file names, data element headers, etc., are read in from
a .json file and converted to DataFileExternal objects.

This should not be used by the Data Hub. The Data Hub should generate
DataFileExternal objects directly from the stored data files instead.
 */
@Component
public class TrialDataProcessor {

    private final ObjectMapper mapper = new ObjectMapper();

    public List<DataFileExternal> readExternalData(String fileName) throws IOException {
        return readJsonToList(fileName);
    }

    private List<DataFileExternal> readJsonToList(String fileName) throws IOException {
        File file = new File(fileName);
        InputStream inputStream = new FileInputStream(file);
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

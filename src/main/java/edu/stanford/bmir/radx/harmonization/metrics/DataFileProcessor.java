package edu.stanford.bmir.radx.harmonization.metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
This processor takes Java objects from the Data Hub that
contain information about data files and converts them
into DataFile and DataFilePair objects.
 */
public class DataFileProcessor {
    private Pattern VERSION_TAG_REGEX = Pattern.compile("_v(\\d+)\\.csv");
    private Pattern VARIABLE_CLEANER_REGEX = Pattern.compile("(_+\\d+)+$");
    private List<String> SEGMENTS_TO_DELETE_FROM_FILE_NAME = Arrays.asList(
            "_v(\\d+)", "_DATA", "_origcopy", "_transformcopy", ".csv");

    public Map<ReducedFileName, DataFilePair> processDataFiles(List<DataFileExternal> externalDataFiles)
            throws InvalidProgramException, InvalidDataFileCategoryException, NoVersionNumberException {
        Map<ReducedFileName, DataFilePair> dataFilePairMap = new HashMap<>();
        for (var externalDataFile: externalDataFiles) {
            StudyId studyId = StudyId.valueOf(externalDataFile.studyId());
            Program program = Program.fromString(externalDataFile.program());
            DataFileCategory category = DataFileCategory.fromString(externalDataFile.category());
            int version = extractVersion(externalDataFile.fileName());
            ReducedFileName name = extractReducedFileName(externalDataFile.fileName());
            Set<String> variableNames = preprocessVariableNames(externalDataFile.variableNames());
            DataFile dataFile = new DataFile(externalDataFile.fileName(), version, variableNames);
            if (!dataFilePairMap.containsKey(name)) {
                dataFilePairMap.put(name, new DataFilePair(name, program, studyId));
            }
            DataFilePair currentPair = dataFilePairMap.get(name);
            switch(category) {
                case TRANSFORM:
                    dataFilePairMap.put(name, currentPair.updateTransformData(dataFile));
                    break;
                case ORIG:
                    dataFilePairMap.put(name, currentPair.updateOrigData(dataFile));
                    break;
            }
        }
        return dataFilePairMap;
    }

    private Set<String> preprocessVariableNames(List<String> variableNames) {
        Set<String> processedVariableNames = new HashSet<>();
        for (var variable: variableNames) {
            processedVariableNames.add(cleanVariableName(variable));
        }
        return processedVariableNames;
    }

    private String cleanVariableName(String variableName) {
        Matcher matcher = VARIABLE_CLEANER_REGEX.matcher(variableName);
        return matcher.replaceAll("");
    }

    private ReducedFileName extractReducedFileName(String filename) {
        String reducedFileName = filename;
        for (String segment: SEGMENTS_TO_DELETE_FROM_FILE_NAME) {
            reducedFileName = reducedFileName.replaceAll(segment, "");
        }
        return ReducedFileName.valueOf(reducedFileName);
    }

    private int extractVersion(String filename) throws NoVersionNumberException {
        Matcher matcher = VERSION_TAG_REGEX.matcher(filename);
        if (matcher.find()) {
            String versionNumber = matcher.group(1);
            return Integer.parseInt(versionNumber);
        } else {
            throw new NoVersionNumberException(filename);
        }
    }
}

package edu.stanford.bmir.radx.harmonization.metrics;

import org.springframework.stereotype.Component;

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
@Component
public class DataFileProcessor {
    private Pattern VERSION_TAG_REGEX = Pattern.compile("_v(\\d+)\\.csv");
    private Pattern VARIABLE_CLEANER_REGEX = Pattern.compile("(_+\\d+)+$");
    private List<String> SEGMENTS_TO_DELETE_FROM_FILE_NAME = Arrays.asList(
            "_v(\\d+)", "_DATA", "_origcopy", "_transformcopy", ".csv");

    /*
    Convert information about data files in an external representation into the
    internal form that connects data files whose names differ only by version number
    and the origcopy/transformcopy label.
     */
    public Map<ReducedFileName, DataFilePair> processDataFiles(List<DataFileExternal> externalDataFiles)
            throws InvalidProgramException, InvalidDataFileCategoryException, NoVersionNumberException {
        Map<ReducedFileName, DataFilePair> dataFilePairMap = new HashMap<>();
        for (DataFileExternal externalDataFile: externalDataFiles) {
            // pull relevant information out of external representation of the data file
            StudyId studyId = StudyId.valueOf(externalDataFile.studyId());
            Program program = Program.fromString(externalDataFile.program());
            DataFileCategory category = DataFileCategory.fromString(externalDataFile.category());
            int version = extractVersion(externalDataFile.fileName());
            ReducedFileName name = extractReducedFileName(externalDataFile.fileName());
            Set<String> variableNames = preprocessVariableNames(externalDataFile.variableNames());

            // store the information in the internal data file pair representation
            if (!dataFilePairMap.containsKey(name)) {
                dataFilePairMap.put(name, new DataFilePair(name, program, studyId));
            }
            DataFilePair currentPair = dataFilePairMap.get(name);
            switch(category) {
                case TRANSFORM:
                    TransformFile transformFile = new TransformFile(externalDataFile.fileName(), version, variableNames);
                    dataFilePairMap.put(name, currentPair.updateTransformData(transformFile));
                    break;
                case ORIG:
                    OrigFile origFile = new OrigFile(externalDataFile.fileName(), version, variableNames);
                    dataFilePairMap.put(name, currentPair.updateOrigData(origFile));
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

    /*
    Reduce a variable name to its canonical form. This specifically addresses
    the case of variable names representing one-hot encodings and variable
    names that denote RedCAP versions.
    Examples:
    RedCAP versioning: "example_variable_name_2" -> "example_variable_name"
    one-hot encoding: "example_variable_name___3" -> "example_variable_name"
    both: "example_variable_name_2___3" -> "example_variable_name"
     */
    private String cleanVariableName(String variableName) {
        Matcher matcher = VARIABLE_CLEANER_REGEX.matcher(variableName);
        return matcher.replaceAll("");
    }

    /*
    Shorten the name of a data file to a canonical form that ignores the
    version tag and the origcopy/transformcopy identifier on the file.
    This allows associating multiple data files that have the same
    ReducedFileName.
    Example: "example_study_transformcopy_v1.csv" and "example_study_origcopy_v2.csv"
    share the same ReducedFileName of "example_study".
     */
    private ReducedFileName extractReducedFileName(String filename) {
        String reducedFileName = filename;
        for (String segment: SEGMENTS_TO_DELETE_FROM_FILE_NAME) {
            reducedFileName = reducedFileName.replaceAll(segment, "");
        }
        return ReducedFileName.valueOf(reducedFileName);
    }

    /*
    Extract the version number from the name of a data file.
    Example: "example_study_origcopy_v2.csv" yields version number 2.
     */
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

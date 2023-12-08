package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/*
This processor takes DataFileExternal objects and converts
them into DataFile and OrigTransformFilePair objects.
 */
@Component
public class DataFileProcessor {

    private final FileNameExtractor fileNameExtractor;
    private final VariableNameCleaner variableNameCleaner;

    public DataFileProcessor(FileNameExtractor fileNameExtractor,
                             VariableNameCleaner variableNameCleaner) {
        this.fileNameExtractor = fileNameExtractor;
        this.variableNameCleaner = variableNameCleaner;
    }

    /*
    Convert information about data files in an external representation into the
    internal form that connects data files whose names differ only by version number
    and the origcopy/transformcopy label.
     */
    public Map<ReducedFileName, OrigTransformFilePair> processDataFiles(List<DataFileExternal> externalDataFiles)
            throws InvalidProgramIdentifierException, InvalidOrigTransformIdentifierException, NoVersionNumberException {
        Map<ReducedFileName, OrigTransformFilePair> dataFilePairMap = new HashMap<>();
        for (DataFileExternal externalDataFile: externalDataFiles) {
            // pull relevant information out of external representation of the data file
            StudyId studyId = StudyId.valueOf(externalDataFile.studyId());
            ProgramIdentifier programIdentifier = ProgramIdentifier.fromString(externalDataFile.program());
            OrigTransformIdentifier otIdentifier = OrigTransformIdentifier.fromString(externalDataFile.category());
            int version = fileNameExtractor.extractVersion(externalDataFile.fileName());
            ReducedFileName name = fileNameExtractor.extractReducedFileName(externalDataFile.fileName());
            Set<String> variableNames = preprocessVariableNames(externalDataFile.variableNames());

            // store the information in the internal data file pair representation
            if (!dataFilePairMap.containsKey(name)) {
                dataFilePairMap.put(name, new OrigTransformFilePair(name, programIdentifier, studyId));
            }
            OrigTransformFilePair currentPair = dataFilePairMap.get(name);
            switch(otIdentifier) {
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
            processedVariableNames.add(variableNameCleaner.cleanVariableName(variable));
        }
        return processedVariableNames;
    }
}

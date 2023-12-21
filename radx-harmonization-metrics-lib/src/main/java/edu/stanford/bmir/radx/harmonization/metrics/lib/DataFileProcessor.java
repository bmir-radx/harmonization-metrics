package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
            throws InvalidProgramIdException, InvalidOrigTransformCategoryException, NoVersionNumberException {
        Map<ReducedFileName, OrigTransformFilePair> dataFilePairMap = new HashMap<>();
        for (DataFileExternal externalDataFile: externalDataFiles) {
            // pull relevant information out of external representation of the data file
            StudyId studyId = StudyId.valueOf(externalDataFile.studyId());
            ProgramId programId = ProgramId.fromString(externalDataFile.program());
            OrigTransformCategory otIdentifier = OrigTransformCategory.fromString(externalDataFile.category());
            int version = fileNameExtractor.extractVersion(externalDataFile.fileName());
            ReducedFileName name = fileNameExtractor.extractReducedFileName(externalDataFile.fileName());
            Set<String> variableNames = preprocessVariableNames(externalDataFile.variableNames());

            // store the information in the internal data file pair representation
            if (!dataFilePairMap.containsKey(name)) {
                dataFilePairMap.put(name, new OrigTransformFilePair(name, programId, studyId));
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

    public Map<StudyId, Study> organizeFilePairsByStudy(
            Map<ReducedFileName, OrigTransformFilePair> pairMap) {
        Map<StudyId, List<OrigTransformFilePair>> studyIdToPairs = new HashMap<>();
        Map<StudyId, ProgramId> studyIdToProgramId = new HashMap<>();
        for (var pair: pairMap.values()) {
            StudyId studyId = pair.studyId();
            if (!studyIdToPairs.containsKey(studyId)) {
                studyIdToPairs.put(studyId, new ArrayList<>());
            }
            if (!studyIdToProgramId.containsKey(studyId)) {
                studyIdToProgramId.put(studyId, pair.programId());
            }
            studyIdToPairs.get(studyId).add(pair);
        }

        Map<StudyId, Study> studyMap = new HashMap<>();
        for (var studyId: studyIdToPairs.keySet()) {
            Study study = new Study(
                    studyId, studyIdToProgramId.get(studyId), studyIdToPairs.get(studyId));
            studyMap.put(studyId, study);
        }
        return studyMap;
    }

    private Set<String> preprocessVariableNames(List<String> variableNames) {
        Set<String> processedVariableNames = new HashSet<>();
        for (var variable: variableNames) {
            processedVariableNames.add(variableNameCleaner.cleanVariableName(variable));
        }
        return processedVariableNames;
    }
}

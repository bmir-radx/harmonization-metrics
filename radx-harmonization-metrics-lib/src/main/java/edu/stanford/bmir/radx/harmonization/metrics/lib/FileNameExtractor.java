package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
Helper class for extracting substrings from a file pairName.
 */
@Component
public class FileNameExtractor {
    private final Pattern VERSION_TAG_REGEX = Pattern.compile("_v(\\d+)\\.csv");
    private final List<String> SEGMENTS_TO_DELETE_FROM_FILE_NAME = Arrays.asList(
            "_v(\\d+)", "_DATA", "_origcopy", "_transformcopy", ".csv");

    /*
    Shorten the pairName of a data file to a canonical form that ignores the
    version tag and the origcopy/transformcopy identifier on the file.
    This allows associating multiple data files that have the same
    ReducedFileName.
    Example: "example_study_transformcopy_v1.csv" and "example_study_origcopy_v2.csv"
    share the same ReducedFileName of "example_study".
     */
    public ReducedFileName extractReducedFileName(String filename) {
        String reducedFileName = filename;
        for (String segment: SEGMENTS_TO_DELETE_FROM_FILE_NAME) {
            reducedFileName = reducedFileName.replaceAll(segment, "");
        }
        return ReducedFileName.valueOf(reducedFileName);
    }

    /*
    Extract the version number from the pairName of a data file.
    Example: "example_study_origcopy_v2.csv" yields version number 2.
     */
    public int extractVersion(String filename) throws NoVersionNumberException {
        Matcher matcher = VERSION_TAG_REGEX.matcher(filename);
        if (matcher.find()) {
            String versionNumber = matcher.group(1);
            return Integer.parseInt(versionNumber);
        } else {
            throw new NoVersionNumberException(filename);
        }
    }
}

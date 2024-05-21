package edu.stanford.bmir.radx.harmonization.metrics.lib;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.lang.Math.max;

@Component
public class CsvWriter {

    public void writeStudyReport(
            List<StudyMetrics> studyMetrics,
            String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] headers = {
                    "Study ID",
                    "DCC",
                    "Number of Data Files",
                    "Total Variables",
                    "Harmonizable Variables (Tier-1)",
                    "Harmonizable Variables (Tier-2)",
                    "Harmonizable Variables (Tier-3)",
                    "Total Harmonizable Variables",
                    "Harmonized Variables (Tier-1)",
                    "Harmonized Variables (Tier-2)",
                    "Harmonized Variables (Tier-3)",
                    "Total Harmonized Variables",
                    "Variable List",
                    "Harmonizable Variables List (Tier-1)",
                    "Harmonizable Variables List (Tier-2)",
                    "Harmonizable Variables List (Tier-3)",
                    "Harmonizable Variables List",
                    "Harmonized Variables List (Tier-1)",
                    "Harmonized Variables List (Tier-2)",
                    "Harmonized Variables List (Tier-3)",
                    "Harmonized Variables List"
            };
            writer.writeNext(headers);

            for (var metrics : studyMetrics) {
                String[] line = getStudyRow(metrics);
                writer.writeNext(line);
            }
        } catch (IOException e) {
            System.err.println(String.format("Failed while writing to %s", filePath));
            throw e;
        }
    }

    public void writeFilePairReport(
            List<OrigTransformFilePairMetrics> pairMetrics,
            String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] headers = {
                    "File Name (Pre-harmonization)",
                    "File Name (Post-harmonization)",
                    "Study ID",
                    "DCC",
                    "Total Variables",
                    "Harmonizable Variables (Tier-1)",
                    "Harmonizable Variables (Tier-2)",
                    "Harmonizable Variables (Tier-3)",
                    "Total Harmonizable Variables",
                    "Harmonized Variables (Tier-1)",
                    "Harmonized Variables (Tier-2)",
                    "Harmonized Variables (Tier-3)",
                    "Total Harmonized Variables",
                    "Variable List (Pre-harmonization)",
                    "Variable List (Post-harmonization)",
                    "Harmonizable Variables List (Tier-1)",
                    "Harmonizable Variables List (Tier-2)",
                    "Harmonizable Variables List (Tier-3)",
                    "Harmonizable Variables List",
                    "Harmonized Variables List (Tier-1)",
                    "Harmonized Variables List (Tier-2)",
                    "Harmonized Variables List (Tier-3)",
                    "Harmonized Variables List"
            };
            writer.writeNext(headers);

            for (var metrics : pairMetrics) {
                String[] line = getPairRow(metrics);
                writer.writeNext(line);
            }
        } catch (IOException e) {
            System.err.println(String.format("Failed while writing to %s", filePath));
            throw e;
        }
    }

    private String[] getPairRow(OrigTransformFilePairMetrics metrics) {
        Set<String> dataElementsAll = new HashSet<>();
        String[] line = {
                metrics.origFileName().orElse(null),
                metrics.transformFileName().orElse(null),
                metrics.studyId().value(),
                metrics.programId().toString(),
                String.valueOf(max(metrics.nDataElementsOrig(), metrics.nDataElementsTransform())),
                String.valueOf(metrics.nHarmonizableDataElementsTier1()),
                String.valueOf(metrics.nHarmonizableDataElementsTier2()),
                String.valueOf(metrics.nHarmonizableDataElementsTier3()),
                String.valueOf(metrics.totalHarmonizable()),
                String.valueOf(metrics.nHarmonizedDataElementsTier1()),
                String.valueOf(metrics.nHarmonizedDataElementsTier2()),
                String.valueOf(metrics.nHarmonizedDataElementsTier3()),
                String.valueOf(metrics.totalHarmonized()),
                String.join(";", metrics.dataElementsOrig()),
                String.join(";", metrics.dataElementsTransform()),
                String.join(";", metrics.harmonizableDataElementsTier1()),
                String.join(";", metrics.harmonizableDataElementsTier2()),
                String.join(";", metrics.harmonizableDataElementsTier3()),
                String.join(";", metrics.harmonizableDataElements()),
                String.join(";", metrics.harmonizedDataElementsTier1()),
                String.join(";", metrics.harmonizedDataElementsTier2()),
                String.join(";", metrics.harmonizedDataElementsTier3()),
                String.join(";", metrics.harmonizedDataElements()),
        };
        return line;
    }

    private String[] getStudyRow(StudyMetrics metrics) {
        String[] line = {
                metrics.studyId().value(),
                metrics.programId().toString(),
                String.valueOf(metrics.nOrigTransformFilePairs()),
                String.valueOf(metrics.nUniqueDataElements()),
                String.valueOf(metrics.nUniqueHarmonizableDataElementsTier1()),
                String.valueOf(metrics.nUniqueHarmonizableDataElementsTier2()),
                String.valueOf(metrics.nUniqueHarmonizableDataElementsTier3()),
                String.valueOf(metrics.totalHarmonizable()),
                String.valueOf(metrics.nUniqueHarmonizedDataElementsTier1()),
                String.valueOf(metrics.nUniqueHarmonizedDataElementsTier2()),
                String.valueOf(metrics.nUniqueHarmonizedDataElementsTier3()),
                String.valueOf(metrics.totalHarmonized()),
                String.join(";", metrics.uniqueDataElements()),
                String.join(";", metrics.harmonizableDataElementsTier1()),
                String.join(";", metrics.harmonizableDataElementsTier2()),
                String.join(";", metrics.harmonizableDataElementsTier3()),
                String.join(";", metrics.harmonizableDataElements()),
                String.join(";", metrics.harmonizedDataElementsTier1()),
                String.join(";", metrics.harmonizedDataElementsTier2()),
                String.join(";", metrics.harmonizedDataElementsTier3()),
                String.join(";", metrics.harmonizedDataElements()),
        };
        return line;
    }
}

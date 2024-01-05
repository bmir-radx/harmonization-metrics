package edu.stanford.bmir.radx.harmonization.metrics.lib;

import com.opencsv.CSVWriter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
public class CsvWriter {

    public void writeStudyReport(List<StudyMetrics> studyMetrics, String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] headers = {
                    "Study ID",
                    "DCC",
                    "Number Orig-Transform Pairs",
                    "Unique Variables",
                    "Unique Harmonizable Variables (Tier-1)",
                    "Unique Harmonizable Variables (Tier-2)",
                    "Unique Harmonizable Variables (Tier-3)",
                    "% Harmonizable",
                    "Unique Harmonized Variables (Tier-1)",
                    "Unique Harmonized Variables (Tier-2)",
                    "Unique Harmonized Variables (Tier-3)",
                    "% Harmonized"
            };
            writer.writeNext(headers);

            for (var metrics : studyMetrics) {
                String[] line = getStudyRow(metrics);
                writer.writeNext(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeFilePairReport(List<OrigTransformFilePairMetrics> pairMetrics, String filePath) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(filePath))) {
            String[] headers = {
                    "File Name (Orig)",
                    "File Name (Transform)",
                    "Study ID",
                    "DCC",
                    "Unique Variables (Orig)",
                    "Unique Variables (Transform)",
                    "Unique Harmonizable Variables (Tier-1)",
                    "Unique Harmonizable Variables (Tier-2)",
                    "Unique Harmonizable Variables (Tier-3)",
                    "% Harmonizable",
                    "Unique Harmonized Variables (Tier-1)",
                    "Unique Harmonized Variables (Tier-2)",
                    "Unique Harmonized Variables (Tier-3)",
                    "% Harmonized"
            };
            writer.writeNext(headers);

            for (var metrics : pairMetrics) {
                String[] line = getPairRow(metrics);
                writer.writeNext(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String[] getPairRow(OrigTransformFilePairMetrics metrics) {
        Double percentHarmonizable = ((double) (metrics.nHarmonizableDataElementsTier1()
                + metrics.nHarmonizableDataElementsTier2()
                + metrics.nHarmonizableDataElementsTier3())
                / Math.max(metrics.nDataElementsOrig(), metrics.nDataElementsTransform()));
        Double percentHarmonized = ((double) (metrics.nHarmonizedDataElementsTier1()
                + metrics.nHarmonizedDataElementsTier2()
                + metrics.nHarmonizedDataElementsTier3())
                / Math.max(metrics.nDataElementsOrig(), metrics.nDataElementsTransform()));
        String[] line = {
                metrics.origFileName().orElse(null),
                metrics.transformFileName().orElse(null),
                metrics.studyId().value(),
                metrics.programId().toString(),
                String.valueOf(metrics.nDataElementsOrig()),
                String.valueOf(metrics.nDataElementsTransform()),
                String.valueOf(metrics.nHarmonizableDataElementsTier1()),
                String.valueOf(metrics.nHarmonizableDataElementsTier2()),
                String.valueOf(metrics.nHarmonizableDataElementsTier3()),
                String.format("%.2f", percentHarmonizable),
                String.valueOf(metrics.nHarmonizedDataElementsTier1()),
                String.valueOf(metrics.nHarmonizedDataElementsTier2()),
                String.valueOf(metrics.nHarmonizedDataElementsTier3()),
                String.format("%.2f", percentHarmonized),
        };
        return line;
    }

    private String[] getStudyRow(StudyMetrics metrics) {
        Double percentHarmonizable = 100 * ((double) (
                metrics.nUniqueHarmonizableDataElementsTier1()
                + metrics.nUniqueHarmonizableDataElementsTier2()
                + metrics.nUniqueHarmonizableDataElementsTier3())
                / metrics.nUniqueDataElements());
        Double percentHarmonized = 100 * ((double) (
                metrics.nUniqueHarmonizedDataElementsTier1()
                + metrics.nUniqueHarmonizedDataElementsTier2()
                + metrics.nUniqueHarmonizedDataElementsTier3())
                / metrics.nUniqueDataElements());
        String[] line = {
                metrics.studyId().value(),
                metrics.programId().toString(),
                String.valueOf(metrics.nOrigTransformFilePairs()),
                String.valueOf(metrics.nUniqueDataElements()),
                String.valueOf(metrics.nUniqueHarmonizableDataElementsTier1()),
                String.valueOf(metrics.nUniqueHarmonizableDataElementsTier2()),
                String.valueOf(metrics.nUniqueHarmonizableDataElementsTier3()),
                String.format("%.2f", percentHarmonizable),
                String.valueOf(metrics.nUniqueHarmonizedDataElementsTier1()),
                String.valueOf(metrics.nUniqueHarmonizedDataElementsTier2()),
                String.valueOf(metrics.nUniqueHarmonizedDataElementsTier3()),
                String.format("%.2f", percentHarmonized),
        };
        return line;
    }
}

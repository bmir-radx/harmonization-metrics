# RADx Harmonization Metrics

A library and command line interface for computing metrics on harmonization for the RADx Data Hub.

## Usage - Library

To use the Harmonization Metrics programmatically, add the following Maven dependency for the library component
of the Harmonization Metrics tool:

```
<dependency>
    <groupId>edu.stanford.bmir.radx</groupId>
    <artifactId>radx-harmonization-metrics-lib</artifactId>
    <version>1.2.0</version>
</dependency>
```

The harmonization metrics library can be used with Spring Boot by scanning components
in the library:
```java
@ComponentScan(basePackages = "edu.stanford.bmir.radx.harmonization.metrics")
```

### Example Implementation

The `MetricsCalculator` component provides a single point of entry for computing
harmonization metrics.
Below is an example of the library usage with some pseudocode for converting
data file records from another data store (e.g., a database) into the `DataFileInput`
format expected by the library.
The implementation of the command line interface also follows this example.
```java
@Component
public class Wrapper {
    
    private final MetricsCalculator calculator;

    public Wrapper(MetricsCalculator calculator) {
        this.calculator = calculator;
    }
    
    public loadDataAndCalculateMetrics() {
        List<Object> rawData = readDataFromDataStore();
        List<DataFileInput> inputData = convertRawData(rawData);
        MetricsReport metrics = calculator.computeHarmonizationMetrics(inputData);
        // use the metrics for other things, e.g., 
        // store to database, write to file, etc.
    }

    private List<DataFileInput> convertRawData(List<Object> rawData) {
        // For each data file record (raw data), obtain the file name,
        // study ID, program ID, whether the file is "orig" or "transform",
        // and the set of variable names from each raw data object, and
        // then use this information to create a DataFileInput object.
    }
```

### Input Specification

The input requires first extracting relevant information from data file records.
The `DataFileInput` class defines the information expected by the metrics
calculator: the file name, the program ID, the study ID, the category, and a
list of the variable names contained in the data file.

The program ID must be one of the following:
- `RADx-UP`
- `RADx-rad`
- `RADx-Tech`
- `Digital Health Technologies`

and the category is also constrained to be one of the following two:
- `orig`
- `transform`

The `DataFileInput` class has the following specification:

```java
public record DataFileInput(
        String fileName,
        String program,
        String studyId,
        String category,
        List<String> variableNames) {
}
```

### Output Specification
A `MetricsReport` object is returned by the `MetricsCalculator`. 
It contains a timestamp and the metrics aggregated by orig-transform
file pairs and by study ID.

```java
public record MetricsReport(
        LocalDate date,
        List<OrigTransformFilePairMetrics> pairMetrics,
        List<StudyMetrics> studyMetrics) {
}
```

The `OrigTransformFilePairMetrics` and `StudyMetrics` objects are records
that can be translated directly into records for a persistent data store.
It is defined as follows:

## Usage - Command Line

The Harmonization Metrics can be produced by running it as a command line tool.
The library and command line tool are on Maven Central:
```
<dependency>
    <groupId>edu.stanford.bmir.radx</groupId>
    <artifactId>radx-harmonization-metrics-app</artifactId>
    <version>1.2.0</version>
</dependency>
<dependency>
    <groupId>edu.stanford.bmir.radx</groupId>
    <artifactId>radx-harmonization-metrics-lib</artifactId>
    <version>1.2.0</version>
</dependency>
```

Then run the jar for the application:
```
java -jar radx-harmonization-metrics-app-1.2.0.jar -f inputs.json -os study_metrics.csv -op dataset_metrics.csv
```

`inputs.json` is expected to have the following input structure.
This contains the same data as expected when using the library programmatically.
```json
[
  {
    "filename": "file1.csv",
    "program": "RADx-rad",
    "study_id": "phs00001",
    "category": "transform",
    "variables": [
      "v1",
      "v2",
      "v3"
    ]
  },
  {
    "filename": "file2.csv",
    "program": "RADx-rad",
    "study_id": "phs00002",
    "category": "orig",
    "variables": [
      "v1",
      "v2",
      "v3"
    ]
  }
]
```

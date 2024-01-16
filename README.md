# RADx Harmonization Metrics

A library and command line interface for computing metrics on harmonization for the RADx Data Hub.

## Usage

To use the Harmonization Metrics library, add the following Maven dependency:

```
<dependency>
    <groupId>edu.stanford.bmir.radx</groupId>
    <artifactId>radx-harmonization-metrics-lib</artifactId>
    <version>1.0.0</version>
</dependency>
```

The harmonization metrics library can be used with Spring Boot by scanning components
in the library:
```java
@ComponentScan(basePackages = "edu.stanford.bmir.radx.harmonization.metrics")
```

The `MetricsCalculator` component provides a single point of entry for computing
harmonization metrics.
The input requires first extracting relevant information from data file records.
The `DataFileInput` class defines the information expected by the metrics
calculator: the file name, the program ID, the study ID, the category, and a
list of the variable names contained in the data file.

Note that the program ID must be one of the following:
- `RADx-UP`
- `RADx-rad`
- `RADx-Tech`
- `Digital Health Technologies`

and the category is also constrained to be one of the following two:
- `orig`
- `transform`

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

# RADx Harmonization Metrics

A library and command line interface for computing metrics on harmonization for the RADx Data Hub.

## Usage

The harmonization metrics library can be used with Spring Boot by injecting the 
`MetricsCalculator` as a dependency and running its `computeHarmonizationMetrics()`
method.
The input requires first extracting relevant information from data file records.
The `DataFileExternal` class defines the information expected by the metrics
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

Below is an example of the library usage after the user has inputs that fit
the expected format.
```java
@Component
public class Wrapper {
    
    private final MetricsCalculator calculator;

    public Wrapper(MetricsCalculator calculator) {
        this.calculator = calculator;
    }
    
    public get_metrics(List<DataFileExternal> data) {
        MetricsReport metrics = calculator.computeHarmonizationMetrics(data);
        // use the metrics for other things, e.g., 
        // store to database, write to file, etc.
    }
}
```

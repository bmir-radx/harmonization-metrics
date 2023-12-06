package edu.stanford.bmir.radx.harmonization.metrics;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class VariableNameCleaner {
    private final Pattern VARIABLE_CLEANER_REGEX = Pattern.compile("(_+\\d+)+$");

    /*
    Reduce a variable pairName to its canonical form. This specifically addresses
    the case of variable names representing one-hot encodings and variable
    names that denote RedCAP versions.
    Examples:
    RedCAP versioning: "example_variable_name_2" -> "example_variable_name"
    one-hot encoding: "example_variable_name___3" -> "example_variable_name"
    both: "example_variable_name_2___3" -> "example_variable_name"
     */
    public String cleanVariableName(String variableName) {
        Matcher matcher = VARIABLE_CLEANER_REGEX.matcher(variableName);
        return matcher.replaceAll("");
    }
}

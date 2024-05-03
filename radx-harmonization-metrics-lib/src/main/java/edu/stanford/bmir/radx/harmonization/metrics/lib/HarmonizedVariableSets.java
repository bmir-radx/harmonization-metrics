package edu.stanford.bmir.radx.harmonization.metrics.lib;

import java.util.Set;

public record HarmonizedVariableSets(
    Set<String> harmonizableDataElementsTier1,
    Set<String> harmonizableDataElementsTier2,
    Set<String> harmonizableDataElementsTier3,
    Set<String> harmonizedDataElementsTier1,
    Set<String> harmonizedDataElementsTier2,
    Set<String> harmonizedDataElementsTier3,
    Set<String> dataElements) {
}

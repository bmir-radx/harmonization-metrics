package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AggregateMetricsTest {

    @Test
    public void aggregateMetricsFromFilePairMetrics() {
        List<OrigTransformFilePairMetrics> pairMetricsList = new ArrayList<>();

        // counts toward harmonizable
        var origOnlyMetrics = new OrigTransformFilePairMetrics(
                ReducedFileName.valueOf("1"), ProgramIdentifier.RADXUP, StudyId.valueOf("1"),
                Optional.of(1), Optional.of(5), Optional.of(5), Optional.of(0),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                5, 0, 0);
        pairMetricsList.add(origOnlyMetrics);

        // counts toward harmonized
        var transformOnlyMetrics = new OrigTransformFilePairMetrics(
                ReducedFileName.valueOf("2"), ProgramIdentifier.RADXRAD, StudyId.valueOf("2"),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(1), Optional.of(7), Optional.of(0), Optional.of(6),
                0, 6, 1);
        pairMetricsList.add(transformOnlyMetrics);

        // counts toward partially harmonized and harmonizable
        var pairMetrics = new OrigTransformFilePairMetrics(
                ReducedFileName.valueOf("3"), ProgramIdentifier.RADXTECH, StudyId.valueOf("3"),
                Optional.of(1), Optional.of(11), Optional.of(10), Optional.of(0),
                Optional.of(1), Optional.of(11), Optional.of(2), Optional.of(8),
                2, 8, 1);
        pairMetricsList.add(pairMetrics);

        // counts toward harmonized and trivially harmonized
        var triviallyHarmonizedMetrics = new OrigTransformFilePairMetrics(
                ReducedFileName.valueOf("4"), ProgramIdentifier.DHT, StudyId.valueOf("4"),
                Optional.of(1), Optional.of(3), Optional.of(0), Optional.of(0),
                Optional.of(1), Optional.of(3), Optional.of(0), Optional.of(0),
                0, 0, 3);
        pairMetricsList.add(triviallyHarmonizedMetrics);

        AggregateMetrics aggregateMetrics = AggregateMetrics.aggregateMetricsFromFilePairMetrics(pairMetricsList);
        assertEquals(4, aggregateMetrics.nOrigTransfromFilePairs());
        assertEquals(2, aggregateMetrics.nHarmonizableOrigTransformFilePairs());
        assertEquals(1, aggregateMetrics.nPartiallyHarmonizedOrigTransformFilePairs());
        assertEquals(2, aggregateMetrics.nHarmonizedOrigTransformFilePairs());
        assertEquals(1, aggregateMetrics.nTriviallyHarmonizedOrigTransformFilePairs());
        assertEquals(7, aggregateMetrics.nMissedHarmonizableDataElements());
        assertEquals(14, aggregateMetrics.nHarmonizedDataElements());
        assertEquals(5, aggregateMetrics.nNonHarmonizableDataElements());
    }
}
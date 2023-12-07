package edu.stanford.bmir.radx.harmonization.metrics;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class MetricsCalculatorTest {

    @Test
    public void computeHarmonizationMetrics()
            throws InvalidOrigTransformIdentifierException, NoVersionNumberException, InvalidProgramIdentifierException {
        DataFileProcessor mockFileProcessor = Mockito.mock(DataFileProcessor.class);
        OrigTransformFilePairMetricsGenerator mockMetricsGenerator = Mockito.mock(OrigTransformFilePairMetricsGenerator.class);
        MetricsCalculator calculator = new MetricsCalculator(mockFileProcessor, mockMetricsGenerator);

        // mock the output of the DataFileProcessor
        Map<ReducedFileName, OrigTransformFilePair> mockPairMap = new HashMap<>();
        ProgramIdentifier program = ProgramIdentifier.RADXUP;
        StudyId studyId = StudyId.valueOf("lol");
        ReducedFileName name1 = ReducedFileName.valueOf("name1");
        OrigTransformFilePair pair1 = new OrigTransformFilePair(name1, program, studyId);
        ReducedFileName name2 = ReducedFileName.valueOf("name2");
        OrigTransformFilePair pair2 = new OrigTransformFilePair(name2, program, studyId);
        ReducedFileName name3 = ReducedFileName.valueOf("name3");
        OrigTransformFilePair pair3 = new OrigTransformFilePair(name3, program, studyId);
        ReducedFileName name4 = ReducedFileName.valueOf("name4");
        OrigTransformFilePair pair4 = new OrigTransformFilePair(name4, program, studyId);
        mockPairMap.put(name1, pair1);
        mockPairMap.put(name2, pair2);
        mockPairMap.put(name3, pair3);
        mockPairMap.put(name4, pair4);
        Mockito.doReturn(mockPairMap).when(mockFileProcessor).processDataFiles(Mockito.anyList());

        // mock the result of the metrics generator four times
        var metrics1 = new OrigTransformFilePairMetrics(
                ReducedFileName.valueOf("1"), ProgramIdentifier.RADXUP, StudyId.valueOf("1"),
                Optional.of(1), Optional.of(5), Optional.of(5), Optional.of(0),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                5, 0, 0);
        var metrics2 = new OrigTransformFilePairMetrics(
                ReducedFileName.valueOf("2"), ProgramIdentifier.RADXRAD, StudyId.valueOf("2"),
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(1), Optional.of(7), Optional.of(0), Optional.of(6),
                0, 6, 1);
        var metrics3 = new OrigTransformFilePairMetrics(
                ReducedFileName.valueOf("3"), ProgramIdentifier.RADXTECH, StudyId.valueOf("3"),
                Optional.of(1), Optional.of(11), Optional.of(10), Optional.of(0),
                Optional.of(1), Optional.of(11), Optional.of(2), Optional.of(8),
                2, 8, 1);
        var metrics4 = new OrigTransformFilePairMetrics(
                ReducedFileName.valueOf("4"), ProgramIdentifier.DHT, StudyId.valueOf("4"),
                Optional.of(1), Optional.of(3), Optional.of(0), Optional.of(0),
                Optional.of(1), Optional.of(3), Optional.of(0), Optional.of(0),
                0, 0, 3);
        Mockito.doReturn(metrics1).doReturn(metrics2).doReturn(metrics3).doReturn(metrics4)
                .when(mockMetricsGenerator).createMetricsFromFilePair(Mockito.any(OrigTransformFilePair.class));

        // run the metrics calculator with a fake input
        List<DataFileExternal> fakeInput = new ArrayList<>();
        AggregateMetrics aggregateMetrics = calculator.computeHarmonizationMetrics(fakeInput);

        // check that the mockFileProcessor was called once
        Mockito.verify(mockFileProcessor, Mockito.times(1))
                .processDataFiles(Mockito.anyList());

        // check that the mockMetricsGenerator was called four times
        Mockito.verify(mockMetricsGenerator, Mockito.times(4))
                .createMetricsFromFilePair(Mockito.any(OrigTransformFilePair.class));

        // check that metrics have the right values
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
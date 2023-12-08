package edu.stanford.bmir.radx.harmonization.metrics.lib;

import org.junit.Test;

public class ReducedFileNameTest {

    @Test(expected = NullPointerException.class)
    public void testReducedFileName_nonNull() {
        String fileName = null;
        ReducedFileName reducedFileName = new ReducedFileName(fileName);
    }

}
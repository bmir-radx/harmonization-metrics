package edu.stanford.bmir.radx.harmonization.metrics;

public record AggregateMetricsInternal(
    int numFilesOrig,
    int numFilesTransform,
    int numFilesMatched,
    int numHarmonizableOrig,
    int numPartiallyHarmonizedOrig,
    int numHarmonizedOrig,
    int numHarmonizableTransform,
    int numPartiallyHarmonizedTransform,
    int numHarmonizedTransform,
    int numHarmonizableMatched,
    int numPartiallyHarmonizedMatched,
    int numHarmonizedMatched) {

    public int getTotalDataSets() {
        return numFilesOrig + numFilesTransform - numFilesMatched;
    }

    public int getTotalHarmonizableDataSets() {
        return numHarmonizableOrig + numHarmonizableTransform + numHarmonizableMatched;
    }

    public int getTotalPartiallyHarmonizedDataSets() {
        return numPartiallyHarmonizedOrig + numPartiallyHarmonizedTransform + numPartiallyHarmonizedMatched;
    }

    public int getTotalHarmonizedDataSets() {
        return numHarmonizedOrig + numHarmonizedTransform + numHarmonizedMatched;
    }

    public AggregateMetricsInternal incrementCountsWithDataSetMetrics(DataSetMetrics dataSetMetrics) {
        AggregateMetricsInternalBuilder builder = toBuilder();
        if (dataSetMetrics.hasOrig() && dataSetMetrics.hasTransform()) {
            builder.withNumFilesMatched(numFilesMatched+1);
            builder.withNumFilesTransform(numFilesTransform+1);
            builder.withNumFilesOrig(numFilesOrig+1);
            if (dataSetMetrics.isHarmonizableTransform()) {
                builder.withNumHarmonizableMatched(numHarmonizableMatched+1);
            } else if (dataSetMetrics.isPartiallyHarmonizedTransform()) {
                builder.withNumHarmonizableMatched(numPartiallyHarmonizedMatched+1);
            } else if (dataSetMetrics.isHarmonizedTransform()) {
                builder.withNumHarmonizableMatched(numHarmonizedMatched+1);
            }
        } else if (dataSetMetrics.hasTransform()) {
            builder.withNumFilesTransform(numFilesTransform+1);
            if (dataSetMetrics.isHarmonizableTransform()) {
                builder.withNumHarmonizableTransform(numHarmonizableTransform+1);
            } else if (dataSetMetrics.isPartiallyHarmonizedTransform()) {
                builder.withNumHarmonizableTransform(numPartiallyHarmonizedTransform+1);
            } else if (dataSetMetrics.isHarmonizedTransform()) {
                builder.withNumHarmonizableTransform(numHarmonizedTransform+1);
            }
        } else if (dataSetMetrics.hasOrig()) {
            builder.withNumFilesOrig(numFilesOrig+1);
            if (dataSetMetrics.isHarmonizableOrig()) {
                builder.withNumHarmonizableOrig(numHarmonizableOrig+1);
            } else if (dataSetMetrics.isPartiallyHarmonizedOrig()) {
                builder.withNumHarmonizableOrig(numPartiallyHarmonizedOrig+1);
            } else if (dataSetMetrics.isHarmonizedOrig()) {
                builder.withNumHarmonizableOrig(numHarmonizedOrig+1);
            }
        }
        return builder.build();
    }

    public AggregateMetricsInternalBuilder toBuilder() {
        return new AggregateMetricsInternalBuilder()
                .withNumFilesOrig(numFilesOrig)
                .withNumFilesTransform(numFilesTransform)
                .withNumFilesMatched(numFilesMatched)
                .withNumHarmonizableOrig(numHarmonizableOrig)
                .withNumPartiallyHarmonizedOrig(numPartiallyHarmonizedOrig)
                .withNumHarmonizedOrig(numHarmonizedOrig)
                .withNumHarmonizableTransform(numHarmonizableTransform)
                .withNumPartiallyHarmonizedTransform(numPartiallyHarmonizedTransform)
                .withNumHarmonizedTransform(numHarmonizedTransform)
                .withNumHarmonizableMatched(numHarmonizableMatched)
                .withNumPartiallyHarmonizedMatched(numPartiallyHarmonizedMatched)
                .withNumHarmonizedMatched(numHarmonizedMatched);
    }

    public static class AggregateMetricsInternalBuilder {
        private int numFilesOrig = 0;
        private int numFilesTransform = 0;
        private int numFilesMatched = 0;
        private int numHarmonizableOrig = 0;
        private int numPartiallyHarmonizedOrig = 0;
        private int numHarmonizedOrig = 0;
        private int numHarmonizableTransform = 0;
        private int numPartiallyHarmonizedTransform = 0;
        private int numHarmonizedTransform = 0;
        private int numHarmonizableMatched = 0;
        private int numPartiallyHarmonizedMatched = 0;
        private int numHarmonizedMatched = 0;

        public AggregateMetricsInternalBuilder withNumFilesOrig(int numFilesOrig) {
            this.numFilesOrig = numFilesOrig;
            return this;
        }

        public AggregateMetricsInternalBuilder withNumFilesTransform(int numFilesTransform) {
            this.numFilesTransform = numFilesTransform;
            return this;
        }

        public AggregateMetricsInternalBuilder withNumFilesMatched(int numFilesMatched) {
            this.numFilesMatched = numFilesMatched;
            return this;
        }

        public AggregateMetricsInternalBuilder withNumHarmonizableOrig(int numHarmonizableOrig) {
            this.numHarmonizableOrig = numHarmonizableOrig;
            return this;
        }

        public AggregateMetricsInternalBuilder withNumPartiallyHarmonizedOrig(int numPartiallyHarmonizedOrig) {
            this.numPartiallyHarmonizedOrig = numPartiallyHarmonizedOrig;
            return this;
        }

        public AggregateMetricsInternalBuilder withNumHarmonizedOrig(int numHarmonizedOrig) {
            this.numHarmonizedOrig = numHarmonizedOrig;
            return this;
        }

        public AggregateMetricsInternalBuilder withNumHarmonizableTransform(int numHarmonizableTransform) {
            this.numHarmonizableTransform = numHarmonizableTransform;
            return this;
        }

        public AggregateMetricsInternalBuilder withNumPartiallyHarmonizedTransform(int numPartiallyHarmonizedTransform) {
            this.numPartiallyHarmonizedTransform = numPartiallyHarmonizedTransform;
            return this;
        }

        public AggregateMetricsInternalBuilder withNumHarmonizedTransform(int numHarmonizedTransform) {
            this.numHarmonizedTransform = numHarmonizedTransform;
            return this;
        }

        public AggregateMetricsInternalBuilder withNumHarmonizableMatched(int numHarmonizableMatched) {
            this.numHarmonizableMatched = numHarmonizableMatched;
            return this;
        }

        public AggregateMetricsInternalBuilder withNumPartiallyHarmonizedMatched(int numPartiallyHarmonizedMatched) {
            this.numPartiallyHarmonizedMatched = numPartiallyHarmonizedMatched;
            return this;
        }

        public AggregateMetricsInternalBuilder withNumHarmonizedMatched(int numHarmonizedMatched) {
            this.numHarmonizedMatched = numHarmonizedMatched;
            return this;
        }

        public AggregateMetricsInternal build() {
            return new AggregateMetricsInternal(
                    numFilesOrig,
                    numFilesTransform,
                    numFilesMatched,
                    numHarmonizableOrig,
                    numPartiallyHarmonizedOrig,
                    numHarmonizedOrig,
                    numHarmonizableTransform,
                    numPartiallyHarmonizedTransform,
                    numHarmonizedTransform,
                    numHarmonizableMatched,
                    numPartiallyHarmonizedMatched,
                    numHarmonizedMatched);
        }
    }
}

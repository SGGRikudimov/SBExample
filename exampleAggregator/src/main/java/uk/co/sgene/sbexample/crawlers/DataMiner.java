package uk.co.sgene.sbexample.crawlers;

import uk.co.sgene.sbexample.aggregator.MinerAwareItemReader;

public interface DataMiner<T extends MinedItem> {
    void mine(MinerAwareItemReader<T> reader);

    Boolean getIsRunning();

    void mine(MinerAwareItemReader<T> reader, long i);

    void interruptTask();
}

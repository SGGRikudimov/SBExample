package uk.co.sgene.sbexample.aggregator.source1;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import uk.co.sgene.sbexample.aggregator.AbstractItemReader;
import uk.co.sgene.sbexample.crawlers.DataMiner;
import uk.co.sgene.sbexample.crawlers.source1.Source1Item;
import uk.co.sgene.sbexample.crawlers.source1.Source1Miner;

public class Source1Reader extends AbstractItemReader<Source1Item> {

    public Source1Reader(ThreadPoolTaskExecutor taskExecutor) {
        super(taskExecutor);
    }

    @Override
    protected DataMiner<Source1Item> getMiner(ThreadPoolTaskExecutor taskExecutor) {
        return new Source1Miner(taskExecutor);
    }
}

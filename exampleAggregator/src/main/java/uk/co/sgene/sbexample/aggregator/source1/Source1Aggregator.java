package uk.co.sgene.sbexample.aggregator.source1;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import uk.co.sgene.sbexample.aggregator.Aggregator;
import uk.co.sgene.sbexample.aggregator.MinerAwareItemReader;
import uk.co.sgene.sbexample.crawlers.source1.Source1Item;
import uk.co.sgene.sbexample.dao.ResultingEntity;

@Component
public class Source1Aggregator extends Aggregator<Source1Item> {

    @Override
    protected String getSourceName() {
        return "Source1";
    }

    @Override
    protected ItemProcessor<Source1Item, ResultingEntity> getProcessor() {
        return new Source1Proccessor();
    }

    @Override
    protected MinerAwareItemReader<Source1Item> getReader() {
        Source1Reader reader = new Source1Reader(taskExecutor);
        reader.setName(this.getClass().getSimpleName());
        return reader;
    }
}

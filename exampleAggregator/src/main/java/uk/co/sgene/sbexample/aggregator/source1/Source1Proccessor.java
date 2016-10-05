package uk.co.sgene.sbexample.aggregator.source1;

import java.util.Date;

import org.springframework.batch.item.ItemProcessor;

import uk.co.sgene.sbexample.crawlers.source1.Source1Item;
import uk.co.sgene.sbexample.dao.ResultingEntity;

public class Source1Proccessor implements ItemProcessor<Source1Item, ResultingEntity> {

    private static final String ID_PREFIX = "S1_";

    @Override
    public ResultingEntity process(Source1Item item) throws Exception {
        return new ResultingEntity(item.getFirstName() + " " + item.getLastName(), ID_PREFIX + item.getId(), new Date());
    }

}

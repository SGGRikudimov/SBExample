package uk.co.sgene.sbexample.aggregator;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStream;

import uk.co.sgene.sbexample.crawlers.MinedItem;

public interface MinerAwareItemReader<T extends MinedItem> extends ItemReader<T>, ItemStream {
    public void addData(List<Pair<Long, T>> items);

    public void addItem(Pair<Long, T> item);
}

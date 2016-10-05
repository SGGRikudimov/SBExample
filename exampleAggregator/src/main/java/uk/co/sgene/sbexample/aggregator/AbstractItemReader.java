package uk.co.sgene.sbexample.aggregator;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.support.AbstractItemStreamItemReader;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import uk.co.sgene.sbexample.crawlers.DataMiner;
import uk.co.sgene.sbexample.crawlers.MinedItem;

public abstract class AbstractItemReader<T extends MinedItem> extends AbstractItemStreamItemReader<T> implements MinerAwareItemReader<T> {

    private LinkedBlockingQueue<Pair<Long, T>> queue = new LinkedBlockingQueue<>();

    private DataMiner<T> miner;

    private Long currentIdx;

    public AbstractItemReader(ThreadPoolTaskExecutor taskExecutor) {
        super();
        this.miner = getMiner(taskExecutor);
    }

    @Override
    public T read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        while (miner.getIsRunning()) {
            if (queue.isEmpty()) {
                Thread.sleep(200);
            } else {
                Pair<Long, T> next = queue.poll();
                currentIdx = next.getLeft();
                return next.getRight();
            }
        }
        return null;
    }

    @Override
    public void addData(List<Pair<Long, T>> items) {
        queue.addAll(items);
    }

    @Override
    public void addItem(Pair<Long, T> item) {
        queue.add(item);
    }

    protected abstract DataMiner<T> getMiner(ThreadPoolTaskExecutor taskExecutor);

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {
        if (executionContext.containsKey("itemId")) {
            LoggerFactory.getLogger(this.getClass()).debug("Restarting from item id:" + executionContext.getLong("itemId"));
            miner.mine(this, executionContext.getLong("itemId"));
        } else {
            LoggerFactory.getLogger(this.getClass()).debug("Starting new mining");
            miner.mine(this);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        if (currentIdx != null) {
            executionContext.putLong("itemId", currentIdx);
        }

    }

    @Override
    public void close() throws ItemStreamException {
        miner.interruptTask();
    }
}

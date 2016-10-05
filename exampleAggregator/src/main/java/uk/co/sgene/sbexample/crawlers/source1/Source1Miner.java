package uk.co.sgene.sbexample.crawlers.source1;

import java.util.concurrent.Future;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import lombok.Getter;
import uk.co.sgene.sbexample.aggregator.MinerAwareItemReader;
import uk.co.sgene.sbexample.crawlers.DataMiner;

public class Source1Miner implements DataMiner<Source1Item> {

    private MinerAwareItemReader<Source1Item> reader;
    @Getter
    private Boolean isRunning = false;

    private ThreadPoolTaskExecutor taskExecutor;

    private Future<?> task = null;

    private long i = 0;

    private void startMining() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    for (; i < 22; i++) {
                        reader.addItem(ImmutablePair.of(i, new Source1Item("Vasya" + i, "Pupkin", i)));
                        Thread.sleep(200L);
                    }
                } catch (InterruptedException e) {
                    LoggerFactory.getLogger(this.getClass()).warn("Data mining interrupted", e);
                } finally {
                    isRunning = false;
                }
            }
        };
        task = taskExecutor.submit(runnable);
    }

    @Override
    public void mine(MinerAwareItemReader<Source1Item> reader) {
        this.reader = reader;
        isRunning = true;
        i = 0;
        startMining();
    }

    @Override
    public void mine(MinerAwareItemReader<Source1Item> reader, long i) {
        this.reader = reader;
        isRunning = true;
        this.i = i;
        startMining();
    }

    @Override
    public void interruptTask() {
        if (task != null && !task.isDone() && !task.isCancelled()) {
            task.cancel(true);
        }
    }

    public Source1Miner(ThreadPoolTaskExecutor taskExecutor) {
        super();
        this.taskExecutor = taskExecutor;
    }

}

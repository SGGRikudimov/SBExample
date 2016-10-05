package uk.co.sgene.sbexample.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import uk.co.sgene.sbexample.aggregator.Aggregator;
import uk.co.sgene.sbexample.crawlers.source1.Source1Item;

@Component
public class Scheduler {

    @Autowired
    private Aggregator<Source1Item> s1Aggr;

    @Scheduled(initialDelay = 1000, fixedRate = 25000)
    public void processSource1() {
        s1Aggr.startJob();
    }
}

package uk.co.sgene;

import static org.junit.Assert.assertEquals;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import uk.co.sgene.sbexample.AppConfig;
import uk.co.sgene.sbexample.aggregator.source1.Source1Reader;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppConfig.class})
@TestPropertySource("classpath:application-test.properties")
public class ReaderTest {

    @Autowired
    protected ThreadPoolTaskExecutor taskExecutor;

    @Test
    public void testReadRestart() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
        Source1Reader reader = new Source1Reader(taskExecutor);
        reader.open(new ExecutionContext(Collections.singletonMap("itemId", 1L)));
        assertEquals(new Long(1L), reader.read().getId());
    }

    @Test
    public void testNewRead() throws UnexpectedInputException, ParseException, NonTransientResourceException, Exception {
        Source1Reader reader = new Source1Reader(taskExecutor);
        ExecutionContext context = new ExecutionContext();
        reader.open(context);
        for (int i = 0; i < 22; i++) {
            assertEquals(new Long(i), reader.read().getId());
        }
    }

}

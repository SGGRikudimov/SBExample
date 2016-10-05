package uk.co.sgene;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import uk.co.sgene.sbexample.AppConfig;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppConfig.class})
public class SocialAggregatorMinerApplicationTests {

    @Test
    public void contextLoads() {
    }

}

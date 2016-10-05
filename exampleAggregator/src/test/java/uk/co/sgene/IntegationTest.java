package uk.co.sgene;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import uk.co.sgene.sbexample.AppConfig;
import uk.co.sgene.sbexample.aggregator.Aggregator;
import uk.co.sgene.sbexample.crawlers.source1.Source1Item;
import uk.co.sgene.sbexample.dao.ResultingEntity;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppConfig.class})
@TestPropertySource("classpath:application-test.properties")
public class IntegationTest {
    @Autowired
    private Aggregator<Source1Item> s1Aggr;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void testRun() {
        JobExecution exe = s1Aggr.startJob();
        while (exe.getEndTime() == null) {
        }
        CriteriaQuery<ResultingEntity> criteria = entityManager.getCriteriaBuilder().createQuery(ResultingEntity.class);
        criteria.select(criteria.from(ResultingEntity.class));
        List<ResultingEntity> result = entityManager.createQuery(criteria).getResultList();
        assertEquals(22, result.size());
    }
}

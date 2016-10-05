package uk.co.sgene.sbexample.aggregator;

import java.util.Date;

import org.hibernate.SessionFactory;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.HibernateItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import uk.co.sgene.sbexample.crawlers.MinedItem;
import uk.co.sgene.sbexample.dao.ResultingEntity;

public abstract class Aggregator<T extends MinedItem> {

    @Autowired
    protected JobBuilderFactory jobBuilderFactory;

    @Autowired
    protected StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    protected ThreadPoolTaskExecutor taskExecutor;

    protected abstract MinerAwareItemReader<T> getReader();

    protected abstract String getSourceName();

    protected abstract ItemProcessor<T, ResultingEntity> getProcessor();

    protected ItemWriter<ResultingEntity> getWriter() {
        HibernateItemWriter<ResultingEntity> writer = new HibernateItemWriter<>();
        writer.setSessionFactory(sessionFactory);
        return writer;
    }

    public Step step1() {
        return stepBuilderFactory.get(getSourceName() + "_step1").<T, ResultingEntity> chunk(10).reader(getReader())
                .processor(getProcessor()).writer(getWriter()).build();
    }

    @Bean
    public Job job() {
        return jobBuilderFactory.get(getJobName()).flow(step1()).end().build();
    }

    public JobExecution startJob() {
        try {
            JobParameters params = new JobParametersBuilder().addDate("startDate", new Date()).toJobParameters();
            return jobLauncher.run(job(), params);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
                | JobParametersInvalidException e) {
            LoggerFactory.getLogger(this.getClass()).error("Something bad happened", e);
        }
        return null;
    }

    public String getJobName() {
        return getSourceName() + "_job";
    }
}

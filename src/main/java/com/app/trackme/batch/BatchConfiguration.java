package com.app.trackme.batch;

import com.app.trackme.domain.Track;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;
    private final RequestAltitudeItemProcessor requestAltitudeItemProcessor;

    private final int chunkSize = 10;

    @Bean
    public Job requestAltitudeJob() {
        return jobBuilderFactory.get("requestAltitudeJob")
                .start(requestAltitudeStep())
                .build();
    }

    @Bean
    public Step requestAltitudeStep() {
        return stepBuilderFactory.get("altitudeStep")
                .<Track, Track>chunk(chunkSize) // Set batch size
                .reader(requestAltitudeItemReader())
                .processor(requestAltitudeItemProcessor)
                .writer(requestAltitudeItemWriter())
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public JpaPagingItemReader<Track> requestAltitudeItemReader() {
        return new JpaPagingItemReaderBuilder<Track>()
                .name("requestAltitudeItemReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT t FROM Track t")
                .build();
    }

    @Bean
    public ItemWriter<Track> requestAltitudeItemWriter() {
        JpaItemWriter<Track> requestAltitudeItemWriter = new JpaItemWriter<>();
        requestAltitudeItemWriter.setEntityManagerFactory(entityManagerFactory);
        return requestAltitudeItemWriter;
    }

}

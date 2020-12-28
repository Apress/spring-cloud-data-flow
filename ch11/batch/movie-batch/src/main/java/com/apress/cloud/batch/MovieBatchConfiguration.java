package com.apress.cloud.batch;

import com.apress.cloud.batch.dropbox.DropboxUtils;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.task.configuration.EnableTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Log4j2
@AllArgsConstructor
@EnableTask
@EnableBatchProcessing
@Configuration
public class MovieBatchConfiguration {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;
    private DropboxUtils dropboxUtils;

    @Bean
    @StepScope
    public Tasklet movieTasklet(
            @Value("#{jobParameters['url']}") String url,
            @Value("#{jobParameters['imdbId']}") String imdbId,
            @Value("#{jobParameters['dropbox.token']}") String token,
            @Value("#{jobParameters['dropbox.path']}") String path,
            @Value("#{jobParameters['dropbox.local-tmp-folder']}") String tmp) {
        return (stepContribution, chunkContext) -> {

            log.debug("Using Image ID: {} and URL: {}", imdbId, url);
            assert url!=null && imdbId!=null;

            dropboxUtils.fromUrlToDropBox(
                    url,
                    imdbId + ".jpg",
                    token,
                    path,
                    tmp);

            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .tasklet(movieTasklet(null, null, null,null,null))
                .build();
    }

    @Bean
    public Job jobParametersJob() {
        return jobBuilderFactory.get("jobParametersJob")
                .start(step1())
                .build();
    }
}

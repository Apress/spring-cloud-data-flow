package com.apress.cloud.movie;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "movie")
public class MovieProperties {
    private String directory;
    private String filePattern;
    private Boolean preventDuplicates;
    private Long fixedDelay;

    private String remoteService = "http://localhost:8181/v1/movie";
}

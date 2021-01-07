package com.apress.cloud.stream.movie;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "movie")
public class MovieDslProperties {
    private String action = "create"; // create | deploy | destroy
}

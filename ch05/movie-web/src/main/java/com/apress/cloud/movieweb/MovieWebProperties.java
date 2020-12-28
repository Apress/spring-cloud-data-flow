package com.apress.cloud.movieweb;

        import lombok.Data;
        import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "movie-web")
public class MovieWebProperties {

    private String path;
}

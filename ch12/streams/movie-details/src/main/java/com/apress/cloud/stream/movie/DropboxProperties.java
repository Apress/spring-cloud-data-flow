package com.apress.cloud.stream.movie;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dropbox")
public class DropboxProperties {

    private String token = null;
    private String path = "/IMDB/";
    private String localTmpFolder = "/tmp/";
}

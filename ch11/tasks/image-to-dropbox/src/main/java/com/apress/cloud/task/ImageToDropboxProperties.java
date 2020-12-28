package com.apress.cloud.task;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dropbox")
public class ImageToDropboxProperties {

    private String apiKey = null;
    private String path = "/IMDB/";
    private String localTmpFolder = "/tmp/";

}

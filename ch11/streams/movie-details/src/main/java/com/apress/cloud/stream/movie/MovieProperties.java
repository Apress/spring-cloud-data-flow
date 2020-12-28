package com.apress.cloud.stream.movie;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "movie")
public class MovieProperties {
    String apiServer = "https://imdb8.p.rapidapi.com/title/get-details?tconst=ID";
    String headerHost = "imdb8.p.rapidapi.com";
    String headerKey = null;
    String taskName = "movie-dropbox-batch";
    DropboxProperties dropbox = new DropboxProperties();
}

package com.apress.cloud.stream.movie;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MovieGenre {

    String INPUT = "movie-genre";
    String GENRE_SCIENCE_FICTION = "science-fiction";
    String GENRE_ROMANCE = "romance";
    String GENRE_HORROR = "horror";
    String GENRE_DEFAULT = "default-output";

    @Input(INPUT)
    SubscribableChannel movie();

    @Output(GENRE_HORROR)
    MessageChannel horror();

    @Output(GENRE_SCIENCE_FICTION)
    MessageChannel scienceFiction();

    @Output(GENRE_ROMANCE)
    MessageChannel romance();
}

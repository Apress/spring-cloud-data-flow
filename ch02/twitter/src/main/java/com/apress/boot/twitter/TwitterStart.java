package com.apress.boot.twitter;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.*;

import java.util.Collections;

@Configuration
public class TwitterStart {

    @Bean
    CommandLineRunner start(Twitter twitter){
        return args -> {
            twitter.streamingOperations().sample(Collections.singletonList(new StreamListener() {

                @Override
                public void onTweet(Tweet tweet) {
                    tweet.getEntities().getHashTags().forEach(hashTagEntity -> {
                        System.out.println(String.format("#%s",hashTagEntity.getText()));
                    });
                }

                @Override
                public void onDelete(StreamDeleteEvent streamDeleteEvent) { }

                @Override
                public void onLimit(int i) { }

                @Override
                public void onWarning(StreamWarningEvent streamWarningEvent) { }
            }));
        };
    }
}

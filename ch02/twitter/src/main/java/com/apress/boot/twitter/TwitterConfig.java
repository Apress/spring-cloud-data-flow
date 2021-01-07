package com.apress.boot.twitter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

@Configuration
@EnableConfigurationProperties(TwitterProperties.class)
public class TwitterConfig {

    private TwitterProperties twitterProperties;

    public TwitterConfig(TwitterProperties twitterProperties){
        this.twitterProperties = twitterProperties;
    }

    @Bean
    TwitterTemplate twitterTemplate(){
        return new TwitterTemplate(twitterProperties.getConsumerKey(),
                twitterProperties.getConsumerSecret(), twitterProperties.getAccessToken(), twitterProperties.getAccessTokenSecret());
    }
}

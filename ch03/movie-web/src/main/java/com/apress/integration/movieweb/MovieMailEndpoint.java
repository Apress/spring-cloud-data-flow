package com.apress.integration.movieweb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.mail.MailMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.handler.annotation.Headers;

import java.util.Date;
import java.util.Map;

@MessageEndpoint
public class MovieMailEndpoint {

    private static final Logger log = LoggerFactory.getLogger(MovieMailEndpoint.class);

    @ServiceActivator
    public MailMessage process(Movie movie, @Headers Map<String,Object> headers) throws Exception {
        log.info("Movie: " + movie);

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo("myuser@mydomain.com");
        mailMessage.setSubject("A new Movie is in Town");
        mailMessage.setSentDate(new Date(0));
        mailMessage.setText(movie.toString());

        return mailMessage;
    }
}

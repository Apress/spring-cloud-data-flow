package com.apress.integration.movie;

/* VERSION 1 */
/*
import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.util.StreamUtils;

@MessageEndpoint
public class MovieEndpoint {

    private static final Logger log = LoggerFactory.getLogger(MovieEndpoint.class);

    @ServiceActivator
    public void process(File input, @Headers Map<String,Object> headers) throws Exception {
        FileInputStream in = new FileInputStream(input);
        String movies = new String(StreamUtils.copyToByteArray(in));
        in.close();
        log.info("Received: \n" +  movies);
    }
}
*/


/* VERSION 2 */
/*
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;


@MessageEndpoint
public class MovieEndpoint {

    private static final Logger log = LoggerFactory.getLogger(MovieEndpoint.class);

    @Autowired
    private MovieService service;

    @ServiceActivator
    public void process(File input, @Headers Map<String,Object> headers) throws Exception {
        FileInputStream in = new FileInputStream(input);
        String movies = service.format(new String(StreamUtils.copyToByteArray(in)));
        in.close();
        log.info("Movies Received: \n" +  movies);
    }
}
*/

/* VERSION 3 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

@MessageEndpoint
public class MovieEndpoint {

    private static final Logger log = LoggerFactory.getLogger(MovieEndpoint.class);

    @Autowired
    private MovieService service;

    @ServiceActivator
    public Message<String> process(File input, @Headers Map<String,Object> headers) throws Exception {
        FileInputStream in = new FileInputStream(input);
        String movies = service.format(new String(StreamUtils.copyToByteArray(in)));
        in.close();
        log.info("Sending the JSON content to a file...");
        return MessageBuilder.withPayload(movies).setHeader("name",input.getName()).setHeader("Content-Type","application/json").build();
    }
}

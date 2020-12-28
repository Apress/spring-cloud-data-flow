package com.apress.cloud.stream.movie;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class MovieConverter implements Converter<String,Movie> {
    @Override
    public Movie convert(String s) {
        List<String> fields = Stream.of(s.split(",")).map(String::trim).collect(Collectors.toList());
        return new Movie(fields.get(0),fields.get(1),Integer.valueOf(fields.get(2)));
    }
}

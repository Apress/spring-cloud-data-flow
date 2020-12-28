package com.apress.cloud.stream.movie;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class MoviePayload {
    String name;
    String[] args;
}

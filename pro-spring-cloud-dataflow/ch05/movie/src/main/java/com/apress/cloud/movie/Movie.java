package com.apress.cloud.movie;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Movie {
    private String title;
    private String actor;
    private int year;
}

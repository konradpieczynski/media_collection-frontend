package com.media_collection.frontend.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
    private Long movieId;
    private String movieTitle;
    private int movieYear;
    private Set<Long> movieCollections = new HashSet<>();
}

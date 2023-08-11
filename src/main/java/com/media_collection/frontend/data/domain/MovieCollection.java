package com.media_collection.frontend.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieCollection {
    private Long movieCollectionId;
    private Long userId;
    private String movieCollectionName;
    private Set<Long> movies = new HashSet<>();
}

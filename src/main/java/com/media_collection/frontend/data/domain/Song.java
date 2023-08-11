package com.media_collection.frontend.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    private Long songId;
    private String songTitle;
    private String songAuthor;
    private Set<Long> songCollections = new HashSet<>();
}

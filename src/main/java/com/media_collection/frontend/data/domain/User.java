package com.media_collection.frontend.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String userName;
    private Suggestions suggestions;
    private Set<Long> movieCollectionList = new HashSet<>();
    private Set<Long> songCollectionList = new HashSet<>();
}

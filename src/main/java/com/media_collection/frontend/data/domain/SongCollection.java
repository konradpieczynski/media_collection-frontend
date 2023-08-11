package com.media_collection.frontend.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongCollection
{
    private Long songCollectionId;
    private Long userId;
    private String songCollectionName;
    private Set<Long> songs = new HashSet<>();
}

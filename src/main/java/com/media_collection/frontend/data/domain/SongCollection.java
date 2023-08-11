package com.media_collection.frontend.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SongCollection
{
    int songCollectionId;
    String songCollectionName;
}
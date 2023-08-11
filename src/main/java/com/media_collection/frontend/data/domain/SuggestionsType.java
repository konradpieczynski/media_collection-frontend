package com.media_collection.frontend.data.domain;

import lombok.Getter;

@Getter
public enum SuggestionsType {
    MOVIES("movies"),
    SONGS("songs");

    private final String value;
    SuggestionsType(String value) {
        this.value = value;
    }
}

package com.media_collection.frontend.data.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Suggestions {
    String type;
    Set<Movie> suggestedMovies;
    Set<Song> suggestedSongs;
    @Override
    public String toString() {
        if (type.equals("movies")) {
            return suggestedMovies.stream().map(Movie::getMovieTitle).collect(Collectors.joining("; "));
        }
        else {
            return suggestedSongs.stream().map(Song::getSongTitle).collect(Collectors.joining("; "));
        }
    }
}

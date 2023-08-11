package com.media_collection.frontend.data.service;

import com.media_collection.frontend.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Component
public class BackendService {
    List<User> users = new ArrayList<>(List.of(new User(1L,"Test user",new Suggestions("movies",new HashSet<>()),new HashSet<>(),new HashSet<>())));
    public List<User> findAllUsers(String value) {
        return users;
    }
    public void saveUser(User user)
    {
        users.add(user);
    }
    public void deleteUser(User user)
    {
        users.remove(user);
    }
    List<Song> songs = new ArrayList<>(List.of(new Song(1L,"Test song","test author", new HashSet<>())));
    public List<Song> findAllSongs(String value) {
        return songs;
    }
    public void saveSong(Song song)
    {
        songs.add(song);
    }
    public void deleteSong(Song song)
    {
        songs.remove(song);
    }
    List<SongCollection> songCollections = new ArrayList<>(List.of(new SongCollection(1L,1L,"Test song collection",new HashSet<>())));
    public List<SongCollection> findAllSongCollections(String value) {
        return songCollections;
    }
    public void saveSongCollection(SongCollection songCollection)
    {
        songCollections.add(songCollection);
    }
    public void deleteSongCollection(SongCollection songCollection)
    {
        songCollections.remove(songCollection);
    }
    List<Movie> movies = new ArrayList<>(List.of(new Movie(1L,"Test movie",1999, new HashSet<>())));
    public List<Movie> findAllMovies(String value) {
        return movies;
    }
    public void saveMovie(Movie movie)
    {
        movies.add(movie);
    }
    public void deleteMovie(Movie movie)
    {
        movies.remove(movie);
    }
    List<MovieCollection> movieCollections = new ArrayList<>(List.of(new MovieCollection(1L,1L,"Test movie collection",new HashSet<>())));
    public List<MovieCollection> findAllMovieCollections(String value) {
        return movieCollections;
    }
    public void saveMovieCollection(MovieCollection movieCollection)
    {
        movieCollections.add(movieCollection);
    }
    public void deleteMovieCollection(MovieCollection movieCollection)
    {
        movieCollections.remove(movieCollection);
    }
}

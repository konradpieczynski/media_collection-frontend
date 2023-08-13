package com.media_collection.frontend.data.service;

import com.media_collection.frontend.config.BackendConfig;
import com.media_collection.frontend.data.domain.*;
import lombok.Getter;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Component
public class BackendService {
    final BackendConfig backendConfig;
    WebClient webClient = WebClient.create();
    @Getter
    List<User> userCache = new ArrayList<>();
    @Getter
    List<Song> songCache = new ArrayList<>();
    @Getter
    List<SongCollection> songCollectionCache = new ArrayList<>();
    @Getter
    List<Movie> movieCache = new ArrayList<>();
    @Getter
    List<MovieCollection> movieCollectionCache = new ArrayList<>();

    public BackendService(BackendConfig backendConfig) {
        this.backendConfig = backendConfig;
        updateCache();
    }
    public void updateCache(){
        this.userCache = getUsers();
        this.songCache = getSongs();
        this.songCollectionCache = getSongCollections();
        this.movieCache = getMovies();
        this.movieCollectionCache = getMovieCollections();
    }
    public List<User> getUsers() {
        return webClient.get()
                .uri(backendConfig.backendUrl + "/users")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(User.class)
                .collectList()
                .block();
    }

    public List<User> findAllUsers(String value) {
        List<User> users = userCache;
        return users.stream().filter(n -> n.getUserName().toLowerCase().contains(value.toLowerCase())).toList();
    }
    public void saveUser(User user)
    {
        if (user.getUserId() == null) {
            webClient.put()
                    .uri(backendConfig.backendUrl + "/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        else {
            webClient.post()
                    .uri(backendConfig.backendUrl + "/users")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(user)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        updateCache();
    }
    public void deleteUser(User user)
    {
        webClient.method(HttpMethod.DELETE)
                .uri(backendConfig.backendUrl + "/users/" +user.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(user)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        updateCache();
    }
    public List<Song> getSongs() {
        return webClient.get()
                .uri(backendConfig.backendUrl + "/songs")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Song.class)
                .collectList()
                .block();
    }

    public List<Song> findAllSongs(String value) {
        List<Song> songs = getSongs();
        return songs.stream().filter(n -> n.getSongTitle().toLowerCase().contains(value.toLowerCase())).toList();
    }
    public void saveSong(Song song)
    {
        if (song.getSongId() == null) {
            webClient.put()
                    .uri(backendConfig.backendUrl + "/songs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(song)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        else {
            webClient.post()
                    .uri(backendConfig.backendUrl + "/songs")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(song)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        updateCache();
    }
    public void deleteSong(Song song)
    {
        webClient.method(HttpMethod.DELETE)
                .uri(backendConfig.backendUrl + "/songs/" +song.getSongId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(song)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        updateCache();
    }
    public List<Movie> getMovies() {
        return webClient.get()
                .uri(backendConfig.backendUrl + "/movies")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Movie.class)
                .collectList()
                .block();
    }

    public List<Movie> findAllMovies(String value) {
        List<Movie> movies = getMovies();
        return movies.stream().filter(n -> n.getMovieTitle().toLowerCase().contains(value.toLowerCase())).toList();
    }
    public void saveMovie(Movie movie)
    {
        if (movie.getMovieId() == null) {
            webClient.put()
                    .uri(backendConfig.backendUrl + "/movies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(movie)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        else {
            webClient.post()
                    .uri(backendConfig.backendUrl + "/movies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(movie)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        updateCache();
    }
    public void deleteMovie(Movie movie)
    {
        webClient.method(HttpMethod.DELETE)
                .uri(backendConfig.backendUrl + "/movies/" +movie.getMovieId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(movie)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        updateCache();
    }
    public List<SongCollection> getSongCollections() {
        return webClient.get()
                .uri(backendConfig.backendUrl + "/songCollections")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(SongCollection.class)
                .collectList()
                .block();
    }

    public List<SongCollection> findAllSongCollections(String value) {
        List<SongCollection> songCollections = getSongCollections();
        return songCollections.stream().filter(n -> n.getSongCollectionName().toLowerCase().contains(value.toLowerCase())).toList();
    }
    public void saveSongCollection(SongCollection songCollection)
    {
        if (songCollection.getSongCollectionId() == null) {
            webClient.put()
                    .uri(backendConfig.backendUrl + "/songCollections")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(songCollection)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        else {
            webClient.post()
                    .uri(backendConfig.backendUrl + "/songCollections")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(songCollection)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        updateCache();
    }
    public void deleteSongCollection(SongCollection songCollection)
    {
        webClient.method(HttpMethod.DELETE)
                .uri(backendConfig.backendUrl + "/songCollections/" +songCollection.getSongCollectionId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(songCollection)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        updateCache();
    }
    public List<MovieCollection> getMovieCollections() {
        return webClient.get()
                .uri(backendConfig.backendUrl + "/movieCollections")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(MovieCollection.class)
                .collectList()
                .block();
    }

    public List<MovieCollection> findAllMovieCollections(String value) {
        List<MovieCollection> movieCollections = getMovieCollections();
        return movieCollections.stream().filter(n -> n.getMovieCollectionName().toLowerCase().contains(value.toLowerCase())).toList();
    }
    public void saveMovieCollection(MovieCollection movieCollection)
    {
        if (movieCollection.getMovieCollectionId() == null) {
            webClient.put()
                    .uri(backendConfig.backendUrl + "/movieCollections")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(movieCollection)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        else {
            webClient.post()
                    .uri(backendConfig.backendUrl + "/movieCollections")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(movieCollection)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
        }
        updateCache();
    }
    public void deleteMovieCollection(MovieCollection movieCollection)
    {
        webClient.method(HttpMethod.DELETE)
                .uri(backendConfig.backendUrl + "/movieCollections/" +movieCollection.getMovieCollectionId())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(movieCollection)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
        updateCache();
    }
    public String mapUserIdToName(Long id){
        List<User> userList = getUserCache();
        for (User user: userList
        ) {
            if (user.getUserId().equals(id)) return
            "Id: "+ user.getUserId() + ", name: " + user.getUserName();
        }
        return "No user";
    }
    public String mapSongIdToTitle(Long id){
        List<Song> songList = getSongCache();
        for (Song song: songList
        ) {
            if (song.getSongId().equals(id)) return
                    "Id: "+ song.getSongId() + ", title: " + song.getSongTitle();
        }
        return "No song";
    }
    public String mapMovieIdToTitle(Long id){
        List<Movie> movieList = getMovieCache();
        for (Movie movie: movieList
        ) {
            if (movie.getMovieId().equals(id)) return
                    "Id: "+ movie.getMovieId() + ", title: " + movie.getMovieTitle();
        }
        return "No movie";
    }
    public String mapSongCollectionIdToName(Long id){
        List<SongCollection> songCollectionList = getSongCollectionCache();
        for (SongCollection songCollection: songCollectionList
        ) {
            if (songCollection.getSongCollectionId().equals(id)) return
                    "Id: "+ songCollection.getSongCollectionId() + ", name: " + songCollection.getSongCollectionName();
        }
        return "No songCollection";
    }
    public String mapMovieCollectionIdToName(Long id){
        List<MovieCollection> movieCollectionList = getMovieCollectionCache();
        for (MovieCollection movieCollection: movieCollectionList
        ) {
            if (movieCollection.getMovieCollectionId().equals(id)) return
                    "Id: "+ movieCollection.getMovieCollectionId() + ", name: " + movieCollection.getMovieCollectionName();
        }
        return "No movieCollection";
    }
}

package fr.centralesupelec.sio.model;

import java.util.EnumSet;

/**
 * An entity class for a movie.
 */
public class Movie {

    private long id;
    private String title;
    // MovieGenre is an enum, combinations of enums are best handled by EnumSet.
    private EnumSet<MovieGenre> genres;
    private String genreMovie;
    private String director;

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    private String actors;

    public long getId() {
        return id;
    }

    public String getGenreMovie() {
        return genreMovie;
    }

    public void setGenreMovie(String genreMovie) {
        this.genreMovie = genreMovie;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public EnumSet<MovieGenre> getGenres() {
        return genres;
    }

    public void setGenres(EnumSet<MovieGenre> genres) {
        this.genres = genres;
    }
}
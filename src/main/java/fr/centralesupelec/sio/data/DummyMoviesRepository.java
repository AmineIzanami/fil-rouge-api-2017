package fr.centralesupelec.sio.data;

import fr.centralesupelec.sio.model.Movie;
import fr.centralesupelec.sio.model.MovieGenre;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.sql.*;

/**
 * A concrete {@link MoviesRepository} backed by an in-memory list of static {@link Movie} entities.
 */
// This class is not accessible outside of its package.
class DummyMoviesRepository extends MoviesRepository {

    // Hold entities in a simple list.
    private final List<Movie> mMovies;

    DummyMoviesRepository() {
        // Define some static movies on creation
        Movie m1 = new Movie();
        m1.setId(1);
        m1.setTitle("Lord of the Rings: The Return of the King");
        m1.setGenres(EnumSet.of(MovieGenre.FANTASY));
        Movie m2 = new Movie();
        m2.setId(2);
        m2.setTitle("Star Wars VIII: The Last Jedi");
        m2.setGenres(EnumSet.of(MovieGenre.SCIENCE_FICTION));
        Movie m3 = new Movie();
        m3.setId(3);
        m3.setTitle("Kingsman 2: The Golden Circle");
        m3.setGenres(EnumSet.of(MovieGenre.COMEDY, MovieGenre.ACTION));
        mMovies = Arrays.asList(m1, m2, m3);
    }

    @Override
    public List<Movie> getMovies() {
        return mMovies;
    }

    @Override
    public Movie getMovie(long id) {
        // See DummyAccountsRepository for more details an variants.
        return mMovies.parallelStream()
                .filter(movie -> movie.getId() == id)
                .findFirst()
                .orElse(null);
    }



    }



package fr.centralesupelec.sio.data;


import fr.centralesupelec.sio.model.Movie;
import fr.centralesupelec.sio.model.MovieGenre;

import java.sql.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import static fr.centralesupelec.sio.data.DatabaseActorsRepository.getNameActor;
import static fr.centralesupelec.sio.data.DatabaseDirectorsRepository.getNameDirector;

/**
 * A {@link MoviesRepository} backed by a database.
 */
// Example implementation of another storage
public class DatabaseMoviesRepository extends MoviesRepository {

    @Override
    public List<Movie> getMovies() {
        // TODO
        throw new UnsupportedOperationException("Not implemented!");
    }

    @Override
    public Movie getMovie(long id) {
        // TODO
        throw new UnsupportedOperationException("Not implemented!");
    }

    public static List<Movie> getAllMovies(){
        List<Movie> mMovies = new ArrayList<Movie>();;

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database\\movies.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * from movies" );
            while ( rs.next() ) {
                String  movieId = rs.getString("movieId");
                String  title = rs.getString("title");
                String  genres = rs.getString("genres");
                String  director = rs.getString("Director");
                String  actor = rs.getString("Actors");
                Movie m1 = new Movie();
                m1.setId(Integer.parseInt(movieId));
                m1.setTitle(title);
                m1.setGenreMovie(genres);
                m1.setDirector(director);
                m1.setActors(actor);

                mMovies.add(m1);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        System.out.println("Operation done successfully");

        return mMovies;
    }
    public static List<Movie> getMovieByTitle(String movieTitle){
        List<Movie> mMovies = new ArrayList<Movie>();;

        Connection c = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database\\movies.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * from movies WHERE  title like upper(?)");
            stmt.setString(1, "%" + movieTitle.toUpperCase()     + "%");
            ResultSet rs = stmt.executeQuery( );

            while ( rs.next() ) {
                String  movieId = rs.getString("movieId");
                String  title = rs.getString("title");
                String  genres = rs.getString("genres");
                String  director = rs.getString("Director");
                String  actor = rs.getString("Actors");
                Movie m1 = new Movie();
                m1.setId(Integer.parseInt(movieId));
                m1.setTitle(title);
                m1.setGenreMovie(genres);
                m1.setDirector(director);
                m1.setActors(actor);

                mMovies.add(m1);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        System.out.println("Operation done successfully");

        return mMovies;
    }

    public static List<Movie> getMoviesByGenre(String movieGenre){
        List<Movie> mMovies = new ArrayList<Movie>();;

        Connection c = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database\\movies.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * from movies WHERE  genres like ?");
            stmt.setString(1, "%" + movieGenre + "%");
            ResultSet rs = stmt.executeQuery( );

            while ( rs.next() ) {
                String  movieId = rs.getString("movieId");
                String  title = rs.getString("title");
                String  genres = rs.getString("genres");
                String  director = rs.getString("Director");
                String  actor = rs.getString("Actors");
                Movie m1 = new Movie();
                m1.setId(Integer.parseInt(movieId));
                m1.setTitle(title);
                m1.setGenreMovie(genres);
                m1.setDirector(director);
                m1.setActors(actor);

                mMovies.add(m1);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        System.out.println("Operation done successfully");

        return mMovies;
    }
    public static List<Movie> getMoviesByActors(String movieActor){
        List<Movie> mMovies = new ArrayList<Movie>();;
        String[] arrayActor = movieActor.split(",", -1);
        for (String iterActor: arrayActor) {
            String nameActor = getNameActor(iterActor);
            Connection c = null;
            PreparedStatement stmt = null;
            try {
                Class.forName("org.sqlite.JDBC");
                c = DriverManager.getConnection("jdbc:sqlite:Database\\movies.db");
                c.setAutoCommit(false);
                System.out.println("Opened database successfully");

                stmt = c.prepareStatement("SELECT * from movies WHERE Actors like ? limit 1");
                stmt.setString(1, "%" + nameActor + "%");
                ResultSet rs = stmt.executeQuery();

                while (rs.next()) {
                    String movieId = rs.getString("movieId");
                    String title = rs.getString("title");
                    String genres = rs.getString("genres");
                    String director = rs.getString("Director");
                    String actor = rs.getString("Actors");
                    Movie m1 = new Movie();
                    m1.setId(Integer.parseInt(movieId));
                    m1.setTitle(title);
                    m1.setGenreMovie(genres);
                    m1.setDirector(director);
                    m1.setActors(actor);

                    mMovies.add(m1);
                }
                rs.close();
                stmt.close();
                c.close();
            } catch (Exception e) {
                System.err.println(e.getClass().getName() + ": " + e.getMessage());
                System.exit(0);

            }
        }
        System.out.println("Operation done successfully");

        return mMovies;
    }
    public static List<Movie> getMoviesByDirector(String movieDirector){
        List<Movie> mMovies = new ArrayList<Movie>();;
        String nameDirector = getNameDirector(movieDirector);
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database\\movies.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * from movies WHERE  Director like ?");
            stmt.setString(1, "%" + nameDirector + "%");
            ResultSet rs = stmt.executeQuery( );

            while ( rs.next() ) {
                String  movieId = rs.getString("movieId");
                String  title = rs.getString("title");
                String  genres = rs.getString("genres");
                String  director = rs.getString("Director");
                String  actor = rs.getString("Actors");
                Movie m1 = new Movie();
                m1.setId(Integer.parseInt(movieId));
                m1.setTitle(title);
                m1.setGenreMovie(genres);
                m1.setDirector(director);
                m1.setActors(actor);

                mMovies.add(m1);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        System.out.println("Operation done successfully");

        return mMovies;
    }
    public static List<Movie> getMoviesByAll(String movieTitle,String movieGenre, String movieDirector,Integer limit){
        List<Movie> mMovies = new ArrayList<Movie>();;
        String nameDirector = getNameDirector(movieDirector);
        Connection c = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:Database\\movies.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * from movies WHERE title like upper(?) and genres like ? and Director like ? limit ?");
            stmt.setString(1, "%" + movieTitle.toUpperCase()+ "%");
            stmt.setString(2, "%" + movieGenre+ "%");
            stmt.setString(3, "%" + nameDirector + "%");
            stmt.setString(4, "%" + limit + "%");
            ResultSet rs = stmt.executeQuery( );

            while ( rs.next() ) {
                String  movieId = rs.getString("movieId");
                String  title = rs.getString("title");
                String  genres = rs.getString("genres");
                String  director = rs.getString("Director");
                String  actor = rs.getString("Actors");
                Movie m1 = new Movie();
                m1.setId(Integer.parseInt(movieId));
                m1.setTitle(title);
                m1.setGenreMovie(genres);
                m1.setDirector(director);
                m1.setActors(actor);

                mMovies.add(m1);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        System.out.println("Operation done successfully");

        return mMovies;
    }
}

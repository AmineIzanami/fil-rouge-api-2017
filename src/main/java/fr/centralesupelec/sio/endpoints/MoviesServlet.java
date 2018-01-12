package fr.centralesupelec.sio.endpoints;


import fr.centralesupelec.sio.data.DatabaseMoviesRepository;
import fr.centralesupelec.sio.endpoints.utils.ResponseHelper;
import fr.centralesupelec.sio.model.Movie;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A servlet to access the list of {@link Movie}s.
 */
// The following pattern will exactly match /movies only.
@WebServlet(urlPatterns = "/movies")
public class MoviesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Movie> movies = new ArrayList<Movie>();;

        if(     req.getParameter("title")!=null
                &&
                !req.getParameter("title").isEmpty()
                &&
                req.getParameter("director")!=null
                &&
                !req.getParameter("director").isEmpty()
                &&
                req.getParameter("genre")!=null
                &&
                !req.getParameter("genre").isEmpty()
                &&
                req.getParameter("limit")!=null
                &&
                !req.getParameter("limit").isEmpty()
        ) {
            String title = req.getParameter("title");
            String director = req.getParameter("director");
            String genre = req.getParameter("genre");
            Integer limit = Integer.valueOf(req.getParameter("limit"));
            movies = DatabaseMoviesRepository.getMoviesByAll(title,genre,director);
            ResponseHelper.writeJsonResponse(resp,movies.parallelStream().skip(limit*10).limit(limit));
        }
        else if(req.getParameter("title")!=null
                &&
                !req.getParameter("title").isEmpty()
                &&
                req.getParameter("genre")!=null
                &&
                !req.getParameter("genre").isEmpty()){
            String title = req.getParameter("title");
            String genre = req.getParameter("genre");
            movies = DatabaseMoviesRepository.getMovieByTitle(title);
            movies.removeIf(p -> !p.getGenreMovie().contains(genre));

        }
        else if(req.getParameter("title")!=null && !req.getParameter("title").isEmpty()){
            String title = req.getParameter("title");
            movies = DatabaseMoviesRepository.getMovieByTitle(title);
        }
        else if(req.getParameter("director")!=null && !req.getParameter("director").isEmpty()) {
            String director = req.getParameter("director");
            movies = DatabaseMoviesRepository.getMoviesByDirector(director);

        }
        else if(req.getParameter("genre")!=null && !req.getParameter("genre").isEmpty()) {
            String genre = req.getParameter("genre");
            movies = DatabaseMoviesRepository.getMoviesByGenre(genre);
        }
        else if(req.getParameter("limit")!=null && !req.getParameter("limit").isEmpty()) {
            movies = DatabaseMoviesRepository.getAllMovies();
            Integer limit = Integer.valueOf(req.getParameter("limit"));
            ResponseHelper.writeJsonResponse(resp,movies.parallelStream().skip(limit*10).limit(limit));
        }
        else{
            movies = DatabaseMoviesRepository.getAllMovies();
            }
        ResponseHelper.writeJsonResponse(resp,movies);

    }

}

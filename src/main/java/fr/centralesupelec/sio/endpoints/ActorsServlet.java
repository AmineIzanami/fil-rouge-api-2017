package fr.centralesupelec.sio.endpoints;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.centralesupelec.sio.data.DatabaseActorsRepository;
import fr.centralesupelec.sio.data.DatabaseMoviesRepository;

import fr.centralesupelec.sio.endpoints.utils.ResponseHelper;
import fr.centralesupelec.sio.model.Actor;
import fr.centralesupelec.sio.model.Movie;
import fr.centralesupelec.sio.model.MovieGenre;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static fr.centralesupelec.sio.model.MovieGenre.getGenres;


@WebServlet(urlPatterns = "/actors")
public class ActorsServlet extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Get movies from the repository.
        // TODO: Add pagination parameters
        List<Actor> actors = null;
        List<Movie> movies = null;
        if(req.getParameter("actor")!= null) {
            String actor = req.getParameter("actor");
            movies = DatabaseMoviesRepository.getMoviesByActors(actor);
            ResponseHelper.writeJsonResponse(resp,movies);
        }
        else if(req.getParameter("limit")!= null){
            Integer limit = Integer.valueOf(req.getParameter("limit"));
            actors = DatabaseActorsRepository.getAllActors(limit);
            ResponseHelper.writeJsonResponse(resp,actors);
        }
       else {
            actors = DatabaseActorsRepository.getAllActors(300);
            ResponseHelper.writeJsonResponse(resp,actors);
        }
        //Write to the response.

    }

}
package fr.centralesupelec.sio.data;

import fr.centralesupelec.sio.model.Actor;
import fr.centralesupelec.sio.model.Movie;
import fr.centralesupelec.sio.model.MovieGenre;

import java.sql.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


// Example implementation of another storage
public class DatabaseActorsRepository {

    public static List<Actor> getAllActors(Integer limit){
        List<Actor> mActors = new ArrayList<Actor>();;

        Connection c = null;
        PreparedStatement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:\\resources\\actors.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * from actors limit ?");
            stmt.setInt(1, limit );
            ResultSet rs = stmt.executeQuery( );
            while ( rs.next() ) {
                String  id_actor = rs.getString("id_actor");
                String  nameActor = rs.getString("actor");

                Actor a1 = new Actor();
                a1.setIdActor(id_actor);
                a1.setNameActor(nameActor);


                mActors.add(a1);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        System.out.println("Operation done successfully");

        return mActors;
    }
    public static String getNameActor(String idActor){


        Connection c = null;
        PreparedStatement stmt = null;
        String nameActor = "";

        try {

            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:resources\\actors.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * from actors where id_actor =  ? limit 1");
            stmt.setString(1, idActor );
            ResultSet rs = stmt.executeQuery( );
           while(rs.next()) {
               nameActor = rs.getString("actor");
           }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        System.out.println("Operation done successfully");


        return nameActor;
    }

}

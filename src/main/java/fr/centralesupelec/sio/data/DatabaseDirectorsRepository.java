package fr.centralesupelec.sio.data;

import fr.centralesupelec.sio.model.Actor;
import fr.centralesupelec.sio.model.Director;
import fr.centralesupelec.sio.model.Movie;
import fr.centralesupelec.sio.model.MovieGenre;

import java.sql.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;


// Example implementation of another storage
public class DatabaseDirectorsRepository {

    public static List<Director> getAllDirectors(){
        List<Director> mDirector = new ArrayList<Director>();;

        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:resources\\director.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery( "SELECT * from director" );
            while ( rs.next() ) {
                String  idDirector = rs.getString("id_director");
                String  nameDirector = rs.getString("directore");

                Director d1 = new Director();
                 d1.setIdDirector(idDirector);
                d1.setNameDirector(nameDirector);


                mDirector.add(d1);
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        System.out.println("Operation done successfully");

        return mDirector;
    }

    public static String getNameDirector(String idDirector){


        Connection c = null;
        PreparedStatement stmt = null;
        String nameDirector = "";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:resources\\director.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.prepareStatement("SELECT * from director where id_director = ? limit 1");
            stmt.setString(1, idDirector );
            ResultSet rs = stmt.executeQuery( );
            while(rs.next()) {
                nameDirector = rs.getString("directore");
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);

        }
        System.out.println("Operation done successfully");


        return nameDirector;
    }


}

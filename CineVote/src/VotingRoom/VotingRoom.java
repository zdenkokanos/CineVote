package VotingRoom;

import People.*;
import Voters.*;

import java.util.*;

public class VotingRoom {
    private List<Movie> movies;
    private List<Voters> voters;
    public VotingRoom(){
        movies = new ArrayList<>();
        voters = new ArrayList<>();
        populateMovies();
        makeVoters();
    }

    public void populateMovies() {
        movies.add(new Movie("Movie 1", new Director("Director 1", 21), new Actor("Actor 1", 21), 2000));
        movies.add(new Movie("Movie 2", new Director("Director 2", 45), new Actor("Actor 2", 30), 2005));
        movies.add(new Movie("Movie 3", new Director("Director 3", 50), new Actor("Actor 3", 25), 2010));
    }

    public void makeVoters(){
        voters.add(new Admin("admin", "admin123"));
        voters.add(new LowerClass("lower", "lower123"));
        voters.add(new MiddleClass("middle", "middle"));
        voters.add(new MovieExpert("movieexpert", "movie"));
    }

    public List<Movie> getMovies() {
        return movies;
    }
    public List<Voters> getVoters() {
        return voters;
    }

}

package VotingRoom;

import People.*;

import java.util.*;

public class VotingRoom {
    private List<Movie> movies;
    public VotingRoom(){
        movies = new ArrayList<>();
        populateMovies();
    }

    public void populateMovies() {
        movies.add(new Movie("Movie 1", new Director("Director 1", 21), new Actor("Actor 1", 21), 2000));
        movies.add(new Movie("Movie 2", new Director("Director 2", 45), new Actor("Actor 2", 30), 2005));
        movies.add(new Movie("Movie 3", new Director("Director 3", 50), new Actor("Actor 3", 25), 2010));
    }

    public List<Movie> getMovies() {
        return movies;
    }

}

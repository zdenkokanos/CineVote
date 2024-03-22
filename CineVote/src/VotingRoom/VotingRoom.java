package VotingRoom;

import People.*;
import Voters.*;

import java.io.*;
import java.util.*;

public class VotingRoom implements Serializable {
    private List<Movie> movies;
    private List<Voters> voters;

    public VotingRoom() {
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

    public void makeVoters() {
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

    public void saveVotingRoom() {
        try (FileOutputStream fileOut = new FileOutputStream("voting.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {

            out.writeObject(movies);
            out.writeObject(voters);
            System.out.println("VotingRoom object has been serialized and saved.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadVotingRoom() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("voting.ser"))) {
            movies = (List<Movie>) in.readObject();
            voters = (List<Voters>) in.readObject();
            System.out.println("VotingRoom object has been deserialized and loaded.");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}

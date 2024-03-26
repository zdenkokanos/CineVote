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
        movies.add(new Movie("Inception", new Director("Christopher Nolan", 50), new Actor("Leonardo DiCaprio", 45), 2010));
        movies.add(new Movie("The Shawshank Redemption", new Director("Frank Darabont", 62), new Actor("Tim Robbins", 63), 1994));
        movies.add(new Movie("The Godfather", new Director("Francis Ford Coppola", 82), new Actor("Marlon Brando", 80), 1972));
        movies.add(new Movie("Pulp Fiction", new Director("Quentin Tarantino", 58), new Actor("John Travolta", 67), 1994));
        movies.add(new Movie("The Dark Knight", new Director("Christopher Nolan", 50), new Actor("Christian Bale", 48), 2008));
        movies.add(new Movie("Schindler's List", new Director("Steven Spielberg", 75), new Actor("Liam Neeson", 69), 1993));
        movies.add(new Movie("Fight Club", new Director("David Fincher", 59), new Actor("Brad Pitt", 58), 1999));
        movies.add(new Movie("Forrest Gump", new Director("Robert Zemeckis", 70), new Actor("Tom Hanks", 65), 1994));
        movies.add(new Movie("The Matrix", new Director("Lana Wachowski", 54), new Actor("Keanu Reeves", 57), 1999));
        movies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", new Director("Peter Jackson", 60), new Actor("Elijah Wood", 41), 2001));
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

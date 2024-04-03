package VotingRoom;

import People.*;
import Voters.*;

import java.io.*;
import java.util.*;

public class VotingRoom implements Serializable {
    private List<VotingObserver> observers = new ArrayList<>();
    private List<Movie> movies;
    private List<Voters> voters;

    private List<Movie> nominatedMovies;

    public VotingRoom() {
        movies = new ArrayList<>();
        voters = new ArrayList<>();
        nominatedMovies = new ArrayList<>(); //movies which were nominated to be accepted by admin
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
        voters.add(new Admin("admin", "admin"));
        voters.add(new LowerClass("lower", "lower"));
        voters.add(new MiddleClass("middle", "middle"));
        voters.add(new MovieExpert("movieexpert", "movie"));
        voters.add(new AnalyzeExpert("analyze", "analyze"));
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<Voters> getVoters() {
        return voters;
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }


    public void addVoter(LowerClass voter) {
        voters.add(voter);
    }

    public void suggestNominaation(Movie movie, int priority) {
        if (priority == 1)
        {
            nominatedMovies.add(0, movie); // Add movie to the beginning of the list
        } else
        {
            nominatedMovies.add(movie); // Add movie to the end of the list
        }
    }

    public List<Movie> getNominatedMovies() {
        return nominatedMovies;
    }


    public void saveVotingRoom() {
        try (FileOutputStream fileOut = new FileOutputStream("voting.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut))
        {

            out.writeObject(movies);
            out.writeObject(voters);
            out.writeObject(nominatedMovies);
            System.out.println("VotingRoom object has been serialized and saved.");

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadVotingRoom() {
        File file = new File("voting.ser");
        if (!file.exists())
        {
            System.out.println("No file found. Skipping loading.");
            return;
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file)))
        {
            movies = (List<Movie>) in.readObject();
            voters = (List<Voters>) in.readObject();
            nominatedMovies = (List<Movie>) in.readObject();
            System.out.println("VotingRoom object has been deserialized and loaded.");
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void declineSuggestion(Movie movie) {
        nominatedMovies.remove(movie);
    }

    public void acceptSuggestion(Movie movie) {
        nominatedMovies.remove(movie);
        movies.add(movie);
    }

    public int getNominationCount() {
        int count = 0;
        for (Movie movie : nominatedMovies)
        {
            count++;
        }
        return count;
    }

    public void addObserver(VotingObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(VotingObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers() {
        for (VotingObserver observer : observers)
        {
            observer.update(movies);
        }
    }

    public void restartVoting(){
        File fileToDelete = new File("voting.ser");

        // Check if the file exists
        if (fileToDelete.exists()) {
            // Attempt to delete the file
            boolean isDeleted = fileToDelete.delete();

            // Check if the file was successfully deleted
            if (isDeleted) {
                System.out.println("File deleted successfully.");
            } else {
                System.out.println("Failed to delete the file.");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }
}

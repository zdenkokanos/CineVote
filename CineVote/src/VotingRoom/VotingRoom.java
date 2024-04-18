package VotingRoom;

import CanBeVoted.*;
import Voters.*;

import java.io.*;
import java.util.*;

//poznámky:
//spravit nejaky passport pre film ktory bude drzat typ ako kategoriu a zaroven aj metodu na to aby to vypisalo pri informaciach o filme
//postacuje agregácia ale treba spraviť vlastnú
public class VotingRoom implements Serializable {
    private List<VotingObserver> observers; //návrhový vzor Observer
    private List<Movie> movies; //aggregation
    private List<Voters> voters; //aggregation
    private List<Director> directors; //aggregation
    private List<Actor> actors; //aggregation
    private List<Movie> nominatedMovies;   //aggregation

    public VotingRoom() {
        observers = new ArrayList<>();
        movies = new ArrayList<>();
        voters = new ArrayList<>();
        directors = new ArrayList<>();
        actors = new ArrayList<>();

        nominatedMovies = new ArrayList<>(); //movies which were nominated to be accepted by admin
        populateMovies();
        makeVoters();
    }

    public void populateMovies() {
        Director nolan = new Director("Christopher Nolan", 50);
        Director darabont = new Director("Frank Darabont", 62);
        Director coppola = new Director("Francis Ford Coppola", 82);
        Director tarantino = new Director("Quentin Tarantino", 58);
        Director spielberg = new Director("Steven Spielberg", 75);
        Director fincher = new Director("David Fincher", 59);
        Director zemeckis = new Director("Robert Zemeckis", 70);
        Director wachowski = new Director("Lana Wachowski", 54);
        Director jackson = new Director("Peter Jackson", 60);
        // Add other directors as needed

        directors.add(nolan);
        directors.add(darabont);
        directors.add(coppola);
        directors.add(tarantino);
        directors.add(spielberg);
        directors.add(fincher);
        directors.add(zemeckis);
        directors.add(wachowski);
        directors.add(jackson);
        // Add other directors to the directors array

        Actor dicaprio = new Actor("Leonardo DiCaprio", 45, new BankAccount("SK3742462397422289572645",
                "Tatra Banka", "TATRSKBX"));
        Actor robbins = new Actor("Tim Robbins", 63, new BankAccount("SK3646472527639475623961",
                "Prima Banka", "KOMASK2X"));
        Actor brando = new Actor("Marlon Brando", 80, new BankAccount("SK0776382686276877392518",
                "Unicredit", "BACXCZPP"));
        Actor travolta = new Actor("John Travolta", 67, new BankAccount("SK9215833235933132996394",
                "Unicredit", "BACXCZPP"));
        Actor bale = new Actor("Christian Bale", 48, new BankAccount("SK4587883786186887898145",
                "Prima Banka", "KOMASK2X"));
        Actor neeson = new Actor("Liam Neeson", 69, new BankAccount("SK2922266682516937257835",
                "Prima Banka", "KOMASK2X"));
        Actor pitt = new Actor("Brad Pitt", 58, new BankAccount("SK4472397139862495814442",
                "Tatra Banka", "TATRSKBX"));
        Actor hanks = new Actor("Tom Hanks", 65, new BankAccount("SK8833811937227371832999",
                "Tatra Banka", "TATRSKBX"));
        Actor reeves = new Actor("Keanu Reeves", 57, new BankAccount("SK5289273242194196694814",
                "Tatra Banka", "TATRSKBX"));
        Actor wood = new Actor("Elijah Wood", 41, new BankAccount("SK4882867918151751175644",
                "Tatra Banka", "TATRSKBX"));
        // Add other actors as needed

        actors.add(dicaprio);
        actors.add(robbins);
        actors.add(brando);
        actors.add(travolta);
        actors.add(bale);
        actors.add(neeson);
        actors.add(pitt);
        actors.add(hanks);
        actors.add(reeves);
        actors.add(wood);
        // Add other actors to the actors array

        movies.add(new Movie("Inception", nolan, dicaprio, 2010));
        movies.add(new Movie("The Shawshank Redemption", darabont, robbins, 1994));
        movies.add(new Movie("The Godfather", coppola, brando, 1972));
        movies.add(new Movie("Pulp Fiction", tarantino, travolta, 1994));
        movies.add(new Movie("The Dark Knight", nolan, bale, 2008));
        movies.add(new Movie("Schindler's List", spielberg, neeson, 1993));
        movies.add(new Movie("Fight Club", fincher, pitt, 1999));
        movies.add(new Movie("Forrest Gump", zemeckis, hanks, 1994));
        movies.add(new Movie("The Matrix", wachowski, reeves, 1999));
        movies.add(new Movie("The Lord of the Rings: The Fellowship of the Ring", jackson, wood, 2001));
    }

    public void makeVoters() {
        voters.add(new Admin("admin", "admin", new BankAccount(100)));
        voters.add(new LowerClass("lower", "lower", new BankAccount(100)));
        voters.add(new MiddleClass("middle", "middle", new BankAccount(100)));
        voters.add(new MovieExpert("movieexpert", "movieexpert", new BankAccount(100)));
        voters.add(new AnalyzeExpert("analyzeexpert", "analyzeexpert", new BankAccount(100)));
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
            out.writeObject(actors);
            out.writeObject(directors);
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
            actors = (List<Actor>) in.readObject();
            directors = (List<Director>) in.readObject();
            System.out.println("VotingRoom object has been deserialized and loaded.");
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public List<Director> getDirectors() {
        return directors;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void addDirector(Director director) {
        directors.add(director);
    }

    public void addActor(Actor actor) {
        actors.add(actor);
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

    public void restartVoting() {
        File fileToDelete = new File("voting.ser");

        // Check if the file exists
        if (fileToDelete.exists())
        {
            // Attempt to delete the file
            boolean isDeleted = fileToDelete.delete();

            // Check if the file was successfully deleted
            if (isDeleted)
            {
                System.out.println("Voting was restarted successfuly! :)");
            } else
            {
                System.out.println("Failed to delete the file.");
            }
        } else
        {
            System.out.println("File does not exist.");
        }
    }
}

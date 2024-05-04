package VotingRoom;

import CanBeVoted.*;
import Voters.*;

import java.io.*;
import java.util.*;

/**
 * Trieda VotingRoom je centrálne miesto pre správu hlasovacieho procesu.
 * Zodpovedá za udržiavanie zoznamov filmov, režisérov, hercov, voličov a za riadenie hlasovacích aktivít.
 */
public class VotingRoom implements Serializable {
    private List<VotingObserver> observers; // Sledovatelia zmien v hlasovacích dátach
    private List<Movie> movies; // Zoznam filmov dostupných na hlasovanie
    private List<People> voters; // Zoznam voličov
    private List<Director> directors; // Zoznam režisérov
    private List<Actor> actors; // Zoznam hercov
    private List<Movie> nominatedMovies; // Zoznam filmov čakajúcich na schválenie
    private boolean on_going; //Hovorí o priebehu hlasovania

    /**
     * Konštruktor pre VotingRoom inicializuje zoznamy a začína hlasovací proces.
     */
    public VotingRoom() {
        observers = new ArrayList<>();
        movies = new ArrayList<>();
        voters = new ArrayList<>();
        directors = new ArrayList<>();
        actors = new ArrayList<>();
        this.on_going = true;

        nominatedMovies = new ArrayList<>(); // Filmy čakajúce na schválenie adminom
        populateMovies();
        makeVoters();
    }

    /**
     * Vytvára vzorový zoznam filmov, režisérov a hercov pre hlasovanie.
     */
    public void populateMovies() {
        Director nolan = new Director("Christopher Nolan", 50, new BankAccount("SK7424335535497194675425",
                "Prima Banka", "KOMASK2X"));
        Director darabont = new Director("Frank Darabont", 62, new BankAccount("SK9891431716787324639574",
                "Tatra Banka", "TATRSKBX"));
        Director coppola = new Director("Francis Ford Coppola", 82, new BankAccount("SK9236312673445174713253",
                "Unicredit", "BACXCZPP"));
        Director tarantino = new Director("Quentin Tarantino", 58, new BankAccount("SK7876432521192919844289",
                "Tatra Banka", "TATRSKBX"));
        Director spielberg = new Director("Steven Spielberg", 75, new BankAccount("SK5229572261864387974517",
                "Unicredit", "BACXCZPP"));
        Director fincher = new Director("David Fincher", 59, new BankAccount("SK7599457568469925325714",
                "Tatra Banka", "TATRSKBX"));
        Director zemeckis = new Director("Robert Zemeckis", 70, new BankAccount("SK6451941995262469936322",
                "Tatra Banka", "TATRSKBX"));
        Director wachowski = new Director("Lana Wachowski", 54, new BankAccount("SK6866695673559324876362",
                "Unicredit", "BACXCZPP"));
        Director jackson = new Director("Peter Jackson", 60, new BankAccount("SK3056628357872576753787",
                "Prima Banka", "KOMASK2X"));
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
        voters.add(nolan);
        voters.add(darabont);
        voters.add(coppola);
        voters.add(tarantino);
        voters.add(spielberg);
        voters.add(fincher);
        voters.add(zemeckis);
        voters.add(wachowski);
        voters.add(jackson);
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
        voters.add(dicaprio);
        voters.add(robbins);
        voters.add(brando);
        voters.add(travolta);
        voters.add(bale);
        voters.add(neeson);
        voters.add(pitt);
        voters.add(hanks);
        voters.add(reeves);
        voters.add(wood);
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
        movies.add(new Movie("The Lord of the Rings", jackson, wood, 2001));
    }

    /**
     * Inicializuje zoznam voličov.
     */
    public void makeVoters() {
        voters.add(new Admin("admin", "admin", new BankAccount(100)));
        voters.add(new LowerClass("lower", "lower", new BankAccount(100)));
        voters.add(new MiddleClass("middle", "middle", new BankAccount(100)));
        voters.add(new MovieExpert("movieexpert", "movieexpert", new BankAccount(100)));
        voters.add(new AnalyzeExpert("analyzeexpert", "analyzeexpert", new BankAccount(100)));
    }

    /**
     * Vráti zoznam všetkých filmov.
     *
     * @return Zoznam filmov
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     * Vráti zoznam všetkých voličov.
     *
     * @return Zoznam voličov
     */
    public List<People> getVoters() {
        return voters;
    }

    /**
     * Pridá film do zoznamu filmov.
     *
     * @param movie Film na pridanie
     */
    public void addMovie(Movie movie) {
        movies.add(movie);
    }


    public void addVoter(LowerClass voter) {
        voters.add(voter);
    }

    /**
     * Navrhne nomináciu filmu s určitou prioritou.
     *
     * @param movie    Film na nomináciu
     * @param priority Priorita nominácie
     */
    public void suggestNomination(Movie movie, int priority) {
        if (priority == 1)
        {
            nominatedMovies.add(0, movie); // Pridať film na začiatok zoznamu
        } else
        {
            nominatedMovies.add(movie); // Pridať film na koniec zoznamu
        }
    }

    /**
     * Vráti zoznam nominovaných filmov.
     *
     * @return Zoznam nominovaných filmov
     */
    public List<Movie> getNominatedMovies() {
        return nominatedMovies;
    }

    /**
     * Uloží stav VotingRoom do súboru pomocou sérializácie.
     */
    public void saveVotingRoom() {
        try (FileOutputStream fileOut = new FileOutputStream("voting.ser");
             ObjectOutputStream out = new ObjectOutputStream(fileOut))
        {

            out.writeObject(movies);
            out.writeObject(voters);
            out.writeObject(nominatedMovies);
            out.writeObject(actors);
            out.writeObject(directors);
            out.writeBoolean(on_going);
            System.out.println("VotingRoom object has been serialized and saved.");

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Načíta stav VotingRoom zo súboru pomocou deserializácie.
     */
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
            voters = (List<People>) in.readObject();
            nominatedMovies = (List<Movie>) in.readObject();
            actors = (List<Actor>) in.readObject();
            directors = (List<Director>) in.readObject();
            this.on_going = in.readBoolean();
            System.out.println("VotingRoom object has been deserialized and loaded.");
        } catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Vráti aktuálny stav hlasovania.
     *
     * @return true ak hlasovanie prebieha, inak false
     */
    public boolean getStatus() {
        return on_going;
    }

    /**
     * Nastaví stav hlasovania.
     *
     * @param status Nový stav hlasovania
     */
    public void setStatus(boolean status) {
        this.on_going = status;
    }

    /**
     * Vráti zoznam režisérov.
     *
     * @return Zoznam režisérov
     */
    public List<Director> getDirectors() {
        return directors;
    }

    /**
     * Vráti zoznam hercov.
     *
     * @return Zoznam hercov
     */
    public List<Actor> getActors() {
        return actors;
    }

    /**
     * Pridá režiséra do zoznamu režisérov.
     *
     * @param director Režisér na pridanie
     */
    public void addDirector(Director director) {
        directors.add(director);
    }

    /**
     * Pridá herca do zoznamu hercov.
     *
     * @param actor Herec na pridanie
     */
    public void addActor(Actor actor) {
        actors.add(actor);
    }

    /**
     * Odstráni návrh filmu z nominovaných filmov.
     *
     * @param movie Film na odstránenie
     */
    public void declineSuggestion(Movie movie) {
        nominatedMovies.remove(movie);
    }

    /**
     * Akceptuje návrh filmu a pridá ho do zoznamu filmov.
     *
     * @param movie Film na pridanie
     */
    public void acceptSuggestion(Movie movie) {
        nominatedMovies.remove(movie);
        movies.add(movie);
    }

    /**
     * Vráti počet nominovaných filmov.
     *
     * @return Počet nominovaných filmov
     */
    public int getNominationCount() {
        return nominatedMovies.size();
    }

    /**
     * Pridá pozorovateľa do zoznamu.
     *
     * @param observer Pozorovateľ na pridanie
     */
    public void addObserver(VotingObserver observer) {
        observers.add(observer);
    }

    /**
     * Vráti zoradené polia a výhercov hlasovania.
     *
     * @return zoradené polia a výhercov hlasovania
     */
    public Winners getWinners() {
        return new Winners(movies, actors, directors);
    }

    /**
     * Odstráni pozorovateľa zo zoznamu.
     *
     * @param observer Pozorovateľ na odstránenie
     */
    public void removeObserver(VotingObserver observer) {
        observers.remove(observer);
    }

    /**
     * Upozorní všetkých pozorovateľov na zmeny.
     */
    public void notifyObservers() {
        for (VotingObserver observer : observers)
        {
            observer.update(movies);
        }
    }

    /**
     * Reštartuje hlasovanie tým, že odstráni súbor so sérializovanými dátami, ak existuje.
     */
    public void restartVoting() {
        File fileToDelete = new File("voting.ser");

        if (fileToDelete.exists())
        {
            boolean isDeleted = fileToDelete.delete();

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

    /**
     * Vnútorná trieda Winners zodpovedá za správu a zisťovanie víťazov v rôznych kategóriách.
     */
    public class Winners {
        private List<Movie> movies;
        private List<Actor> actors;
        private List<Director> directors;

        public Winners(List<Movie> movies, List<Actor> actors, List<Director> directors) {
            this.movies = movies;
            this.actors = actors;
            this.directors = directors;
        }

        /**
         * Vráti zoznam filmov zoradený podľa počtu hlasov.
         *
         * @return Zoznam zoradených filmov
         */
        public List<Movie> getWinnersMovies() {
            List<Movie> sortedMovies = new ArrayList<>(movies);
            Collections.sort(sortedMovies, (a, b) -> Integer.compare(b.getVotes(), a.getVotes()));
            return sortedMovies;
        }

        /**
         * Vráti zoznam hercov zoradený podľa počtu hlasov.
         *
         * @return Zoznam zoradených hercov
         */
        public List<Actor> getWinnersActors() {
            List<Actor> sortedActors = new ArrayList<>(actors);
            Collections.sort(sortedActors, (a, b) -> Integer.compare(b.getVotes(), a.getVotes()));
            return sortedActors;
        }

        /**
         * Vráti zoznam režisérov zoradený podľa počtu hlasov.
         *
         * @return Zoznam zoradených režisérov
         */
        public List<Director> getWinnersDirectors() {
            List<Director> sortedDirectors = new ArrayList<>(directors);
            Collections.sort(sortedDirectors, (a, b) -> Integer.compare(b.getVotes(), a.getVotes()));
            return sortedDirectors;
        }
    }
}

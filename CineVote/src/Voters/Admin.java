package Voters;

import CanBeVoted.Actor;
import CanBeVoted.BankAccount;
import CanBeVoted.Director;
import CanBeVoted.Movie;
import VotingRoom.*;

/**
 * Trieda Admin predstavuje administrátora hlasovacej miestnosti, ktorý má rozšírené
 * oprávnenia na správu a manipuláciu s objektmi a procesmi vo vnútri hlasovacej miestnosti.
 * Administrátor môže priamo nominovať filmy, režisérov a hercov do hlasovania a tiež prijímať
 * nominácie a vidieť priebežné výsledky.
 */
public class Admin extends Voters {

    /**
     * Konštruktor pre vytvorenie admina s používateľským menom a heslom.
     *
     * @param username Používateľské meno admina
     * @param password Heslo admina
     */
    public Admin(String username, String password) {
        super(username, password);
    }

    /**
     * Konštruktor pre vytvorenie admina s používateľským menom, heslom a bankovým účtom.
     *
     * @param username Používateľské meno admina
     * @param password Heslo admina
     * @param bankAccount Bankový účet admina
     */
    public Admin(String username, String password, BankAccount bankAccount) {
        super(username, password, bankAccount);
    }

    /**
     * Nominuje film do hlasovacej miestnosti priamo, bez potreby schválenia.
     * Vyhľadáva existujúce údaje o režisérovi a hercovi alebo vytvára nové inštancie.
     *
     * @param movieName Názov filmu
     * @param directorName Meno režiséra
     * @param directorAge Vek režiséra
     * @param actorName Meno herca
     * @param actorAge Vek herca
     * @param makeYear Rok vytvorenia filmu
     * @param votingRoom Hlasovacia miestnosť, do ktorej sa film nominuje
     */
    public void nominate(String movieName, String directorName, int directorAge, String actorName, int actorAge, int makeYear, VotingRoom votingRoom) {
        boolean foundDirector = false;
        boolean foundActor = false;
        Director thisDirector = null;
        Actor thisActor = null;
        for (Director director : votingRoom.getDirectors()) {
            if (director.getName().equals(directorName)) {
                thisDirector = director;
                foundDirector = true;
            }
        }
        for (Actor actor : votingRoom.getActors()) {
            if (actor.getName().equals(actorName)) {
                thisActor = actor;
                foundActor = true;
            }
        }
        if (!foundDirector) {
            thisDirector = new Director(directorName, directorAge);
            votingRoom.addDirector(thisDirector);
        }
        if (!foundActor) {
            thisActor = new Actor(actorName, actorAge);
            votingRoom.addActor(thisActor);
        }
        Movie movie = new Movie(movieName, thisDirector, thisActor, makeYear);
        votingRoom.addMovie(movie);
    }

    /**
     * Prijíma nomináciu filmu do hlasovacej miestnosti.
     *
     * @param votingRoom Hlasovacia miestnosť, do ktorej sa film pridáva
     * @param movie Film, ktorý sa prijíma do nominácie
     */
    public void accept_nomination(VotingRoom votingRoom, Movie movie) {
        votingRoom.addMovie(movie);
    }

}

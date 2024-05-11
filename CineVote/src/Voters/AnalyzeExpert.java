package Voters;

import CanBeVoted.Actor;
import CanBeVoted.BankAccount;
import CanBeVoted.Director;
import VotingRoom.VotingRoom;
import CanBeVoted.Movie;

/**
 * Trieda AnalyzeExpert reprezentuje experta, ktorý sa špecializuje na analýzu a nomináciu filmov.
 * Tento expert má schopnosť navrhovať nominácie s vyššou prioritou, čo znamená, že jeho návrhy
 * sú zobrazené na vrchu zoznamu.
 */
public class AnalyzeExpert extends MiddleClass {

    /**
     * Konštruktor pre vytvorenie experta na analýzu s používateľským menom a heslom.
     *
     * @param username Používateľské meno experta
     * @param password Heslo experta
     */
    public AnalyzeExpert(String username, String password) {
        super(username, password);
    }

    /**
     * Konštruktor pre vytvorenie experta na analýzu s používateľským menom, heslom a bankovým účtom.
     *
     * @param username Používateľské meno experta
     * @param password Heslo experta
     * @param bankAccount Bankový účet experta
     */
    public AnalyzeExpert(String username, String password, BankAccount bankAccount) {
        super(username, password, bankAccount);
    }

    /**
     * Navrhuje nomináciu filmu do hlasovacej miestnosti s vyššou prioritou.
     * Tento metodický prístup zabezpečuje, že návrhy tohto experta sú zobrazené na vrchu zoznamu.
     * Vyhľadáva existujúce údaje o režisérovi a hercovi alebo vytvára nové inštancie.
     *
     * @param movieName Názov filmu
     * @param directorName Meno režiséra
     * @param directorAge Vek režiséra
     * @param actorName Meno herca
     * @param actorAge Vek herca
     * @param makeYear Rok vytvorenia filmu
     * @param votingRoom Hlasovacia miestnosť, do ktorej sa film navrhuje
     */
    @Override
    public void suggest_nomination(String movieName, String directorName, int directorAge, String actorName, int actorAge, int makeYear, VotingRoom votingRoom) {
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
        }
        if (!foundActor) {
            thisActor = new Actor(actorName, actorAge);
        }
        Movie movie = new Movie(movieName, thisDirector, thisActor, makeYear);
        votingRoom.suggestNomination(movie, 1);
    }
}

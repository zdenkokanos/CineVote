package Voters;

import CanBeVoted.*;
import VotingRoom.*;

/**
 * Trieda MiddleClass reprezentuje strednú vrstvu voličov, ktorí majú právomoci navrhovať nominácie filmov
 * do hlasovacej miestnosti. Tieto nominácie musia byť následne schválené administrátorom predtým,
 * ako sa zúčastnia na hlasovaní.
 */
public class MiddleClass extends Voters {

    /**
     * Konštruktor pre vytvorenie člena strednej triedy s používateľským menom a heslom.
     *
     * @param username Používateľské meno člena
     * @param password Heslo člena
     */
    public MiddleClass(String username, String password) {
        super(username, password);
    }

    /**
     * Konštruktor pre vytvorenie člena strednej triedy s používateľským menom, heslom a bankovým účtom.
     *
     * @param username Používateľské meno člena
     * @param password Heslo člena
     * @param bankAccount Bankový účet člena
     */
    public MiddleClass(String username, String password, BankAccount bankAccount) {
        super(username, password, bankAccount);
    }

    /**
     * Navrhuje nomináciu filmu pre schválenie administrátorom.
     * Tento proces zahŕňa vyhľadávanie existujúcich údajov o režisérovi a hercovi, alebo vytvorenie nových inštancií,
     * a následné nominovanie filmu do hlasovacej miestnosti.
     *
     * @param movieName Názov filmu
     * @param directorName Meno režiséra
     * @param directorAge Vek režiséra
     * @param actorName Meno herca
     * @param actorAge Vek herca
     * @param makeYear Rok vytvorenia filmu
     * @param votingRoom Hlasovacia miestnosť, do ktorej sa film navrhuje
     */
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
            votingRoom.addDirector(thisDirector);
        }
        if (!foundActor) {
            thisActor = new Actor(actorName, actorAge);
            votingRoom.addActor(thisActor);
        }
        Movie movie = new Movie(movieName, thisDirector, thisActor, makeYear);
        votingRoom.suggestNomination(movie, 0);
    }
}

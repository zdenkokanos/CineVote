package Voters;

import CanBeVoted.*;
import VotingRoom.*;

public class MiddleClass extends Voters {

    public MiddleClass(String username, String password) {
        super(username, password);
    }
    public MiddleClass(String username, String password, BankAccount bankAccount) {
        super(username, password, bankAccount);
    }

    //this method suggests movie for admin to accept it
    public void suggest_nomination(String movieName, String directorName, int directorAge, String actorName, int actorAge, int makeYear, VotingRoom votingRoom) {
        boolean foundDirector = false;
        boolean foundActor = false;
        Director thisDirector = null;
        Actor thisActor = null;
        for (Director director : votingRoom.getDirectors())//checks if that director isn't already created
        {
            if (director.getName().equals(directorName))
            {
                thisDirector = director;
                foundDirector = true;
            }
        }
        for (Actor actor : votingRoom.getActors())//checks if that actor isn't already created
        {
            if (actor.getName().equals(directorName))
            {
                thisActor = actor;
                foundActor = true;
            }
        }
        if (!foundDirector)
        {
            thisDirector = new Director(directorName, directorAge);
            votingRoom.addDirector(thisDirector);
        }
        if (!foundActor)
        {
            thisActor = new Actor(actorName, actorAge);
            votingRoom.addActor(thisActor);
        }
        Movie movie = new Movie(movieName, thisDirector, thisActor, makeYear);
        votingRoom.suggestNominaation(movie, 0);
    }
}

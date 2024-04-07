package Voters;

import CanBeVoted.Actor;
import CanBeVoted.Director;
import VotingRoom.VotingRoom;
import CanBeVoted.Movie;

public class AnalyzeExpert extends MiddleClass {

    public AnalyzeExpert(String username, String password) {
        super(username, password);
    }

    //this method overloads the suggest_nomination method of middle class, it has higher priority which means it is displayed on top of the list
    @Override
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
        votingRoom.suggestNominaation(movie, 1);
    }
}

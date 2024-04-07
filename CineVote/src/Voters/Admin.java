package Voters;

import CanBeVoted.Actor;
import CanBeVoted.Director;
import CanBeVoted.Movie;
import VotingRoom.*;


public class Admin extends Voters {

    public Admin(String username, String password) {
        super(username, password);
    }

    //this method directly adds the movie to the voting room, no need to accept
    public void nominate(String movieName, String directorName, int directorAge, String actorName, int actorAge, int makeYear, VotingRoom votingRoom) {
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
        if (!foundDirector) //if the director isn't already created it creates a new instance of director
        {
            thisDirector = new Director(directorName, directorAge);
            votingRoom.addDirector(thisDirector);
        }
        if (!foundActor) //this proceeds the same but for the actor
        {
            thisActor = new Actor(actorName, actorAge);
            votingRoom.addActor(thisActor);
        }
        Movie movie = new Movie(movieName, thisDirector, thisActor, makeYear);
        votingRoom.addMovie(movie);

    }

    public void accept_nomination(VotingRoom votingRoom, Movie movie) {
        votingRoom.addMovie(movie);
    }

    public void see_results() {

    }
}

package Voters;

import People.Actor;
import People.Director;
import VotingRoom.*;


public class Admin extends Voters {

    public Admin(String username, String password){
        super(username, password);
    }

    public void nominate(String movieName, String directorName, int directorAge, String actorName, int actorAge, int makeYear, VotingRoom votingRoom) {
        Movie movie = new Movie(movieName, new Director(directorName,directorAge), new Actor(actorName, actorAge), makeYear);
        votingRoom.addMovie(movie);
    }

    public void accept_nomination(VotingRoom votingRoom, Movie movie) {
        votingRoom.addMovie(movie);
    }

    public void see_results() {

    }
}

package Voters;

import People.Actor;
import People.Director;
import VotingRoom.VotingRoom;
import VotingRoom.Movie;

public class AnalyzeExpert extends MiddleClass{

    public AnalyzeExpert(String username, String password){
        super(username, password);
    }
    public void suggest_nomination(String movieName, String directorName, int directorAge, String actorName, int actorAge, int makeYear, VotingRoom votingRoom) {
        Movie movie = new Movie(movieName, new Director(directorName,directorAge), new Actor(actorName, actorAge), makeYear);
        votingRoom.suggestNominaation(movie, 1);
    }
}

package VotingRoom;

import People.Actor;
import People.Director;

public class Movie {
    String title;
    Director director;
    Actor mainActor;
    int year;

    public Movie(String title, Director director, int year) {
        this.title = title;
        this.director = director;
        this.year = year;
    }
}

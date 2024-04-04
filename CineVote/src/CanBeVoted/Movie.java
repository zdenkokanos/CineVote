package CanBeVoted;

import java.io.Serializable;

public class Movie extends CanBeVoted implements Serializable {
    private String title;
    private Director director;
    private Actor mainActor;
    private int year;

    public Movie(String title, Director director, Actor mainActor, int year) {
        this.title = title;
        this.director = director;
        this.mainActor = mainActor;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getDirectorName() {
        return director.getName();
    }

    public String getMainActorName() {
        return mainActor.getName();
    }

    public int getReleaseYear() {
        return year;
    }
}

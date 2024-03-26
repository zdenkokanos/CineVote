package VotingRoom;

import People.*;
import People.Director;

import java.io.*;


public class Movie implements Serializable
{
    private String title;
    private Director director;
    private Actor mainActor;
    private int year;

    private int votes;

    public Movie(String title, Director director, Actor mainActor, int year)
    {
        this.title = title;
        this.director = director;
        this.mainActor = mainActor;
        this.year = year;
    }

    public String getTitle()
    {
        return title;
    }

    public void addvote(int weight)
    {
        this.votes += weight;
    }

    public int getVotes()
    {
        return votes;
    }
}



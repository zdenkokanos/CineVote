package Voters;
import CanBeVoted.*;
import VotingRoom.*;

import java.io.*;

public abstract class Voters implements VotingProcess, Serializable{
    private String username;
    private String password;
    private boolean voted;

    public Voters(String username, String password){
        this.username = username;
        this.password = password;
    }
    public void vote(CanBeVoted canBeVoted){
        canBeVoted.addVote(1);
        voted();
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }

    public boolean getVoted(){
        return voted;
    }
    public void voted(){
        this.voted = true;
    }
}

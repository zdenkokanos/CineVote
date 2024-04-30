package CanBeVoted;

import java.io.Serializable;

public abstract class CanBeVoted implements Serializable {
    private int votes;

    public CanBeVoted() {
        this.votes = 0;
    }

    public void addVote(int weight) {
        this.votes += weight;
    }

    public int getVotes() {
        return votes;
    }

}
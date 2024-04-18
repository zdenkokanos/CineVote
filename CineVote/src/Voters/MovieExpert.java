package Voters;

import CanBeVoted.CanBeVoted;
import CanBeVoted.Movie;
import CanBeVoted.BankAccount;

public class MovieExpert extends MiddleClass {

    //this class has a vote with weight of 2
    public MovieExpert(String username, String password) {
        super(username, password);
    }

    public MovieExpert(String username, String password, BankAccount bankAccount) {
        super(username, password, bankAccount);
    }

    @Override
    public void vote(CanBeVoted canBeVoted) {
        canBeVoted.addVote(2);
        voted();
    }

}

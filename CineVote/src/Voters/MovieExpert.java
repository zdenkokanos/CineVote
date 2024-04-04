package Voters;
import CanBeVoted.CanBeVoted;
import CanBeVoted.Movie;

public class MovieExpert extends MiddleClass{

    public MovieExpert(String username, String password){
        super(username, password);
    }
    @Override
    public void vote(CanBeVoted canBeVoted){
        canBeVoted.addVote(2);
        voted();
    }

}

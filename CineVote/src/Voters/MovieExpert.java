package Voters;
import VotingRoom.*;
public class MovieExpert extends MiddleClass{

    public MovieExpert(String username, String password){
        super(username, password);
    }
    @Override
    public void vote(Movie movie){
        movie.addvote(2);
    }

}

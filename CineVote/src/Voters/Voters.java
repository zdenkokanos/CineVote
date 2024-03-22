package Voters;
import VotingRoom.*;

public class Voters{
    private String username;
    private String password;
    private boolean voted;


    public Voters(String username, String password){
        this.username = username;
        this.password = password;
    }
    public void vote(Movie movie){
        System.out.println("I voted!");
        movie.addvote(1);
    }

    public String getUsername(){
        return username;
    }

    public String getPassword(){
        return password;
    }
}

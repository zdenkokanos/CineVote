package GUI;

import Voters.Voters;
import VotingRoom.Movie;
import VotingRoom.VotingRoom;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;


public class VotingScene extends Scene {
    private MessageScene thankYouScene = new MessageScene("Thank you for your time!");
    private Button vote = new Button("Vote");
    private String css = this.getClass().getResource("main.css").toExternalForm();

    public VotingScene(VotingRoom votingRoom, Voters voter, Stage stage){
        super(new FlowPane(), 500, 500, Color.GRAY);
        FlowPane pane = (FlowPane) this.getRoot();
        ToggleGroup toggleGroup = new ToggleGroup();

        // Display movies from VotingRoom
        List<Movie> movies = votingRoom.getMovies();
        int i = 0;
        for(Movie movie: movies){
            System.out.println(i+": "+ movie.getTitle()+"/////"+ movie.getVotes());
            i++;
        }
        for (Movie movie : movies) {
            RadioButton radioButton = new RadioButton(movie.getTitle());
            radioButton.setToggleGroup(toggleGroup);
            VBox movieBox = new VBox(vote, radioButton);
            pane.getChildren().add(movieBox);
            radioButton.setUserData(movie);
        }

        vote.setOnAction(e -> {
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                Movie selectedMovie = (Movie) selectedRadioButton.getUserData();
                voter.vote(selectedMovie);
                System.out.println("Selected movie: " + selectedMovie.getTitle()+"\n***********************");
                stage.setScene(thankYouScene);
                votingRoom.saveVotingRoom();
                int j = 0;
                for(Movie movie: movies){
                    System.out.println(j+": "+ movie.getTitle()+ "/////"+ movie.getVotes());
                    j++;
                }
            } else {
                System.out.println("No movie selected.");
            }
        });
    }
}

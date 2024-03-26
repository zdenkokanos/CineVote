package GUI;

import Voters.Voters;
import VotingRoom.Movie;
import VotingRoom.VotingRoom;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;
import javafx.scene.control.ScrollPane;


public class VotingScene extends Scene {
    private MessageScene thankYouScene = new MessageScene("Thank you for your time!");
    private Button vote = new Button("Vote");
    private String css = this.getClass().getResource("votingScene.css").toExternalForm();

    public VotingScene(VotingRoom votingRoom, Voters voter, Stage stage){
        super(new ScrollPane(), 500, 500, Color.LIGHTGRAY);
        ScrollPane scrollPane = (ScrollPane) this.getRoot();
        VBox pane = new VBox();
        scrollPane.setContent(pane);
        ToggleGroup toggleGroup = new ToggleGroup();
        getStylesheets().add(css);
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
            VBox movieBox = new VBox(radioButton);
            movieBox.setAlignment(Pos.CENTER);
            movieBox.setPadding(new Insets(10, 10, 10, 10));
            pane.getChildren().add(movieBox);
            radioButton.setUserData(movie);
        }

        VBox votebutton = new VBox(vote);
        votebutton.setAlignment(Pos.CENTER);
        votebutton.setPadding(new Insets(50, 10, 10, 10));
        pane.getChildren().add(votebutton);

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

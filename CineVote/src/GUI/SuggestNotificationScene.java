package GUI;

import CanBeVoted.Movie;
import VotingRoom.VotingRoom;
import Voters.Admin;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SuggestNotificationScene extends Scene {
    private Button exit = new Button("Exit");
    public SuggestNotificationScene(VotingRoom votingRoom, Stage stage, Admin admin) {
        //sets the pane and the main elements
        super(new VBox(), 500, 600);

        // Create VBox to hold multiple boxes with text
        VBox container = (VBox) this.getRoot();
        container.setPadding(new Insets(10)); // Add padding to the container

        // Create and add boxes with text and buttons
        try
        {
            for (Movie suggestedMovie : votingRoom.getNominatedMovies())
            {
                container.getChildren().add(createMovieBox(suggestedMovie, votingRoom, stage, container));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        //sets buttons on action
        exit.setOnAction(e->{
            AdminScene adminScene = new AdminScene(stage, votingRoom, admin);
            stage.setScene(adminScene);
        });
        container.getChildren().add(exit);
    }


    private VBox createMovieBox(Movie movie, VotingRoom votingRoom, Stage stage, VBox parentContainer) {
        Label label = new Label(movie.getTitle());
        Button button1 = new Button("Accept");
        Button button2 = new Button("Decline");
        VBox box = new VBox(label, new HBox(button1, button2));

        //sets buttons on action
        button1.setOnAction(e -> {
            votingRoom.acceptSuggestion(movie);
            votingRoom.saveVotingRoom();
            parentContainer.getChildren().remove(box);

        });

        button2.setOnAction(e -> {
            votingRoom.declineSuggestion(movie);
            votingRoom.saveVotingRoom();
            parentContainer.getChildren().remove(box);

        });

        //adds some css styling
        box.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        box.setSpacing(5);
        box.setPadding(new Insets(5));
        return box;
    }
}

package GUI;
import VotingRoom.Movie;

import VotingRoom.VotingRoom;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.stage.*;
import java.util.*;

public class MainGUI extends Application {
    private Button logIn = new Button("Log In");
    private Button vote = new Button("Vote");
    private VotingRoom votingRoom = new VotingRoom();
    String css = this.getClass().getResource("main.css").toExternalForm();

    public void start(Stage PrimaryStage) throws Exception {
        logIn.setId("logInButton");
        PrimaryStage.setTitle("CineVote");
        Image icon = new Image("CineVote.png");
        Group root = new Group();
        Scene LogInScene = new Scene(root, 500, 500, Color.BLACK);
        LogInScene.getStylesheets().add(css);
        PrimaryStage.getIcons().add(icon);
        PrimaryStage.setResizable(false);

        // Buttons
        AnchorPane pane = new AnchorPane();

        AnchorPane.setTopAnchor(logIn, 200.0); // Distance from the top
        AnchorPane.setLeftAnchor(logIn, 200.0); // Distance from the left

        pane.getChildren().add(logIn);

        root.getChildren().add(pane); // Add pane to the root

        // Set action for the logIn button
        logIn.setOnAction(e -> switchToVotingScene(PrimaryStage));

        PrimaryStage.setScene(LogInScene);
        PrimaryStage.show(); // Shows the stage
    }

    private void switchToVotingScene(Stage stage) {
        FlowPane pane = new FlowPane();
        Scene VotingScene = new Scene(pane, 500, 500, Color.GRAY);

        // Display movies from VotingRoom
        List<Movie> movies = votingRoom.getMovies();
        for (Movie movie : movies) {
            Label movieLabel = new Label(movie.getTitle());
            Label movieVote = new Label(String.valueOf(movie.getVotes()));
            Button voteButton = new Button("Vote");
            voteButton.setOnAction(e ->{
                movie.vote();
                movieVote.setText(String.valueOf(movie.getVotes()));
            }); // Handle voting action
            VBox movieBox = new VBox(movieLabel, voteButton, movieVote);
            pane.getChildren().add(movieBox);
        }

        stage.setScene(VotingScene); // Switch to voting scene
    }



    public static void main(String[] args) {
        launch(args);
    }
}

package GUI;

import VotingRoom.*;
import Voters.Voters;
import VotingRoom.VotingRoom;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.geometry.Pos;

import java.util.*;

import javafx.geometry.Insets;

public class MainGUI extends Application {
    private Button logIn = new Button("Log In");
    private Button vote = new Button("Vote");
    private VotingRoom votingRoom = new VotingRoom();
    private TextField usernameInput = new TextField();
    private PasswordField passwordInput = new PasswordField();

    String css = this.getClass().getResource("main.css").toExternalForm();

    public void start(Stage primaryStage) throws Exception {
        logIn.setId("logInButton");
        primaryStage.setTitle("CineVote");
        Image icon = new Image("CineVote.png");
        Group root = new Group();
        Scene logInScene = new Scene(root, 500, 500, Color.BLACK);
        logInScene.getStylesheets().add(css);
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER); // Center the GridPane

        Insets insets = new Insets(100);
        gridPane.setPadding(insets);

        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameInput, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordInput, 1, 1);
        gridPane.add(logIn, 1, 2);

        root.getChildren().add(gridPane);


        logIn.setOnAction(e -> {
            for (Voters voter : votingRoom.getVoters()) {
                // Check if the entered username and password match the voter's credentials
                if (voter.getUsername().equals(usernameInput.getText()) && voter.getPassword().equals(passwordInput.getText())) {
                    switchToVotingScene(primaryStage, voter); // If match found, switch to voting scene
                    return; // Exit the loop since a match is found
                }
            }

        });

            primaryStage.setScene(logInScene);
            primaryStage.show(); // Shows the stage
        }

        private void switchToVotingScene (Stage stage, Voters voter){
            FlowPane pane = new FlowPane();
            Scene VotingScene = new Scene(pane, 500, 500, Color.GRAY);

            // Display movies from VotingRoom
            List<Movie> movies = votingRoom.getMovies();
            for (Movie movie : movies) {
                Label movieLabel = new Label(movie.getTitle());
                Label movieVote = new Label(String.valueOf(movie.getVotes()));
                Button voteButton = new Button("Vote");
                voteButton.setOnAction(e -> {
                    voter.vote(movie);
                    movieVote.setText(String.valueOf(movie.getVotes()));
                }); // Handle voting action
                VBox movieBox = new VBox(movieLabel, voteButton, movieVote);
                pane.getChildren().add(movieBox);
            }

            stage.setScene(VotingScene); // Switch to voting scene
        }


        public static void main (String[]args){
            launch(args);
        }

    }

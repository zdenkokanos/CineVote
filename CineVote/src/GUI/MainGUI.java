package GUI;

import VotingRoom.*;
import Voters.Voters;
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

    private Button load = new Button("Load");

    String css = this.getClass().getResource("main.css").toExternalForm();

    public void start(Stage primaryStage) throws Exception {
        logIn.setId("logInButton");
        primaryStage.setTitle("CineVote");
        Image icon = new Image("CineVote.png");
        Group root = new Group();
        Scene logInScene = new Scene(root, 500, 500, Color.GRAY);
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
        gridPane.add(load,3,4);

        root.getChildren().add(gridPane);


        logIn.setOnAction(e -> {
            for (Voters voter : votingRoom.getVoters()) {
                // Check if the entered username and password match the voter's credentials
                if (voter.getUsername().equals(usernameInput.getText()) && voter.getPassword().equals(passwordInput.getText())) {
                    if(voter.getVoted()==true){
                        switchToAlreadyVoted(primaryStage);
                    }
                    else{
                        switchToVotingScene(primaryStage, voter); // If match found, switch to voting scene
                    }
                    return; // Exit the loop since a match is found
                }
            }

        });

        load.setOnAction(e->votingRoom.loadVotingRoom());

        primaryStage.setScene(logInScene);
        primaryStage.show(); // Shows the stage
    }

    private void switchToVotingScene(Stage stage, Voters voter) {

        FlowPane pane = new FlowPane();
        ToggleGroup toggleGroup = new ToggleGroup();
        Scene VotingScene = new Scene(pane, 500, 500, Color.GRAY);

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
                switchToThankYou(stage);
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
        stage.setScene(VotingScene); // Switch to voting scene
    }

    public void switchToThankYou(Stage stage) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        Scene thankYouScene = new Scene(vbox, 500, 500, Color.LIGHTGRAY);
        thankYouScene.getStylesheets().add(css);
        Label thankYouLabel = new Label("Thank you for your time!");
        thankYouLabel.setId("thankYou");

        vbox.getChildren().add(thankYouLabel);

        stage.setScene(thankYouScene); // Switch to Thank You scene
    }

    public void switchToAlreadyVoted(Stage stage) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        Scene alreadyVotedScene = new Scene(vbox, 500, 500, Color.BLACK);
        alreadyVotedScene.getStylesheets().add(css);
        Label alreadyVotedLabel = new Label("You voted already!");
        alreadyVotedLabel.setId("thankYou");

        vbox.getChildren().add(alreadyVotedLabel);

        stage.setScene(alreadyVotedScene); // Switch to Thank You scene
    }

    public static void main(String[] args) {
        launch(args);
    }

}

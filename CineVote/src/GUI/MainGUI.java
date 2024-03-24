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
import javafx.geometry.Insets;

public class MainGUI extends Application {
    private Button logIn = new Button("Log In");
    private VotingRoom votingRoom = new VotingRoom();
    private TextField usernameInput = new TextField();
    private PasswordField passwordInput = new PasswordField();
    private Button load = new Button("Load");
    private MessageScene alreadyVotedScene = new MessageScene("You have already voted!");
     private String css = this.getClass().getResource("main.css").toExternalForm();

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
                    if(voter.getVoted()){
                        primaryStage.setScene(alreadyVotedScene);
                    }
                    else{
                        VotingScene votingScene = new VotingScene(votingRoom, voter, primaryStage);
                        primaryStage.setScene(votingScene); // If match found, switch to voting scene
                    }
                    return; // Exit the loop since a match is found
                }
            }

        });

        load.setOnAction(e->votingRoom.loadVotingRoom());

        primaryStage.setScene(logInScene);
        primaryStage.show(); // Shows the stage
    }

    public static void main(String[] args) {
        launch(args);
    }

}

package GUI;

import Voters.*;
import VotingRoom.VotingRoom;
import VotingRoom.People;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ExitScene extends Scene {
    private String css = this.getClass().getResource("exit.css").toExternalForm();
    private Button continueB = new Button("Continue");
    private Button logOut = new Button("Log out");
    private Button exitB = new Button("Exit");

    public ExitScene(VotingRoom votingRoom, Stage stage, People voter) {
        super(new VBox(20), 500, 600); // Added spacing between elements
        VBox vbox = (VBox) getRoot();
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(30)); // Added padding
        vbox.setStyle("-fx-background-color: linear-gradient(to bottom, #ffffff, #cccccc);"); // Background gradient
        getStylesheets().add(css);

        Label messageLabel = new Label("Succesfully updated");
        messageLabel.setId("message");
        messageLabel.setStyle("-fx-font-size: 16pt; -fx-text-fill: #333333;"); // Enhanced label styling

        // Style buttons
        continueB.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        logOut.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white;");
        exitB.setStyle("-fx-background-color: #f44336; -fx-text-fill: white;");

        // Set actions for buttons
        exitB.setOnAction(e -> {
            votingRoom.saveVotingRoom();
            stage.close();
        });
        continueB.setOnAction(e -> {
            AddMovieScene addMovieScene = new AddMovieScene(stage, votingRoom, voter, "Suggest movie nomination");
            if (voter instanceof Admin) {
                AdminScene adminScene = new AdminScene(stage, votingRoom, (Admin) voter);
                stage.setScene(adminScene);
            } else {
                stage.setScene(addMovieScene);
            }
        });
        logOut.setOnAction(e -> {
            stage.setScene(new LogInScene(stage, votingRoom));
        });

        vbox.getChildren().addAll(messageLabel, continueB, logOut, exitB);
    }
}

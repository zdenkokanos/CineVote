package GUI;

import Voters.*;
import VotingRoom.VotingRoom;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ExitScene extends Scene {
    private String css = this.getClass().getResource("main.css").toExternalForm();
    private Button continueB = new Button("Continue");
    private Button exitB = new Button("Exit");

    public ExitScene(VotingRoom votingRoom, Stage stage, Admin admin) {
        super(new VBox(), 500, 500, Color.LIGHTGRAY);
        VBox vbox = (VBox) getRoot();
        vbox.setAlignment(Pos.CENTER);
        getStylesheets().add(css);

        Label messageLabel = new Label("Succesfully updated");
        messageLabel.setId("message");

        exitB.setOnAction(e->{
            votingRoom.saveVotingRoom();
            stage.close();
        });
        continueB.setOnAction(e->{
            AdminScene adminScene = new AdminScene(stage, votingRoom, admin);
            stage.setScene(adminScene);
        });
        vbox.getChildren().addAll(messageLabel, continueB, exitB);
    }
}

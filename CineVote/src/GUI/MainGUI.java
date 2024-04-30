package GUI;

import VotingRoom.VotingRoom;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.*;

public class MainGUI extends Application {

    private VotingRoom votingRoom = new VotingRoom();

    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("CineVote");
        Image icon = new Image("CineVote.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);
        votingRoom.loadVotingRoom();
        if (votingRoom.getStatus())
        {
            LogInScene logInScene = new LogInScene(primaryStage, votingRoom);
            primaryStage.setScene(logInScene);
        } else
        {
            Results results = new Results(primaryStage ,votingRoom);
            primaryStage.setScene(results);
        }
        primaryStage.show(); // Shows the stage
    }

    public static void main(String[] args) {
        launch(args);
    }

}

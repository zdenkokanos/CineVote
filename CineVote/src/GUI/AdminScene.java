package GUI;

import Voters.Admin;
import Voters.Voters;
import VotingRoom.VotingRoom;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AdminScene extends Scene {
    private Button addMovie = new Button("Add movies");

    public AdminScene(Stage stage, VotingRoom votingRoom, Admin admin) {
        super(new VBox(), 500, 500, Color.LIGHTGRAY);
        VBox vbox = (VBox) getRoot();
        vbox.setAlignment(Pos.CENTER);

        addMovie.setOnAction(e->{
            AddMovieScene addMovieScene = new AddMovieScene(stage, votingRoom, (Voters)admin, "Nominate movies");
            stage.setScene(addMovieScene);
        });



        vbox.getChildren().addAll(addMovie);
    }
}

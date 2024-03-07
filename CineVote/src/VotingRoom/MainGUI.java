package VotingRoom;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class MainGUI extends Application {

    private Button voteButton = new Button("Vote");
    private TextArea output = new TextArea();
    private ScrollPane scroll = new ScrollPane();
    private Button erase = new Button("Erase");

    public void start(Stage mainWindow) {
        mainWindow.setTitle("CineVote");
        FlowPane pane = new FlowPane();
        pane.getChildren().add(voteButton);
        pane.getChildren().add(erase);
        pane.getChildren().add(output);

        scroll.setContent(pane);

        voteButton.setOnAction(e -> {
            output.appendText("Voted\n");
            System.out.print("Voted\n");
        });

        erase.setOnAction(e-> output.clear());

        mainWindow.setScene(new Scene(scroll, 500, 300)); // with scrollbars
        mainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
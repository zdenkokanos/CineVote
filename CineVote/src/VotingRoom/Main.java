package VotingRoom;

import People.MiddleClass;
import javafx.application.Application;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.event.*;

public class Main extends Application {

    private Button voteButton = new Button("Vote");
    private TextArea output = new TextArea();
    private ScrollPane scroll = new ScrollPane();

    public void start(Stage mainWindow) {
        mainWindow.setTitle("CineVote");
        FlowPane pane = new FlowPane();
        pane.getChildren().add(voteButton);
        pane.getChildren().add(output);

        scroll.setContent(pane);


        voteButton.setOnAction(e -> {
            output.appendText("Voted\n");
            System.out.print("Voted\n");
        });


        mainWindow.setScene(new Scene(scroll, 500, 300)); // with scrollbars
        mainWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
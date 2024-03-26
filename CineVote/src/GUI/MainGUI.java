package GUI;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.*;

public class MainGUI extends Application {

    public void start(Stage primaryStage) throws Exception {
        LogInScene logInScene= new LogInScene(primaryStage);
        primaryStage.setTitle("CineVote");
        Image icon = new Image("CineVote.png");
        primaryStage.getIcons().add(icon);
        primaryStage.setResizable(false);

        primaryStage.setScene(logInScene);
        primaryStage.show(); // Shows the stage
    }

    public static void main(String[] args) {
        launch(args);
    }

}

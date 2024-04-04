package GUI;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class MessageScene extends Scene {
    private String css = this.getClass().getResource("main.css").toExternalForm();
    private Button backToLogin = new Button("Back to login?");

    public MessageScene(String message) {
        super(new VBox(), 500, 600, Color.LIGHTGRAY);
        VBox vbox = (VBox) getRoot();
        vbox.setAlignment(Pos.CENTER);
        getStylesheets().add(css);

        Label thankYouLabel = new Label(message);
        thankYouLabel.setId("message");

        backToLogin.setOnAction(e -> {
            Stage primaryStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            primaryStage.setScene(new LogInScene(primaryStage));
        });

        vbox.getChildren().add(backToLogin);
        vbox.getChildren().add(thankYouLabel);
        EventHandler<KeyEvent> enterEventHandler = event -> {
            if (event.getCode() == KeyCode.ENTER)
            {
                backToLogin.fire();
            }
        };

        setOnKeyPressed(enterEventHandler);
    }
}

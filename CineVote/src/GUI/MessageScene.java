package GUI;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


public class MessageScene extends Scene {
    private String css = this.getClass().getResource("main.css").toExternalForm();
    public MessageScene(String message) {
        super(new VBox(), 500, 500, Color.LIGHTGRAY);
        VBox vbox = (VBox) getRoot();
        vbox.setAlignment(Pos.CENTER);
        getStylesheets().add(css);

        Label thankYouLabel = new Label(message);
        thankYouLabel.setId("message");

        vbox.getChildren().add(thankYouLabel);
    }
}

package GUI;

import Voters.Admin;
import Voters.MiddleClass;
import VotingRoom.VotingRoom;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import VotingRoom.*;
import javafx.stage.Stage;

/**
 * Trieda ChoiceScene poskytuje grafické rozhranie, kde používatelia môžu vyberať
 * medzi rôznymi možnosťami na základe ich role. Zobrazuje tlačidlá pre rôzne akcie
 * ako sú hlasovanie, správa bankového účtu, navrhovanie filmov alebo prístup k administrátorským nastaveniam.
 */
public class ChoiceScene extends Scene {
    private String css = this.getClass().getResource("choice.css").toExternalForm();

    public ChoiceScene(Stage stage, VotingRoom votingRoom, People voter) {
        super(new VBox(20), 500, 600); // VBox with spacing between children
        getStylesheets().add(css);
        VBox root = (VBox) this.getRoot();
        root.setAlignment(Pos.CENTER);
        root.getStyleClass().add("background");

        if (voter instanceof MiddleClass)
        {
            // Create the first button
            VotingScene votingScene = new VotingScene(votingRoom, voter, stage);
            Button buttonOne = createButton("votingicon.png", "Voting", votingScene, stage);

            // Create the second button
            MessageScene bankAccount = new MessageScene("Welcome at your Bank account", voter, votingRoom);
            Button buttonTwo = createButton("bankicon.png", "Bank account", bankAccount, stage);

            AddMovieScene addMovieScene = new AddMovieScene(stage, votingRoom, voter, "Suggest movie nomination");
            Button buttonTwo2 = createButton("movieicon.png", "Suggest movie", addMovieScene, stage);
            // Add buttons to the root
            if (voter.getVoted())
            {
                root.getChildren().addAll(buttonTwo, buttonTwo2);
            } else
            {
                root.getChildren().addAll(buttonOne, buttonTwo2, buttonTwo);
            }
        } else if (voter instanceof Admin)
        {
            // Create the first button
            VotingScene votingScene = new VotingScene(votingRoom, voter, stage);
            Button buttonOne = createButton("votingicon.png", "Voting", votingScene, stage);

            // Create the second button
            MessageScene bankAccount = new MessageScene("Welcome at your Bank account", voter, votingRoom);
            Button buttonTwo = createButton("bankicon.png", "Bank account", bankAccount, stage);

            AdminScene adminScene = new AdminScene(stage, votingRoom, (Admin) voter);
            Button buttonTwo2 = createButton("adminicon.png", "Admin settings", adminScene, stage);
            // Add buttons to the root
            if (voter.getVoted())
            {
                root.getChildren().addAll(buttonTwo, buttonTwo2);
            } else
            {
                root.getChildren().addAll(buttonOne, buttonTwo2, buttonTwo);
            }
        } else
        {
            // Create the first button
            VotingScene votingScene = new VotingScene(votingRoom, voter, stage);
            Button buttonOne = createButton("votingicon.png", "Voting", votingScene, stage);

            // Create the second button
            MessageScene bankAccount = new MessageScene("Welcome at your Bank account", voter, votingRoom);
            Button buttonTwo = createButton("bankicon.png", "Bank account", bankAccount, stage);
            // Add buttons to the root
            root.getChildren().addAll(buttonOne, buttonTwo);
        }


    }

    private Button createButton(String iconPath, String text, Scene scene, Stage stage) {
        Button button = new Button();

        // Create and configure the icon
        ImageView icon = new ImageView(new Image(iconPath));
        icon.setFitHeight(100); // Set the icon size
        icon.setFitWidth(100);

        // Create and configure the label
        Label label = new Label(text);
        label.setFont(new Font("Arial", 16)); // Set the font size

        // Stack the icon and label vertically
        VBox layout = new VBox(10, icon, label); // VBox with spacing
        layout.setAlignment(Pos.CENTER);

        // Set the layout as the button graphic
        button.setGraphic(layout);

        button.setOnAction(e -> {
            stage.setScene(scene);
        });
        return button;
    }
}

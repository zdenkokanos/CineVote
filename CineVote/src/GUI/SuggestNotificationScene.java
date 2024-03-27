package GUI;

import VotingRoom.Movie;
import VotingRoom.VotingRoom;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SuggestNotificationScene extends Scene {
    public SuggestNotificationScene(VotingRoom votingRoom, Stage stage) {
        super(new VBox(), 500, 500);

        // Create VBox to hold multiple boxes with text
        VBox container = (VBox) this.getRoot();
        container.setPadding(new Insets(10)); // Add padding to the container

        // Create and add boxes with text and buttons
        try
        {
            for (Movie suggestedMovie : votingRoom.getNominatedMovies())
            {
                container.getChildren().add(createMovieBox(suggestedMovie, votingRoom, stage, container));
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    // Method to create a VBox containing movie title and buttons
    private VBox createMovieBox(Movie movie, VotingRoom votingRoom, Stage stage, VBox parentContainer) {
        Label label = new Label(movie.getTitle()); // Create a label with the movie title
        Button button1 = new Button("Accept"); // Create button 1
        Button button2 = new Button("Decline"); // Create button 2
        VBox box = new VBox(label, new HBox(button1, button2)); // Container for buttons and label

        // Button actions
        button1.setOnAction(e -> {
            votingRoom.acceptSuggestion(movie);
            votingRoom.saveVotingRoom();
            parentContainer.getChildren().remove(box); // Remove the container from the parent VBox
            // You may need to update the scene here if necessary
        });

        button2.setOnAction(e -> {
            votingRoom.declineSuggestion(movie);
            votingRoom.saveVotingRoom();
            parentContainer.getChildren().remove(box); // Remove the container from the parent VBox
            // You may need to update the scene here if necessary
        });

        box.setStyle("-fx-border-color: black; -fx-border-width: 1px;"); // Add border to the box
        box.setSpacing(5); // Set spacing between label and buttons
        box.setPadding(new Insets(5)); // Add padding to the box
        return box; // Return the created VBox
    }
}

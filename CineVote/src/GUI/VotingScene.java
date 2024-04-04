package GUI;

import People.Director;
import Voters.Voters;
import VotingRoom.Movie;
import VotingRoom.VotingRoom;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TitledPane;

import javafx.scene.control.Label;

import javafx.scene.layout.HBox;

public class VotingScene extends Scene {
    private MessageScene thankYouScene = new MessageScene("Thank you for your time!");
    private Button vote = new Button("Vote");
    private String css = this.getClass().getResource("votingScene.css").toExternalForm();

    public VotingScene(VotingRoom votingRoom, Voters voter, Stage stage){
        super(new ScrollPane(), 500, 600, Color.LIGHTGRAY);
        ScrollPane scrollPane = (ScrollPane) this.getRoot();
        scrollPane.setVvalue(0.0);
        VBox pane = new VBox(); //the main pane
        scrollPane.setContent(pane);
        ToggleGroup toggleGroup = new ToggleGroup(); //all movies are under this toggle group
        getStylesheets().add(css); //adds css to voting scene
        // Display movies from VotingRoom
        List<Movie> movies = votingRoom.getMovies();

        TitledPane allMoviesPane = new TitledPane(); //this makes sections for movies directors and actors
        allMoviesPane.setText("Vote for Movies");
        VBox allMoviesContent = new VBox();

        //displays all movies in votingScene
        for (Movie movie : movies) {
            VBox movieSection = createMovieSection(movie, toggleGroup);
            allMoviesContent.getChildren().add(movieSection);
        }

        allMoviesPane.setContent(allMoviesContent);
        pane.getChildren().add(allMoviesPane);

        TitledPane anotherSectionPane = new TitledPane();
        anotherSectionPane.setText("Vote for Directors");
        anotherSectionPane.setExpanded(false);
        VBox directorSectionContent = new VBox();

        //displays all directors in VotingScene
        ToggleGroup directors = new ToggleGroup(); //toggle group for directors
        for(Director director: votingRoom.getDirectors()){
            VBox directorContent = createDirectorSection(director, toggleGroup);
            directorSectionContent.getChildren().add(directorContent);
        }

        anotherSectionPane.setContent(directorSectionContent);
        pane.getChildren().add(anotherSectionPane);

        VBox votebutton = new VBox(vote);
        votebutton.setPadding(new Insets(10, 10, 10, 10));
        pane.getChildren().add(votebutton);

        vote.setOnAction(e -> {
            RadioButton selectedRadioButton = (RadioButton) toggleGroup.getSelectedToggle();
            if (selectedRadioButton != null) {
                Movie selectedMovie = (Movie) selectedRadioButton.getUserData();
                voter.vote(selectedMovie);
                System.out.println("Selected movie: " + selectedMovie.getTitle()+"\n***********************");
                stage.setScene(thankYouScene);
                votingRoom.saveVotingRoom();
            } else {
                System.out.println("No movie selected.");
            }
        });

        EventHandler<KeyEvent> enterEventHandler = event -> {
            if (event.getCode() == KeyCode.ENTER) {
                vote.fire(); // Trigger voting button action
            }
        };

        setOnKeyPressed(enterEventHandler);
    }

    private VBox createMovieSection(Movie movie, ToggleGroup toggleGroup) {
        ToggleButton additionalInfoButton = new ToggleButton("►");
        Label directorLabel = new Label("Director: " + movie.getDirectorName());
        Label actorLabel = new Label("Main Actor/Actress: " + movie.getMainActorName());
        Label yearLabel = new Label("Release Year: " + movie.getReleaseYear());
        directorLabel.setVisible(false); // Initially hidden
        actorLabel.setVisible(false);
        yearLabel.setVisible(false);
        directorLabel.setPadding(new Insets(0, 0, 0, 50)); // Left padding of 50 for AllLabels
        actorLabel.setPadding(new Insets(0, 0, 0, 50));
        yearLabel.setPadding(new Insets(0, 0, 0, 50));
        RadioButton radioButton = new RadioButton(movie.getTitle());
        radioButton.setToggleGroup(toggleGroup);

        // Use HBox to arrange radioButton and additionalInfoButton horizontally
        HBox buttonBox = new HBox(radioButton, additionalInfoButton);
        VBox movieBox = new VBox(buttonBox);
        movieBox.setPadding(new Insets(10, 10, 10, 10));
        radioButton.setUserData(movie);

        // Add additional info labels to button box but make it invisible initially
        buttonBox.getChildren().add(directorLabel);
        buttonBox.getChildren().add(actorLabel);
        buttonBox.getChildren().add(yearLabel);

        // Toggle additional info visibility when button is clicked
        additionalInfoButton.setOnAction(e -> {
            boolean isVisible = directorLabel.isVisible(); //if one is not shown, all of them aren't
            directorLabel.setVisible(!isVisible);
            actorLabel.setVisible(!isVisible);
            yearLabel.setVisible(!isVisible);
            // Adjust layout accordingly
            if (isVisible) {
                additionalInfoButton.setText("►");
                movieBox.getChildren().remove(directorLabel);
                movieBox.getChildren().remove(actorLabel);
                movieBox.getChildren().remove(yearLabel);
            } else {
                additionalInfoButton.setText("▼");
                movieBox.getChildren().add(directorLabel);
                movieBox.getChildren().add(actorLabel);
                movieBox.getChildren().add(yearLabel);
            }
        });

        return movieBox;
    }

    private VBox createDirectorSection(Director director, ToggleGroup toggleGroup){
        VBox directorBox = new VBox();
        RadioButton radioButton = new RadioButton(director.getName());
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setUserData(director); // Set director as user data
        directorBox.getChildren().add(radioButton);
        return directorBox;
    }
}



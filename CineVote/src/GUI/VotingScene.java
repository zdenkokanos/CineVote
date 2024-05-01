package GUI;

import CanBeVoted.Actor;
import CanBeVoted.Director;
import Voters.Voters;
import VotingRoom.People;
import CanBeVoted.Movie;
import VotingRoom.VotingRoom;
import javafx.animation.PauseTransition;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.TitledPane;

import javafx.scene.control.Label;

import javafx.scene.layout.HBox;
import javafx.util.Duration;

/**
 * Trieda VotingScene poskytuje grafické rozhranie pre hlasovanie. Umožňuje voličom hlasovať
 * za filmy, režisérov a hercov. Scéna obsahuje rozbaľovacie panely pre každú kategóriu
 * a radiobuttony pre výber možností hlasovania.
 */
public class VotingScene extends Scene {
    private Button vote = new Button("Vote");
    private String css = this.getClass().getResource("votingScene.css").toExternalForm();

    /**
     * Konštruktor triedy VotingScene inicializuje a nastavuje komponenty potrebné pre hlasovanie.
     * Tento konštruktor vytvára užívateľské rozhranie obsahujúce radiobuttony pre každú kategóriu
     * a spracováva logiku pre odoslanie hlasov.
     *
     * Scéna je vybavená scroll panelom, čo umožňuje voličovi pohodlne pristupovať k veľkému
     * počtu hlasovacích možností bez potreby meniť veľkosť okna.
     *
     * @param votingRoom objekt hlasovacej miestnosti, kde sú uložené všetky filmy, režiséri a herci.
     * @param voter objekt reprezentujúci voliča, ktorý práve hlasuje.
     * @param stage hlavné okno, na ktorom sa scéna zobrazuje.
     */
    public VotingScene(VotingRoom votingRoom, People voter, Stage stage) {
        //sets the pane and the main elements
        super(new ScrollPane(), 500, 600, Color.LIGHTGRAY);
        ScrollPane scrollPane = (ScrollPane) this.getRoot();
        scrollPane.setVvalue(0.0);
        VBox pane = new VBox(); //the main pane
        scrollPane.setContent(pane);

        getStylesheets().add(css); //adds css to voting scene

        // Display movies from VotingRoom
        TitledPane allMoviesPane = new TitledPane(); //this makes sections for movies directors and actors
        allMoviesPane.setText("Vote for Movies");
        allMoviesPane.setExpanded(false);
        VBox allMoviesContent = new VBox();

        //director TitledPane
        TitledPane directorSectionPane = new TitledPane();
        directorSectionPane.setText("Vote for Directors");
        directorSectionPane.setExpanded(false);
        VBox allDirectorContent = new VBox();

        //actor TitledPane
        TitledPane actorSectionPane = new TitledPane();
        actorSectionPane.setText("Vote for Actors");
        actorSectionPane.setExpanded(false);
        VBox allActorContent = new VBox();

        //toggle groups
        ToggleGroup movies = new ToggleGroup(); //all movies are under this toggle group
        ToggleGroup directors = new ToggleGroup(); //toggle group for directors
        ToggleGroup actors = new ToggleGroup(); //toggle group for actors

        //displays all movies in VotingScene
        for (Movie movie : votingRoom.getMovies())
        {
            VBox movieSection = createMovieSection(movie, movies, allMoviesPane, directorSectionPane, directors);
            allMoviesContent.getChildren().add(movieSection);
        }

        allMoviesPane.setContent(allMoviesContent);
        pane.getChildren().add(allMoviesPane);

        //displays all directors in VotingScene

        for (Director director : votingRoom.getDirectors())
        {
            VBox directorContent = createDirectorSection(director, directors, directorSectionPane, actorSectionPane, actors);
            allDirectorContent.getChildren().add(directorContent);
        }

        directorSectionPane.setContent(allDirectorContent);
        pane.getChildren().add(directorSectionPane);


        //displays all actors in VotingScene

        for (Actor actor : votingRoom.getActors())
        {
            VBox actorContent = createActorSection(actor, actors, actorSectionPane);
            allActorContent.getChildren().add(actorContent);
        }

        actorSectionPane.setContent(allActorContent);
        pane.getChildren().add(actorSectionPane);

        //error message is shown when there is some radiobuttons which were not chosen
        Label errorMessageLabel = new Label("");
        errorMessageLabel.setTextFill(Color.RED);
        errorMessageLabel.setPadding(new Insets(5, 0, 0, 5));
        pane.getChildren().add(errorMessageLabel);

        //vote button
        VBox votebutton = new VBox(vote);
        votebutton.setPadding(new Insets(10, 10, 10, 10));
        pane.getChildren().add(votebutton);

        //sets buttons on action
        vote.setOnAction(e -> {
            RadioButton selectedRadioButtonMovie = (RadioButton) movies.getSelectedToggle();
            RadioButton selectedRadioButtonDirector = (RadioButton) directors.getSelectedToggle();
            RadioButton selectedRadioButtonActor = (RadioButton) actors.getSelectedToggle();

            if (selectedRadioButtonMovie != null && selectedRadioButtonDirector != null && selectedRadioButtonActor != null)
            {
                Movie selectedMovie = (Movie) selectedRadioButtonMovie.getUserData();
                Director selectedDirector = (Director) selectedRadioButtonDirector.getUserData();
                Actor selectedActor = (Actor) selectedRadioButtonActor.getUserData();
                voter.vote(selectedMovie);
                voter.vote(selectedDirector);
                voter.vote(selectedActor);
                System.out.println("Selected movie: " + selectedMovie.getTitle() + "\n***********************");
                System.out.println("Selected director: " + selectedDirector.getName() + "\n***********************");
                System.out.println("Selected actor: " + selectedActor.getName() + "\n***********************");
                //printMovies(votingRoom); //if you want to print vote count after each voting session
                if (voter.getBankAccount() != null)
                {
                    MessageScene thankYouScene = new MessageScene("Thank you for your time!", voter, votingRoom);
                    stage.setScene(thankYouScene);
                } else
                {
                    MessageScene thankYouScene = new MessageScene("Thank you for your time!", votingRoom);
                    stage.setScene(thankYouScene);
                }
                votingRoom.saveVotingRoom();
            } else
            {
                errorMessageLabel.setText("You have to choose from each category!");
                PauseTransition pause = new PauseTransition(Duration.seconds(0.25)); //makes smoother transition showing the error message
                pause.setOnFinished(event -> {
                    if (selectedRadioButtonMovie == null)
                    {
                        allMoviesPane.setExpanded(true);
                        allMoviesContent.setStyle("-fx-background-color: rgba(255, 0, 0, 0.7);");

                    } else if (selectedRadioButtonActor == null)
                    {
                        allActorContent.setStyle("-fx-background-color: rgba(255, 0, 0, 0.7);");
                        actorSectionPane.setExpanded(true);
                    } else if (selectedRadioButtonDirector == null)
                    {
                        directorSectionPane.setExpanded(true);
                        allDirectorContent.setStyle("-fx-background-color: rgba(255, 0, 0, 0.7);");
                    }
                });
                pause.play(); // Start the pause transition
            }
        });

        //lets you submit votes by hitting enter
        EventHandler<KeyEvent> enterEventHandler = event -> {
            if (event.getCode() == KeyCode.ENTER)
            {
                vote.fire(); // Trigger voting button action
            }
        };

        setOnKeyPressed(enterEventHandler);
    }

    private VBox createMovieSection(Movie movie, ToggleGroup movies, TitledPane moviePane, TitledPane directorPane, ToggleGroup directors) {
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
        radioButton.setToggleGroup(movies);

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
            if (isVisible)
            {
                additionalInfoButton.setText("►");
                movieBox.getChildren().remove(directorLabel);
                movieBox.getChildren().remove(actorLabel);
                movieBox.getChildren().remove(yearLabel);
            } else
            {
                additionalInfoButton.setText("▼");
                movieBox.getChildren().add(directorLabel);
                movieBox.getChildren().add(actorLabel);
                movieBox.getChildren().add(yearLabel);
            }
        });

        radioButton.setOnAction(e -> {
            moviePane.setExpanded(false);
            if (directors.getSelectedToggle() == null)
            {
                directorPane.setExpanded(true);
            }
        });

        return movieBox;
    }

    private VBox createDirectorSection(Director director, ToggleGroup toggleGroup, TitledPane directorPane, TitledPane actorPane, ToggleGroup actors) {
        VBox directorBox = new VBox();
        RadioButton radioButton = new RadioButton(director.getName());
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setUserData(director); // Set director as user data
        directorBox.getChildren().add(radioButton);
        radioButton.setOnAction(e -> {
            directorPane.setExpanded(false);
            if (actors.getSelectedToggle() == null)
            {
                actorPane.setExpanded(true);
            }
        });
        return directorBox;
    }

    private VBox createActorSection(Actor actor, ToggleGroup toggleGroup, TitledPane actorPane) {
        VBox actorBox = new VBox();
        RadioButton radioButton = new RadioButton(actor.getName());
        radioButton.setToggleGroup(toggleGroup);
        radioButton.setUserData(actor); // Set director as user data
        actorBox.getChildren().add(radioButton);
        radioButton.setOnAction(e -> {
            actorPane.setExpanded(false);
        });
        return actorBox;
    }

    private void printMovies(VotingRoom votingRoom) {
        for (Movie movie : votingRoom.getMovies())
        {
            System.out.println("Movie: " + movie.getTitle() + ": " + movie.getVotes());
        }
        System.out.println("*************************");
        for (Director director : votingRoom.getDirectors())
        {
            System.out.println("Director: " + director.getName() + ": " + director.getVotes());
        }
        System.out.println("*************************");
        for (Actor actor : votingRoom.getActors())
        {
            System.out.println("Actor: " + actor.getName() + ": " + actor.getVotes());
        }
    }
}



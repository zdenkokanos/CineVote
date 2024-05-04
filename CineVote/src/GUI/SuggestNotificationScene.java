package GUI;

import CanBeVoted.Movie;
import VotingRoom.VotingRoom;
import Voters.Admin;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Trieda SuggestNotificationScene poskytuje grafické rozhranie pre administrátora,
 * aby mohol prijímať alebo odmietať nominácie filmov. Táto scéna zobrazuje zoznam filmov,
 * ktoré boli navrhnuté na hlasovanie, s možnosťami akceptovať alebo odmietnuť každý návrh.
 */
public class SuggestNotificationScene extends Scene {
    private Button exit = new Button("Exit");
    private String css = this.getClass().getResource("suggestion.css").toExternalForm();

    /**
     * Konštruktor pre triedu SuggestNotificationScene, ktorý inicializuje a nastavuje
     * komponenty potrebné pre zobrazenie a spracovanie navrhnutých filmov.
     *  @param votingRoom objekt hlasovacej miestnosti, kde sú uložené navrhnuté filmy
     *  @param stage hlavné okno, na ktorom sa scéna zobrazuje
     *  @param admin aktuálne prihlásený administrátor
     */
    public SuggestNotificationScene(VotingRoom votingRoom, Stage stage, Admin admin) {
        //sets the pane and the main elements
        super(new VBox(), 500, 600);
        getStylesheets().add(css);
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

        //sets buttons on action
        exit.setOnAction(e->{
            AdminScene adminScene = new AdminScene(stage, votingRoom, admin);
            stage.setScene(adminScene);
        });
        Insets buttonMargin = new Insets(10, 0, 0, 0);  // Top, Right, Bottom, Left margins
        VBox.setMargin(exit, buttonMargin);
        container.getChildren().add(exit);
    }

    /**
     * Vytvára grafický kontajner pre jednotlivý film zoznamu nominovaných filmov.
     * Každý kontajner obsahuje názov filmu a tlačidlá na akceptáciu alebo zamietnutie filmu.
     *
     * @param movie film, pre ktorý sa vytvára grafický kontajner
     * @param votingRoom referencia na objekt VotingRoom, ktorý spravuje nominácie
     * @param stage hlavné okno, na ktorom sa môžu vykonávať ďalšie akcie
     * @param parentContainer rodičovský kontajner, do ktorého sa vkladá tento box
     * @return VBox kontajner obsahujúci grafické prvky pre jeden film
     */
    private VBox createMovieBox(Movie movie, VotingRoom votingRoom, Stage stage, VBox parentContainer) {
        Label label = new Label(movie.getTitle());
        label.getStyleClass().add("movie-label");

        Button acceptButton = new Button("Accept");
        acceptButton.getStyleClass().add("accept-button");

        Button declineButton = new Button("Decline");
        declineButton.getStyleClass().add("decline-button");

        HBox buttonBox = new HBox(10, acceptButton, declineButton); // Spacing between buttons
        VBox box = new VBox(10, label, buttonBox); // Spacing between label and button box
        box.setPadding(new Insets(10));
        box.setStyle("-fx-background-color: white; -fx-border-color: #ccc; -fx-border-radius: 5px;");
        box.getStyleClass().add("movie-box");
        //sets buttons on action
        acceptButton.setOnAction(e -> {
            votingRoom.acceptSuggestion(movie);
            votingRoom.saveVotingRoom();
            parentContainer.getChildren().remove(box);

        });

        declineButton.setOnAction(e -> {
            votingRoom.declineSuggestion(movie);
            votingRoom.saveVotingRoom();
            parentContainer.getChildren().remove(box);

        });

        return box;
    }
}

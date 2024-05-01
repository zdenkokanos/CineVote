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
        Button button1 = new Button("Accept");
        Button button2 = new Button("Decline");
        VBox box = new VBox(label, new HBox(button1, button2));

        //sets buttons on action
        button1.setOnAction(e -> {
            votingRoom.acceptSuggestion(movie);
            votingRoom.saveVotingRoom();
            parentContainer.getChildren().remove(box);

        });

        button2.setOnAction(e -> {
            votingRoom.declineSuggestion(movie);
            votingRoom.saveVotingRoom();
            parentContainer.getChildren().remove(box);

        });

        //adds some css styling
        box.setStyle("-fx-border-color: black; -fx-border-width: 1px;");
        box.setSpacing(5);
        box.setPadding(new Insets(5));
        return box;
    }
}

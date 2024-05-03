package GUI;

import Voters.*;
import VotingRoom.VotingRoom;
import VotingRoom.People;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Trieda ExitScene poskytuje scénu pre záverečné interakcie s používateľom po úpravách alebo akciách v aplikácii.
 * Umožňuje používateľom pokračovať v používaní aplikácie, odhlásiť sa alebo ukončiť aplikáciu.
 */
public class ExitScene extends Scene {
    private String css = this.getClass().getResource("main.css").toExternalForm();
    private Button continueB = new Button("Continue");
    private Button logOut = new Button("Log out");
    private Button exitB = new Button("Exit");


    /**
     * Konštruktor pre triedu ExitScene, ktorý inicializuje a nastavuje
     * komponenty potrebné pre interakciu s používateľom po dokončení akcie v aplikácii.
     *
     * @param votingRoom objekt hlasovacej miestnosti, kde sú uložené dáta a nastavenia
     * @param stage      hlavné okno, na ktorom sa scéna zobrazuje
     * @param voter      objekt aktuálne prihláseného používateľa
     */
    public ExitScene(VotingRoom votingRoom, Stage stage, People voter) {
        //sets the pane and the main elements
        super(new VBox(), 500, 500, Color.LIGHTGRAY);
        VBox vbox = (VBox) getRoot();
        vbox.setAlignment(Pos.CENTER);
        getStylesheets().add(css);

        Label messageLabel = new Label("Succesfully updated");
        messageLabel.setId("message");

        //sets all buttons on action
        exitB.setOnAction(e -> { //this button exits the stage
            votingRoom.saveVotingRoom();
            stage.close();
        });
        continueB.setOnAction(e -> { //this button lets you continue in suggesting movies
            AddMovieScene addMovieScene = new AddMovieScene(stage, votingRoom, voter, "Suggest movie nomination");

            if (voter instanceof Admin)
            {
                AdminScene adminScene = new AdminScene(stage, votingRoom, (Admin) voter);
                stage.setScene(adminScene);
            } else
            {
                stage.setScene(addMovieScene);
            }
        });
        logOut.setOnAction(e -> { //this buttons lets you logOut and log In as a different user so you don't have to log back on
            stage.setScene(new LogInScene(stage, votingRoom));
        });
        vbox.getChildren().addAll(messageLabel, continueB, logOut, exitB);
    }
}

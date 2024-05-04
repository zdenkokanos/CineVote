package GUI;

import Voters.*;
import VotingRoom.VotingRoom;
import VotingRoom.People;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Trieda AddMovieScene poskytuje scénu pre pridávanie alebo nominovanie filmov v rámci aplikácie.
 * Umožňuje používateľom zadávať informácie o filme, režisérovi a hlavnom hercovi.
 */
public class AddMovieScene extends Scene {
    private TextField movieName = new TextField();
    private TextField directorName = new TextField();
    private TextField directorAge = new TextField();
    private TextField actorName = new TextField();
    private TextField actorAge = new TextField();
    private TextField makeYear = new TextField();
    private Button submit = new Button("Submit");
    private Button logOut = new Button("Log Out");
    private Button goBack = new Button("Exit");
    private String css = this.getClass().getResource("addMovieCSS.css").toExternalForm();

    /**
     * Konštruktor pre triedu AddMovieScene, ktorý inicializuje komponenty a nastavuje správanie scény.
     * Táto scéna umožňuje používateľom Middle class a Admin zadávať údaje potrebné pre nomináciu alebo návrh filmu.
     *
     * @param stage      hlavné okno, na ktorom sa scéna zobrazuje
     * @param votingRoom referencia na objekt hlasovacej miestnosti, kde sa ukladajú filmy
     * @param voter      objekt používateľa, ktorý vykonáva nomináciu alebo návrh filmu
     * @param message    správa zobrazujúca sa na začiatku formulára, môže označovať kontext, v ktorom sa formulár používa
     */
    public AddMovieScene(Stage stage, VotingRoom votingRoom, People voter, String message) {
        //sets the pane and the main elements
        super(new VBox(), 500, 600, Color.LIGHTGRAY);
        VBox vBox = (VBox) getRoot();
        vBox.getStyleClass().add("background");
        GridPane gridPane = new GridPane();
        Label messageLabel = new Label(message);
        getStylesheets().add(css);
        messageLabel.setId("messageLabel");
        vBox.getChildren().add(messageLabel);
        vBox.getChildren().add(gridPane);
        vBox.setAlignment(Pos.CENTER);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setVgap(10); // Vertical gap between elements
        gridPane.setHgap(10); // Horizontal gap between elements
        gridPane.setPadding(new Insets(80, 0, 0, 0));
        vBox.setPadding(new Insets(30, 0, 0, 0));



        // Add labels and text fields to the grid pane
        gridPane.add(new Label("Movie Name:"), 0, 0);
        gridPane.add(movieName, 1, 0);
        gridPane.add(new Label("Director Name:"), 0, 1);
        gridPane.add(directorName, 1, 1);
        gridPane.add(new Label("Director Age:"), 0, 2);
        gridPane.add(directorAge, 1, 2);
        gridPane.add(new Label("Actor Name:"), 0, 3);
        gridPane.add(actorName, 1, 3);
        gridPane.add(new Label("Actor Age:"), 0, 4);
        gridPane.add(actorAge, 1, 4);
        gridPane.add(new Label("Make Year:"), 0, 5);
        gridPane.add(makeYear, 1, 5);
        gridPane.add(submit, 1, 6);

        Label errorMessageLabel = new Label("");
        errorMessageLabel.setTextFill(Color.RED);
        errorMessageLabel.setPadding(new Insets(10, 0, 0, 0));
        vBox.getChildren().add(errorMessageLabel);


        //sets buttons on action
        submit.setOnAction(e -> {
            String movieNameText = movieName.getText();
            String directorNameText = directorName.getText();
            String directorAgeTextStr = directorAge.getText();
            String actorNameText = actorName.getText();
            String actorAgeTextStr = actorAge.getText();
            String makeYearTextStr = makeYear.getText();

            if (movieNameText.isEmpty() || directorNameText.isEmpty() || directorAgeTextStr.isEmpty() ||
                    actorNameText.isEmpty() || actorAgeTextStr.isEmpty() || makeYearTextStr.isEmpty())
            {
                errorMessageLabel.setText("Please fill in all fields.");
                return;
            }
            try
            {
                int directorAgeText = Integer.parseInt(directorAgeTextStr);
                int actorAgeText = Integer.parseInt(actorAgeTextStr);
                int makeYearText = Integer.parseInt(makeYearTextStr);

                if (voter instanceof Admin)
                {
                    ((Admin) voter).nominate(movieNameText, directorNameText, directorAgeText, actorNameText, actorAgeText, makeYearText, votingRoom);
                } else if (voter instanceof MiddleClass)
                {
                    ((MiddleClass) voter).suggest_nomination(movieNameText, directorNameText, directorAgeText, actorNameText, actorAgeText, makeYearText, votingRoom);
                }
                votingRoom.saveVotingRoom();
                ExitScene exitScene = new ExitScene(votingRoom, stage, voter);
                stage.setScene(exitScene);
            } catch (NumberFormatException ex)
            {
                errorMessageLabel.setText("Please enter valid integer values for age and year.");
            }

        });

        //lets you submit with hitting Enter Key
        EventHandler<KeyEvent> enterEventHandler = event -> {
            if (event.getCode() == KeyCode.ENTER)
            {
                submit.fire(); // Trigger submit button action
            }
        };

        setOnKeyPressed(enterEventHandler);

        goBack.setId("exit");
        logOut.setId("exit");

        goBack.setOnAction(e->{
            AdminScene adminScene = new AdminScene(stage, votingRoom, (Admin) voter);
            stage.setScene(adminScene);
        });

        logOut.setOnAction(e->{
            LogInScene logInScene = new LogInScene(stage, votingRoom);
            stage.setScene(logInScene);
        });

        if(voter instanceof Admin){
            vBox.getChildren().add(goBack);
        }
        else{
            vBox.getChildren().add(logOut);
        }
    }
}


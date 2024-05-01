package GUI;

import Voters.*;
import CanBeVoted.*;
import VotingRoom.*;
import Voters.LowerClass;
import VotingRoom.VotingRoom;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

import static CanBeVoted.BankAccount.createAccount;
/**
 * Trieda LogInScene poskytuje užívateľské rozhranie pre prihlásenie do hlasovacieho systému
 * a registráciu nových užívateľov. Umožňuje užívateľom zadať svoje používateľské meno a heslo na prihlásenie,
 * alebo vytvoriť nový účet, ak ešte nemajú svoj vlastný. Scéna je nastavená s jednoduchým
 * formulárom pre vstup od užívateľa a tlačidlami na odoslanie prihlasovacích alebo registračných formulárov.
 */
public class LogInScene extends Scene {
    private final Button logIn = new Button("Log In");
    private String username;
    private String password;
    private boolean exists = false;
    private final Button register = new Button("Register");
    private TextField usernameInput = new TextField();
    private PasswordField passwordInput = new PasswordField();
    private String css = this.getClass().getResource("login.css").toExternalForm();

    /**
     * Konštruktor pre triedu LogInScene, ktorý inicializuje grafické rozhranie pre prihlásenie a registráciu.
     * Vytvorí všetky potrebné grafické komponenty a pridá ich do scény.
     *
     * @param primaryStage hlavné okno aplikácie, kde bude scéna zobrazená
     * @param votingRoom referencia na objekt VotingRoom, ktorý obsahuje údaje o hlasovacích voličoch a procesoch
     */
    public LogInScene(Stage primaryStage, VotingRoom votingRoom) {
        //sets the pane and the main elements
        super(new VBox(), 500, 600, Color.BLACK);
        VBox vBox = (VBox) getRoot();
        GridPane gridPane = new GridPane();
        Label pleaseLogLabel = new Label("Welcome at Cinevote!");
        pleaseLogLabel.setId("pleaseLogLabel");
        VBox.setMargin(pleaseLogLabel, new Insets(0, 0, 30, 0));
        vBox.getChildren().add(pleaseLogLabel);
        vBox.getChildren().addAll(gridPane);
        vBox.setAlignment(Pos.CENTER);
        vBox.getStyleClass().add("background");
        getStylesheets().add(css);
        logIn.setId("logInButton");
        register.setId("registerButton");
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER); // Center the GridPane

        //login input layout
        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameInput, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordInput, 1, 1);
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(logIn, register);

        //button layout with error message
        gridPane.add(buttonBox, 1, 2);
        Label errorMessageLabel = new Label("");
        errorMessageLabel.setTextFill(Color.RED); // Set text color to red
        gridPane.add(errorMessageLabel, 0, 3, 2, 1); // Add error message label to the grid
        votingRoom.loadVotingRoom(); //this deserializates the object to continue in the ongoing voting

        //set buttons on action
        logIn.setOnAction(e -> {
            for (People voter : votingRoom.getVoters())
            {
                // Check if the entered username and password match the voter's credentials
                if (voter.getUsername().equals(usernameInput.getText()) && voter.getPassword().equals(passwordInput.getText()))
                {
                    if (voter.getVoted())
                    {
                        if (voter instanceof Admin)
                        {
                            AdminScene adminScene = new AdminScene(primaryStage, votingRoom, (Admin) voter);
                            primaryStage.setScene(adminScene);
                            break;
                        } else if (voter instanceof MiddleClass)
                        {
                            AddMovieScene addMovieScene = new AddMovieScene(primaryStage, votingRoom, voter, "Suggest movie nomination");
                            primaryStage.setScene(addMovieScene);
                            break;
                        } else
                        {
                            if (voter.getBankAccount() != null)
                            {
                                MessageScene alreadyVotedScene = new MessageScene("You have already voted!", voter, votingRoom);
                                primaryStage.setScene(alreadyVotedScene);
                            } else
                            {
                                MessageScene alreadyVotedScene = new MessageScene("You have already voted!", votingRoom);
                                primaryStage.setScene(alreadyVotedScene);
                            }
                            break;
                        }
                    } else
                    {
                        VotingScene votingScene = new VotingScene(votingRoom, voter, primaryStage);
                        primaryStage.setScene(votingScene); // If match found, switch to voting scene
                    }
                    break; // Exit the loop since a match is found
                } else
                {
                    errorMessageLabel.setText("Incorrect username or password.");
                }
            }

        });

        register.setOnAction(e -> {
            exists = false;
            username = usernameInput.getText();
            password = passwordInput.getText();
            try
            {
                if (username.isEmpty() || password.isEmpty())
                {
                    throw new BlankInputException("Username and password fields cannot be blank.");
                } else
                {
                    for (People voter : votingRoom.getVoters())
                    {
                        if (username.equals(voter.getUsername()))
                        {
                            exists = true;
                        }
                    }
                    if (!exists)
                    {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Do you want to create a bank account?");

                        // Add yes and no buttons
                        alert.getButtonTypes().setAll(ButtonType.YES, ButtonType.NO);

                        // Show the alert and wait for user response
                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.YES)
                            {
                                // User clicked yes, proceed with registration
                                LowerClass voter = new LowerClass(username, password, createAccount(100));
                                votingRoom.addVoter(voter);
                                VotingScene votingScene = new VotingScene(votingRoom, voter, primaryStage);
                                primaryStage.setScene(votingScene);
                            } else if (response == ButtonType.NO)
                            {
                                LowerClass voter = new LowerClass(username, password);
                                votingRoom.addVoter(voter);
                                VotingScene votingScene = new VotingScene(votingRoom, voter, primaryStage);
                                primaryStage.setScene(votingScene);
                            }
                        });
                    } else
                    {
                        errorMessageLabel.setText("This user already exists, please log in!");
                    }
                }
            } catch (BlankInputException ex)
            {
                errorMessageLabel.setText(ex.getMessage());
            }
        });

        //hitting enter lets you log in, if you want to register you have to hit the button manually
        EventHandler<KeyEvent> enterEventHandler = event -> {
            if (event.getCode() == KeyCode.ENTER)
            {
                logIn.fire(); // Trigger submit button action
            }
        };

        setOnKeyPressed(enterEventHandler);
    }
}

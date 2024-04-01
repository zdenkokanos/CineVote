package GUI;

import Voters.*;
import Voters.LowerClass;
import VotingRoom.VotingRoom;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

public class LogInScene extends Scene {

    private final Button logIn = new Button("Log In");
    private String username;
    private String password;
    private final Button register = new Button("Register");
    private VotingRoom votingRoom = new VotingRoom();
    private TextField usernameInput = new TextField();
    private PasswordField passwordInput = new PasswordField();
    private MessageScene alreadyVotedScene = new MessageScene("You have already voted!");
    private String css = this.getClass().getResource("login.css").toExternalForm();

    public LogInScene(Stage primaryStage) {
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

        gridPane.add(new Label("Username:"), 0, 0);
        gridPane.add(usernameInput, 1, 0);
        gridPane.add(new Label("Password:"), 0, 1);
        gridPane.add(passwordInput, 1, 1);
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(logIn, register);

        gridPane.add(buttonBox, 1, 2);
        Label errorMessageLabel = new Label("");
        errorMessageLabel.setTextFill(Color.RED); // Set text color to red
        gridPane.add(errorMessageLabel, 0, 3, 2, 1); // Add error message label to the grid
        votingRoom.loadVotingRoom();
        logIn.setOnAction(e -> {
            for (Voters voter : votingRoom.getVoters())
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
                            primaryStage.setScene(alreadyVotedScene);
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
            username = usernameInput.getText();
            password = passwordInput.getText();
            if (username.isEmpty() || password.isEmpty())
            {
                errorMessageLabel.setText("You must enter username and password!");
            } else
            {
                LowerClass voter = new LowerClass(username, password);
                votingRoom.addVoter(voter);
                VotingScene votingScene = new VotingScene(votingRoom, voter, primaryStage);
                primaryStage.setScene(votingScene); // If match found, switch to voting scene
            }
        });

    }
}

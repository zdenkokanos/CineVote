package GUI;

import CanBeVoted.*;
import Voters.Voters;
import VotingRoom.VotingRoom;
import VotingRoom.People;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MessageScene extends Scene {
    private String css = this.getClass().getResource("main.css").toExternalForm();
    private Button backToLogin = new Button("Back to login?");
    private TextField donationInput = new TextField();
    private ComboBox<AccountItem> accountDropdown = new ComboBox<>();
    private Button submitDonation = new Button("Submit");
    private Label errorMessageLabel = new Label("");

    public MessageScene(String message, People voter, VotingRoom votingRoom) {
        // Sets the pane and the main elements
        super(new VBox(), 500, 600, Color.LIGHTGRAY);
        VBox vbox = (VBox) getRoot();
        vbox.setAlignment(Pos.CENTER);
        getStylesheets().add(css);

        Label thankYouLabel = new Label(message);
        thankYouLabel.setId("message");
        Label currentBalance = new Label("Your current balance is: " + voter.getBalance() + " €");
        errorMessageLabel.setTextFill(Color.RED);

        // Set the width of the donation input
        donationInput.setPrefWidth(100); // Adjust the width as needed
        // Set up the account dropdown
        accountDropdown.setValue(new AccountItem("Choose name", null));

        for (Actor actor : votingRoom.getActors())
        {
            if (actor.getAccount() != null)
            {
                accountDropdown.getItems().add(new AccountItem(actor.getName() + " -> actor", actor));
            }
        }

        for (Director director : votingRoom.getDirectors())
        {
            if (director.getAccount() != null)
            {
                accountDropdown.getItems().add(new AccountItem(director.getName() + " -> director", director));
            }
        }


        // Sets buttons on action
        backToLogin.setOnAction(e -> {
            Stage primaryStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            primaryStage.setScene(new LogInScene(primaryStage, votingRoom));
        });

        submitDonation.setOnAction(e -> {
            AccountItem selectedItem = accountDropdown.getValue();
            if (accountDropdown.getSelectionModel().isEmpty() || "Choose account".equals(accountDropdown.getValue()))
            {
                errorMessageLabel.setText("Please select an account to donate to.");
                return; // Exit the method early if no account is selected
            }
            String donationAmountText = donationInput.getText();
            try
            {
                float donationAmount = Float.parseFloat(donationAmountText);
                Payable selectedAccount = selectedItem.getPayable();

                // Confirmation dialog
                Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
                confirmDialog.setTitle("Confirm Donation");
                confirmDialog.setHeaderText("Are you sure you want to donate €" + String.format("%.2f", donationAmount) + " to " + selectedAccount.getName() + "?");
                confirmDialog.setContentText("This action cannot be undone.");

                Optional<ButtonType> result = confirmDialog.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK)
                {
                    // User confirmed the donation
                    if (donationAmount <= voter.getBalance())
                    {
                        voter.pay(selectedAccount.getAccount(), donationAmount, voter.getBankAccount());
                        currentBalance.setText("Your current balance is: " + String.format("%.2f", voter.getBalance()) + " €");
                        errorMessageLabel.setText("Donation successful.");
                        votingRoom.saveVotingRoom();
                    } else
                    {
                        errorMessageLabel.setText("Insufficient balance to make the donation.");
                    }
                } else
                {
                    // User cancelled the donation
                    errorMessageLabel.setText("Donation cancelled.");
                }
            } catch (NumberFormatException ex)
            {
                errorMessageLabel.setText("Invalid donation amount: " + donationAmountText); //display error under button
            }
            System.out.println("button to pay was hit");
        });


        // Add elements to the pane
        vbox.getChildren().addAll(thankYouLabel, currentBalance, donationInput, new HBox(accountDropdown, submitDonation, errorMessageLabel), backToLogin);

        // Lets you move back to login just by hitting enter button
        EventHandler<KeyEvent> enterEventHandler = event -> {
            if (event.getCode() == KeyCode.ENTER)
            {
                submitDonation.fire();
            }
        };

        setOnKeyPressed(enterEventHandler);
    }

    public MessageScene(String message, VotingRoom votingRoom) {
        super(new VBox(), 500, 600, Color.LIGHTGRAY);
        VBox vbox = (VBox) getRoot();
        vbox.setAlignment(Pos.CENTER);
        getStylesheets().add(css);

        Label thankYouLabel = new Label(message);
        thankYouLabel.setId("message");

        backToLogin.setOnAction(e -> {
            Stage primaryStage = (Stage) ((Button) e.getSource()).getScene().getWindow();
            primaryStage.setScene(new LogInScene(primaryStage, votingRoom));
        });

        vbox.getChildren().addAll(thankYouLabel, backToLogin);

        // Lets you move back to login just by hitting enter button
        EventHandler<KeyEvent> enterEventHandler = event -> {
            if (event.getCode() == KeyCode.ENTER)
            {
                backToLogin.fire();
            }
        };
    }
}

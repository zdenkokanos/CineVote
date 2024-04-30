package GUI;

import VotingRoom.VotingRoom;
import VotingRoom.Observer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Results extends Scene {
    private Observer votingRoomObserver;
    private BarChart<String, Number> barChartMovies;
    private BarChart<String, Number> barChartActors;
    private BarChart<String, Number> barChartDirectors;
    private Button tableView = new Button("Table View");
    private Button startNewVote = new Button("Start new voting");
    private ComboBox<String> graphSelector;

    public Results(Stage stage, VotingRoom votingRoom) {
        super(new VBox(), 500, 600);
        VBox vBox = (VBox) getRoot();
        //Observer
        votingRoomObserver = new Observer(votingRoom);
        votingRoom.addObserver(votingRoomObserver);

        // Bar Chart Movies
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChartMovies = new BarChart<>(xAxis, yAxis);
        yAxis.setTickUnit(2);
        yAxis.setLabel("Movies");
        barChartMovies.setTitle("Voting has ended!");
        barChartMovies.getData().clear();
        barChartMovies.getData().add(votingRoomObserver.update(votingRoom.getWinnersMovies(), barChartMovies));
        VBox vBoxBarCharts = new VBox(barChartMovies);
        //Bar Chart Actors
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
        yAxis2.setTickUnit(2);
        barChartActors = new BarChart<>(xAxis2, yAxis2);
        yAxis2.setLabel("Actors");
        barChartActors.setTitle("Voting has ended!");
        //Bar Chart Directors
        CategoryAxis xAxis3 = new CategoryAxis();
        NumberAxis yAxis3 = new NumberAxis();
        yAxis3.setTickUnit(2);
        barChartDirectors = new BarChart<>(xAxis3, yAxis3);
        yAxis3.setLabel("Directors");
        barChartDirectors.setTitle("Voting has ended!");


        // Initialize the graph selector ComboBox
        graphSelector = new ComboBox<>();
        graphSelector.setPromptText("Movies");
        ObservableList<String> graphOptions = FXCollections.observableArrayList("Movies", "Actors", "Directors");
        graphSelector.setItems(graphOptions);
        //graphSelector to change which graph you want to look at
        graphSelector.setOnAction(e -> {
            String selectedGraph = graphSelector.getValue();
            if (selectedGraph != null)
            {
                switch (selectedGraph)
                {
                    case "Movies":
                        barChartMovies.getData().clear();
                        barChartMovies.getData().add(votingRoomObserver.update(votingRoom.getWinnersMovies(), barChartMovies));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartMovies);
                        break;
                    case "Actors":
                        barChartActors.getData().clear();
                        barChartActors.getData().add(votingRoomObserver.update(votingRoom.getWinnersActors(), barChartActors));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartActors);
                        break;
                    case "Directors":
                        barChartDirectors.getData().clear();
                        barChartDirectors.getData().add(votingRoomObserver.update(votingRoom.getWinnersDirectors(), barChartDirectors));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartDirectors);
                        break;
                }
            }
        });

        tableView.setOnAction(e -> {
            DetailScene detailScene = new DetailScene(stage, "message");
            stage.setScene(detailScene);
        });

        startNewVote.setOnAction(e -> {
            showAdminPasswordDialog(stage, votingRoom);
        });


        vBox.getChildren().addAll(graphSelector, vBoxBarCharts, tableView, startNewVote);
    }

    private void showAdminPasswordDialog(Stage parentStage, VotingRoom votingRoom) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Admin Authentication");
        dialog.setHeaderText("Enter Admin Password");
        dialog.setContentText("Password:");

        // Hide the input with bullet characters for privacy
        PasswordField pwdField = new PasswordField();
        dialog.getEditor().setText("Enter your password");
        dialog.getEditor().setManaged(false);
        dialog.getEditor().setVisible(false);
        dialog.getDialogPane().setContentText("Password:");
        dialog.getDialogPane().setContent(pwdField);


        // Focus on the password field by default
        dialog.getDialogPane().expandedProperty().addListener((observable, oldValue, newValue) -> pwdField.requestFocus());

        dialog.showAndWait().ifPresent(password -> {
            System.out.println(dialog.getContentText());
            if (password.equals("pass"))
            {  // Assuming "admin" is the correct password.
                votingRoom.restartVoting();
                Alert confirmation = new Alert(Alert.AlertType.INFORMATION, "Voting has been restarted successfully.");
                confirmation.showAndWait();
            } else
            {
                Alert error = new Alert(Alert.AlertType.ERROR, "Invalid password.");
                error.showAndWait();
            }
        });
    }

    public static class DetailScene extends Scene {
        public DetailScene(Stage stage, String message) {
            super(new VBox(), 500, 600);
            VBox root = (VBox) getRoot();
            Label label = new Label(message);
            root.getChildren().add(label);
        }
    }
}

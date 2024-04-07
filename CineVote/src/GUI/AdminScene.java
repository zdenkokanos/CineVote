package GUI;

import Voters.Admin;
import VotingRoom.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class AdminScene extends Scene {
    private Button addMovie = new Button("Add movies");
    private Button startNew = new Button("Start new voting");
    private Button notifications = new Button("");
    private StackPane notificationStackPane = new StackPane();
    private Button exit = new Button("Exit");
    private Button logOut = new Button("Log Out");
    private Observer votingRoomObserver;

    private BarChart<String, Number> barChartMovies;
    private BarChart<String, Number> barChartActors;
    private BarChart<String, Number> barChartDirectors;

    private ComboBox<String> graphSelector;

    public AdminScene(Stage stage, VotingRoom votingRoom, Admin admin) {
        //sets the pane and the main elements
        super(new AnchorPane(), 500, 600, Color.LIGHTGRAY);
        AnchorPane root = (AnchorPane) getRoot();
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        // Load the notification icon and its count of notifications in small red circle
        Image notificationImage = new Image(getClass().getResourceAsStream("/notificationIcon.png"));
        if (notificationImage != null)
        {
            ImageView notifIcon = new ImageView(notificationImage);
            notifIcon.setFitWidth(24);
            notifIcon.setFitHeight(24);
            notifications.setGraphic(notifIcon);

            Circle counterCircle = new Circle(5, Color.RED);
            Label counterLabel = new Label();
            counterLabel.setText(String.valueOf(votingRoom.getNominationCount()));
            counterLabel.setTextFill(Color.BLACK);
            counterLabel.setFont(Font.font("Arial", FontWeight.BOLD, 10));
            StackPane labelPane = new StackPane(counterLabel); // Wrap label in StackPane to apply padding
            labelPane.setPadding(new Insets(-1, 2, 0, 0));

            notificationStackPane.getChildren().addAll(counterCircle, labelPane);
            StackPane.setAlignment(counterLabel, Pos.CENTER);
            StackPane.setAlignment(counterCircle, Pos.TOP_RIGHT);
            StackPane.setAlignment(counterLabel, Pos.TOP_RIGHT);

            // Position the notificationStackPane
            AnchorPane.setTopAnchor(notifications, 10.0);
            AnchorPane.setRightAnchor(notifications, 10.0);
            AnchorPane.setTopAnchor(notificationStackPane, 15.0);
            AnchorPane.setRightAnchor(notificationStackPane, 15.0);
        } else
        {
            System.err.println("Failed to load notification icon.");
        }

        //sets buttons on action
        addMovie.setOnAction(e -> {
            AddMovieScene addMovieScene = new AddMovieScene(stage, votingRoom, admin, "Nominate movies");
            stage.setScene(addMovieScene);
        });

        notifications.setOnAction(e -> {
            SuggestNotificationScene suggestNotificationScene = new SuggestNotificationScene(votingRoom, stage, admin);
            stage.setScene(suggestNotificationScene);
        });

        exit.setOnAction(e -> stage.close());

        startNew.setOnAction(e -> {
            votingRoom.restartVoting();
            stage.close();
        });

        logOut.setOnAction(e -> stage.setScene(new LogInScene(stage)));
        //Observer
        votingRoomObserver = new Observer(votingRoom);
        votingRoom.addObserver(votingRoomObserver);

        // Bar Chart Movies
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChartMovies = new BarChart<>(xAxis, yAxis);
        yAxis.setTickUnit(2);
        yAxis.setLabel("Movies");
        barChartMovies.setTitle("Voting is still running...");
        barChartMovies.getData().clear();
        barChartMovies.getData().add(votingRoomObserver.update(votingRoom.getMovies(), barChartMovies));
        VBox vBoxBarCharts = new VBox(barChartMovies);
        //Bar Chart Actors
        CategoryAxis xAxis2 = new CategoryAxis();
        NumberAxis yAxis2 = new NumberAxis();
        yAxis2.setTickUnit(2);
        barChartActors = new BarChart<>(xAxis2, yAxis2);
        yAxis2.setLabel("Actors");
        barChartActors.setTitle("Voting is still running...");
        //Bar Chart Directors
        CategoryAxis xAxis3 = new CategoryAxis();
        NumberAxis yAxis3 = new NumberAxis();
        yAxis3.setTickUnit(2);
        barChartDirectors = new BarChart<>(xAxis3, yAxis3);
        yAxis3.setLabel("Directors");
        barChartDirectors.setTitle("Voting is still running...");


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
                        barChartMovies.getData().add(votingRoomObserver.update(votingRoom.getMovies(), barChartMovies));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartMovies);
                        break;
                    case "Actors":
                        barChartActors.getData().clear();
                        barChartActors.getData().add(votingRoomObserver.update(votingRoom.getActors(), barChartActors));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartActors);
                        break;
                    case "Directors":
                        barChartDirectors.getData().clear();
                        barChartDirectors.getData().add(votingRoomObserver.update(votingRoom.getDirectors(), barChartDirectors));
                        vBoxBarCharts.getChildren().clear();
                        vBoxBarCharts.getChildren().add(barChartDirectors);
                        break;
                }
            }
        });

        //adds the elements to the pane
        vbox.getChildren().addAll(vBoxBarCharts, addMovie, startNew, logOut, exit);
        root.getChildren().addAll(vbox, notifications, notificationStackPane, graphSelector);

    }

}

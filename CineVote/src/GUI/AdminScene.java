package GUI;

import CanBeVoted.Movie;
import Voters.Admin;
import VotingRoom.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
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
import java.util.List;

public class AdminScene extends Scene implements VotingObserver {
    private Button addMovie = new Button("Add movies");
    private Button startNew = new Button("Start new voting");
    private Button notifications = new Button("");
    private StackPane notificationStackPane = new StackPane();
    private Button exit = new Button("Exit");

    private Button logOut = new Button("Log Out");

    private BarChart<String, Number> barChart;


    public AdminScene(Stage stage, VotingRoom votingRoom, Admin admin) {
        super(new AnchorPane(), 500, 600, Color.LIGHTGRAY);
        AnchorPane root = (AnchorPane) getRoot();

        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        // Load the notification icon
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


        // Bar Chart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setTickUnit(2);
        barChart = new BarChart<>(xAxis, yAxis);
        yAxis.setLabel("Movies");
        barChart.setTitle("Voting is still running...");

        vbox.getChildren().addAll(barChart, addMovie, startNew, logOut,exit);
        root.getChildren().addAll(vbox, notifications, notificationStackPane);

        // register as an observer
        votingRoom.addObserver(this);
        //update with Observer
        update(votingRoom.getMovies());
    }

    @Override
    public void update(List<Movie> movies) {
        // update the bar chart with the new voting data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Movie movie : movies)
        {
            System.out.println("Movie: "+movie.getTitle()+ ": "+ movie.getVotes());
            series.getData().add(new XYChart.Data<>(movie.getTitle(), movie.getVotes()));
        }
        barChart.getData().clear(); // clear existing data
        barChart.getData().add(series); // add updated data
    }
}
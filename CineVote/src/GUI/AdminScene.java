package GUI;

import Voters.Admin;
import Voters.Voters;
import VotingRoom.Movie;
import VotingRoom.VotingRoom;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;


public class AdminScene extends Scene {
    private Button addMovie = new Button("Add movies");
    private Button notifications = new Button("Notifications");

    public AdminScene(Stage stage, VotingRoom votingRoom, Admin admin) {
        super(new VBox(), 500, 500, Color.LIGHTGRAY);
        VBox vbox = (VBox) getRoot();
        vbox.setAlignment(Pos.CENTER);

        addMovie.setOnAction(e -> {
            AddMovieScene addMovieScene = new AddMovieScene(stage, votingRoom, (Voters) admin, "Nominate movies");
            stage.setScene(addMovieScene);
        });

        notifications.setOnAction(e -> {
            SuggestNotificationScene suggestNotificationScene = new SuggestNotificationScene(votingRoom, stage);
            stage.setScene(suggestNotificationScene);
        });

        // Creating a simple bar chart
        List<Movie> movies = new ArrayList<>();
        movies = votingRoom.getMovies();
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Voting is still running...");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Movie movie : movies)
        {
            series.getData().add(new XYChart.Data<>(movie.getTitle(), movie.getVotes()));
        }
        barChart.getData().add(series);

        vbox.getChildren().addAll(addMovie, notifications, barChart);
    }
}

package VotingRoom;

import CanBeVoted.Movie;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.List;

public interface VotingObserver {
    void update(List<Movie> movies);
    <T> XYChart.Series<String, Number> update(List<?> movies, BarChart<String, Number> barChart);
}

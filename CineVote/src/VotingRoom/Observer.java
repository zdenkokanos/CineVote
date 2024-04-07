package VotingRoom;

import CanBeVoted.Actor;
import CanBeVoted.Director;
import CanBeVoted.Movie;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.ArrayList;
import java.util.List;

public class Observer implements VotingObserver {
    private VotingRoom votingRoom;

    public Observer(VotingRoom votingRoom) {
        this.votingRoom = votingRoom;
    }

    public <T> XYChart.Series<String, Number> update(List<?> list, BarChart<String, Number> barChart) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for (Object object : list) {
            if (object instanceof Movie) {
                Movie movie = (Movie) object;
                series.getData().add(new XYChart.Data<>(movie.getTitle(), movie.getVotes()));
            }
            if(object instanceof Director){
                Director director = (Director) object;
                series.getData().add(new XYChart.Data<>(director.getName(), director.getVotes()));
            }
            if(object instanceof Actor){
                Actor actor = (Actor) object;
                series.getData().add(new XYChart.Data<>(actor.getName(), actor.getVotes()));
            }
        }
        return series;
    }

    public void update(List<Movie> movie) {

    }

}

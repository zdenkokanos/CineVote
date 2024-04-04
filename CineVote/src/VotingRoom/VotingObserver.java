package VotingRoom;

import CanBeVoted.Movie;

import java.util.List;

public interface VotingObserver {
    void update(List<Movie> movies);
}

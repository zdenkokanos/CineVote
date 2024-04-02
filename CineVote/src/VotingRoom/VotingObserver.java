package VotingRoom;

import java.util.List;

public interface VotingObserver {
    void update(List<Movie> movies);
}

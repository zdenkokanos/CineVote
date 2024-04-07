package VotingRoom;

import CanBeVoted.*;

public interface VotingProcess {
     void vote(CanBeVoted canBeVoted);
     String getUsername();
     String getPassword();
     boolean getVoted();
}

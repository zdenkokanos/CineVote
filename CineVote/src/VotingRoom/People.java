package VotingRoom;

import CanBeVoted.BankAccount;
import CanBeVoted.CanBeVoted;
import com.sun.javafx.scene.canvas.CanvasHelper;

public interface People {
    String getUsername();
    String getPassword();
    float getBalance();
    boolean getVoted();
    void vote(CanBeVoted canBeVoted);
    void pay(BankAccount bankAccount, float value);
    BankAccount getBankAccount();
}

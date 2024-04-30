package Voters;

import CanBeVoted.*;
import VotingRoom.*;

import java.io.*;

public abstract class Voters implements VotingProcess, Serializable, People{
    private String username;
    private String password;
    private boolean voted;
    private BankAccount bankAccount;

    public Voters(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Voters(String username, String password, BankAccount bankAccount) {
        this.username = username;
        this.password = password;
        this.bankAccount = bankAccount;
    }

    public void vote(CanBeVoted canBeVoted) {
        canBeVoted.addVote(1);
        voted();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean getVoted() {
        return voted;
    }

    public void voted() {
        this.voted = true;
    }

    public float getBalance() {
        return bankAccount.getBalance();
    }
    public void pay(BankAccount bankAccount, float value) {
        this.bankAccount.setBalance(-value);
        bankAccount.payment(value);
    }

    public BankAccount getBankAccount(){
        return bankAccount;
    }
}

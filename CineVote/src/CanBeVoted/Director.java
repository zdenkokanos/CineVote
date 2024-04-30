package CanBeVoted;

import Voters.Voters;
import VotingRoom.People;

import java.io.Serializable;

public class Director extends CanBeVoted implements Serializable, Payable, People {
    private String name;
    private String username;
    private String password;
    private int age;
    private boolean voted;

    private BankAccount bankAccount;

    public Director(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Director(String name, int age, BankAccount bankAccount) {
        this.name = name;
        this.age = age;
        this.bankAccount = bankAccount;
        this.username = name;
        this.password = "";
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public BankAccount getAccount() {
        return bankAccount;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public float getBalance() {
        return bankAccount.getBalance();
    }

    public boolean getVoted() {
        return voted;
    }

    public void vote(CanBeVoted canBeVoted) {
        canBeVoted.addVote(1);
        this.voted = true;
    }

    public void pay(BankAccount bankAccount, float value) {
        this.bankAccount.setBalance(-value);
        bankAccount.payment(value);
    }

    public BankAccount getBankAccount(){
        return bankAccount;
    }
}

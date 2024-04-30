package CanBeVoted;

import VotingRoom.People;

import java.io.Serializable;

//sety a gety do interface mozno dat vek registrovanym uzivatelom
//tiez by mohol hlasovat a mat meno a heslo
//moze byt aj iny interface pre kazdu hierarchou
public class Actor extends CanBeVoted implements Serializable, Payable, People {
    private String name;
    private int age;
    private String username;
    private String password;
    private BankAccount bankAccount;
    private boolean voted;

    public Actor(String name, int age) { //added after starting the voting, doesn't have an account
        this.name = name;
        this.age = age;

    }

    public Actor(String name, int age, BankAccount bankAccount) {
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

    public void vote(CanBeVoted canBeVoted){
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

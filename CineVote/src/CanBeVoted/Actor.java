package CanBeVoted;

import java.io.Serializable;

//sety a gety do interface mozno dat vek registrovanym uzivatelom
//tiez by mohol hlasovat a mat meno a heslo
//moze byt aj iny interface pre kazdu hierarchou
public class Actor extends CanBeVoted implements Serializable {
    private String name;
    private int age;
    private BankAccount bankAccount;

    public Actor(String name, int age) { //added after starting the voting, doesn't have an account
        this.name = name;
        this.age = age;

    }

    public Actor(String name, int age, BankAccount bankAccount){
        this.name = name;
        this.age = age;
        this.bankAccount = bankAccount;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

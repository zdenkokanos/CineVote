package CanBeVoted;

import java.io.Serializable;

public class Actor extends CanBeVoted implements Serializable {
    private String name;
    private int age;

    public Actor(String name, int age){
        this.name = name;
        this.age = age;

    }

    public String getName(){
        return name;
    }

    public int getAge(){
        return age;
    }
}

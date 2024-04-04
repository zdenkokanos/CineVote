package CanBeVoted;

import java.io.Serializable;

public class Director extends CanBeVoted implements Serializable {
    private String name;
    private int age;

    public Director(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}

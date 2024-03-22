package People;

import java.io.Serializable;

public class Actor implements Serializable {
    private String name;
    private int age;

    public Actor(String name, int age){
        this.name = name;
        this.age = age;

    }
}

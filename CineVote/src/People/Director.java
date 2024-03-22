package People;


import java.io.Serializable;

public class Director implements Serializable {
    private String name;
    private int age;

    public Director(String name, int age){
        this.name = name;
        this.age = age;
    }
}

package me.programmers.java.collection;

public class User {
    private int age;
    private String name;

    public User(int age, String name) {
        this.age = age;
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public boolean isOver19() {
        return age >= 19;
    }

    @Override
    public String toString() {
        return name + " " + age;
    }
}

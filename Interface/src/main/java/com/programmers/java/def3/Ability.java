package com.programmers.java.def3;

public interface Ability {
    static void sayHello() {
        System.out.println("hello world");
    }
}

interface Flyable {
    default void fly() {
        System.out.println("FLY");
    }
}

interface Swimmable {
    default void swim() {
        System.out.println("SWIM");
    }
}

interface Walkable {
    default void walk() {
        System.out.println("WALK");
    }
}
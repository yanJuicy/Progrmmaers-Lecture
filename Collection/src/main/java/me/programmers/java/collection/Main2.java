package me.programmers.java.collection;

import java.util.Arrays;

public class Main2 {

    public static void main(String[] args) {
        new MyCollection<User>(
                Arrays.asList(
                        new User(10, "AAA"),
                        new User(10, "AAA"),
                        new User(10, "AAA"),
                        new User(10, "AAA"),
                        new User(10, "AAA"),
                        new User(19, "AAA"),
                        new User(20, "AAb"),
                        new User(20, "AAc"),
                        new User(20, "AAd"),
                        new User(20, "AAe")
                        )
        )
                .filter(User::isOver19)
                .foreach(System.out::println);
    }
}

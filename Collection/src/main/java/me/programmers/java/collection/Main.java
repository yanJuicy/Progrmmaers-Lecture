package me.programmers.java.collection;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int s = new MyCollection<>(Arrays.asList("A", "BD", "CXX", "DDSA"))
                .map(String::length)
                .filter(i -> i % 2 == 0)
                .size();

        System.out.println(s);
    }
}

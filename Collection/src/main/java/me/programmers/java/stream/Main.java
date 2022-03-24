package me.programmers.java.stream;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        Arrays.asList("A", "AB", "ABC", "ABCD", "ABCDE")
                .stream()
                .map(s -> s.length())
                .filter(i -> i % 2 == 0)
                .forEach(System.out::println);
    }
}

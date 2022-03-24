package me.programmers.java.stream;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main2 {

    public static void main(String[] args) {
        Stream<Integer> s = Arrays.asList(1, 2, 3).stream();
        Arrays.stream(new int[]{1, 2, 3}).boxed().toArray(Integer[]::new);

//        Random r = new Random();
//        Stream.generate(() -> r.nextInt())
//                .forEach(System.out::println);

        Stream.iterate(0, (i) -> i + 1)
                .limit(10)
                .forEach(System.out::println);
    }
}

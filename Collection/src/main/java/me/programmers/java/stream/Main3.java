package me.programmers.java.stream;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Stream;

public class Main3 {

    public static void main(String[] args) {
//        Random r = new Random();
//        int[] arr = Stream.generate(() -> r.nextInt(10) + 1)
//                .distinct()
//                .limit(3)
//                .mapToInt(i -> i)
//                .toArray();
//
//        System.out.println(Arrays.toString(arr));

        Random r = new Random();
        int[] arr = Stream.generate(() -> r.nextInt(100) + 1)
                .limit(5)
                .sorted(Comparator.reverseOrder())
                .mapToInt(i -> i)
                .toArray();

        System.out.println(Arrays.toString(arr));
    }
}

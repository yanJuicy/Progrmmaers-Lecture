package com.programmers.java.lambda;

public class Main {
    public static void main(String[] args) {

        MySupplier<String> s = () -> "Hello World";

//        MyMapper m = str -> str.length();
        MyMapper<String, Integer> m = String::length;
        MyMapper<Integer, Integer> m2 = i -> i * i;
        MyMapper<Integer, String> m3 = Integer::toHexString;

//        MyConsumer c = i -> System.out.println(i);
        MyConsumer<Integer> c = System.out::println;
        MyConsumer<String> c2 = System.out::println;


        c2.consume(
                m3.map(
                    m2.map(
                        m.map(
                                s.supply()
                        )
                    )
                )
        );


        MyRunnable r = () -> c.consume(m.map(s.supply()));
        r.run();
    }
}

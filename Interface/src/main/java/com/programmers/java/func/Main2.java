package com.programmers.java.func;

public class Main2 {
    public static void main(String[] args) {
        MyRunnable r1 = new MyRunnable() {
            @Override
            public void run() {
                System.out.println("Hello");
            }
        };
        r1.run();

        // 익명 메소드를 사용해서 표현하는 방법 : 람다 표현식
        MyRunnable r2 = () -> System.out.println("Hello World");
        r2.run();

        MySupply s1 = () -> "Hello World";
        System.out.println(s1.supply());
    }
}

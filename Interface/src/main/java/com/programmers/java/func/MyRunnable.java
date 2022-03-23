package com.programmers.java.func;

@FunctionalInterface
public interface MyRunnable {
    void run(); // 추상 메소드가 하나 밖에 없는 메소드 -> 함수형 인터페이스
}

interface MyRunnable2 {
    void run1();
    void run2();
}

@FunctionalInterface
interface MyMap {
    void map();
    default void sayHello() {
        System.out.println("Hello World");
    }
    static void sayBye() {
        System.out.println("Bye World");
    }
}
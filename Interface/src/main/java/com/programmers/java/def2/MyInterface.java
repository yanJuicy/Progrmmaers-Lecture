package com.programmers.java.def2;

public interface MyInterface {
    void method1();
    default void method2() {
        System.out.println("Bye hello");
    }
}

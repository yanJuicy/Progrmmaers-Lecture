package com.programmers.java.func;

public class Main {
    public static void main(String[] args) {
        // 이름없는 클래스 생성 -> 익명 클래스
        new MySupply() {
            @Override
            public String supply() {
                return "Hello World";
            }
        }.supply();

        MyRunnable r = new MyRunnable() {

            @Override
            public void run() {
                MySupply s = new MySupply() {
                    @Override
                    public String supply() {
                        return "Hello Hello";
                    }
                };
                System.out.println(s.supply());
            }
        };

        r.run();
    }
}

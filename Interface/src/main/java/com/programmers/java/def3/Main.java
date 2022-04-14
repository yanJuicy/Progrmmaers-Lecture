package com.programmers.java.def3;

import javax.swing.plaf.basic.BasicToolBarUI;

public class Main {

    public static void main(String[] args) {
        new Duck().walk();
        new Swan().walk();
        new Swan().swim();
        Ability.sayHello();
    }
}

class Duck implements Swimmable, Walkable{

}

class Swan implements Flyable, Walkable, Swimmable {

}
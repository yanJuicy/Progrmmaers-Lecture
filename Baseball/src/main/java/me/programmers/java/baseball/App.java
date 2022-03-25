package me.programmers.java.baseball;

import me.programmers.java.baseball.engine.Baseball;
import me.programmers.java.baseball.engine.io.NumberGenerator;

public class App {

    public static void main(String[] args) {
        NumberGenerator generator = new HackFakerNumberGenerator();
        Console console = new Console();

        new Baseball(generator, console, console).run();
    }
}

package me.programmers.java.baseball;

import com.github.javafaker.Faker;
import me.programmers.java.baseball.engine.io.NumberGenerator;
import me.programmers.java.baseball.engine.model.Numbers;

import java.util.stream.Stream;

public class HackFakerNumberGenerator implements NumberGenerator {

    private final Faker faker = new Faker();

    @Override
    public Numbers generate(int count) {
        Numbers num = new Numbers(Stream.generate(() -> faker.number().randomDigitNotZero())
                .distinct()
                .limit(count)
                .toArray(Integer[]::new));
        System.out.println(num);
        return num;
    }
}

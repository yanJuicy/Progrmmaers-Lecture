package me.programmers.java.optional;

import me.programmers.java.collection.User;

import java.util.Optional;

public class Main2 {

    public static void main(String[] args) {

        Optional<User> optionalUser = Optional.empty();
        optionalUser = Optional.of(new User(1, "2"));

        optionalUser.isEmpty();     // 값이 없으면 true
        optionalUser.isPresent();   // 값이 있으면 true

        if (optionalUser.isPresent()) {

        } else {

        }

        if (optionalUser.isEmpty()) {

        } else {

        }

        optionalUser.ifPresent(user -> {
            // do 1
        });

        optionalUser.ifPresentOrElse(user -> {
            // do 1
        }, () -> {
            // else do
        });
    }
}

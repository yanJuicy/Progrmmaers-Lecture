package me.programmers.java.baseball.engine.io;

import me.programmers.java.baseball.engine.model.BallCount;

public interface Output {
    void ballCount(BallCount bc);

    void inputError();

    void correct();
}

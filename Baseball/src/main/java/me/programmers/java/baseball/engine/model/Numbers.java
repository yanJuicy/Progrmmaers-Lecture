package me.programmers.java.baseball.engine.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.function.BiConsumer;

@AllArgsConstructor
@ToString
public class Numbers {
    Integer[] nums;

    public void indexedForEach(BiConsumer<Integer, Integer> consumer) {
        for (int i=0; i<nums.length; i++) {
            consumer.accept(nums[i], i);
        }
    }
}

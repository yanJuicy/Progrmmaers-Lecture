package org.prgms.kdt.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class HamcrestAssertionsTest {

    @Test
    @DisplayName("여러 hamcrest matcher 테스트")
    void hamcrestTest() {
        assertThat(1 + 1, equalTo(2));
        assertThat(1 + 1, is(2));
        assertThat(1 + 1, anyOf(is(2), is(3)));

    }

    @Test
    @DisplayName("컬렉션에 대한 matcher 테스트")
    void hamcrestListMatcherTest() {
        var prices = List.of(1, 2, 3);
        assertThat(prices, hasSize(3));
        assertThat(prices, everyItem(greaterThan(0)));
    }

}

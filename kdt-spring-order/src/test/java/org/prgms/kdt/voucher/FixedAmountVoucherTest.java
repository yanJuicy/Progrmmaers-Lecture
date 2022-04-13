package org.prgms.kdt.voucher;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class FixedAmountVoucherTest {

    @BeforeAll
    static void setup() {
        System.out.println("@BeforeAll - run once");
    }

    @BeforeEach
    void init() {
        System.out.println("@BeforeEach - run before each test method");
    }

    @Test
    @DisplayName("기본적인 assertEquals 테스트")
    void testAssertEquals() {
        assertEquals(2, 1 + 1);
    }

    @Test
    @DisplayName("주어진 금액만큼 할인")
    void testDiscount() {
        var sut = new FixedAmountVoucher(100, UUID.randomUUID());
        assertEquals(900, sut.discount(1000));
    }

    @Test
    @DisplayName("할인된 금액은 마이너스가 될 수 없다.")
    void testMinusDiscountedAmount() {
        var sut = new FixedAmountVoucher(1000, UUID.randomUUID());
        assertEquals(0, sut.discount(900));
    }

    @Test
    @DisplayName("할인 금액은 마이너스가 될 수 없다")
    void testWithMinus() {
        assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(-100, UUID.randomUUID()));
    }

    @Test
    @DisplayName("유효한 할인 금액으로만 생성할 수 있다.")
    void testVoucherCreation() {
        assertAll("FixedAmountVoucher creation",
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(0, UUID.randomUUID())),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(-100, UUID.randomUUID())),
                () -> assertThrows(IllegalArgumentException.class, () -> new FixedAmountVoucher(10000000, UUID.randomUUID()))
        );
    }
}
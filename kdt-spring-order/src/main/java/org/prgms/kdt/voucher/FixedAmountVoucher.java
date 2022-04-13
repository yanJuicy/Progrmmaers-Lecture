package org.prgms.kdt.voucher;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private static final long MAX_AMOUNT = 100000;

    private final long amount;
    private final UUID voucherId;

    public FixedAmountVoucher(long amount, UUID voucherId) {
        if (amount < 0) {
            throw new IllegalArgumentException("음수 값으로 할인 할 수 없습니다.");
        }
        if (amount == 0) {
            throw new IllegalArgumentException("0으로 할인 할 수 없습니다.");
        }
        if (amount > MAX_AMOUNT) {
            throw new IllegalArgumentException("입력 한도 초과");
        }


        this.amount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        var discountedAmount = beforeDiscount - amount;
        return discountedAmount < 0 ? 0 : discountedAmount;
    }
}

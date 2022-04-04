package org.prgms.kdt;

import java.util.UUID;

public class FixedAmountVoucher implements Voucher {

    private final long amount;
    private final UUID voucherId;

    public FixedAmountVoucher(long amount, UUID voucherId) {
        this.amount = amount;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount - amount;
    }
}

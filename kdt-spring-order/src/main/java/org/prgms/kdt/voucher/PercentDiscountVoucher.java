package org.prgms.kdt.voucher;

import org.prgms.kdt.voucher.Voucher;

import java.util.UUID;

public class PercentDiscountVoucher implements Voucher {

    private final long percent;
    private final UUID voucherId;

    public PercentDiscountVoucher(long percent, UUID voucherId) {
        this.percent = percent;
        this.voucherId = voucherId;
    }

    @Override
    public UUID getVoucherId() {
        return voucherId;
    }

    @Override
    public long discount(long beforeDiscount) {
        return beforeDiscount * (percent / 100);
    }
}

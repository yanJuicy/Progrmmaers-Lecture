package org.prgms.kdt.aop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.kdt.voucher.FixedAmountVoucher;
import org.prgms.kdt.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.UUID;

@SpringJUnitConfig
@ActiveProfiles("test")
public class AopTest {

    @Configuration
    @ComponentScan(
            basePackages = {"org.prgms.kdt.voucher", "org.prgms.kdt.aop"}
    )
    @EnableAspectJAutoProxy
    static class Config {}

    @Autowired
    VoucherRepository voucherRepository;

    @Test
    @DisplayName("aop test")
    void testOrderSevice() {
        var fixedAmountVoucher = new FixedAmountVoucher(100, UUID.randomUUID());
        voucherRepository.insert(fixedAmountVoucher);
    }

}

package org.prgms.kdt;

import org.prgms.kdt.order.OrderItem;
import org.prgms.kdt.order.OrderProperties;
import org.prgms.kdt.order.OrderService;
import org.prgms.kdt.voucher.FixedAmountVoucher;
import org.prgms.kdt.voucher.JdbcVoucherRepository;
import org.prgms.kdt.voucher.VoucherRepository;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

    public static void main(String[] args) {

        var context = new AnnotationConfigApplicationContext();
        context.register(AppConfiguration.class);
        var environment = context.getEnvironment();
        environment.setActiveProfiles("local");
        context.refresh();

//        var version = environment.getProperty("kdt.version");
//        var minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
//        var supportVendors = environment.getProperty("kdt.support-vendors", List.class);
//        var description = environment.getProperty("description");
//
//        System.out.println("version = " + version);
//        System.out.println("minimumOrderAmount = " + minimumOrderAmount);
//        System.out.println("supportVendors = " + supportVendors);
//        System.out.println("description = " + description);

        var orderProperties = context.getBean(OrderProperties.class);
        System.out.println("orderProperties.getDescription() = " + orderProperties.getDescription());
        System.out.println("orderProperties.getVersion() = " + orderProperties.getVersion());
        System.out.println("orderProperties.getSupportVendors() = " + orderProperties.getSupportVendors());
        System.out.println("orderProperties.getMinimumOrderAmount() = " + orderProperties.getMinimumOrderAmount());

        var customerId = UUID.randomUUID();
        var voucherRepository = context.getBean(VoucherRepository.class);
        var voucher = voucherRepository.insert(new FixedAmountVoucher(10L, UUID.randomUUID()));

        System.out.println("is JDBC = " + (voucherRepository instanceof JdbcVoucherRepository));
        System.out.println("voucherRepository = " + voucherRepository.getClass().getName());

        var orderService = context.getBean(OrderService.class);
        var order= orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAmount: {0} is not 90L", order.totalAmount()));
    }
}

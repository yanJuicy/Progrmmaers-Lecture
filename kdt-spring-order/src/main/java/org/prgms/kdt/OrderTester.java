package org.prgms.kdt;

import org.prgms.kdt.order.OrderItem;
import org.prgms.kdt.order.OrderProperties;
import org.prgms.kdt.order.OrderService;
import org.prgms.kdt.voucher.FixedAmountVoucher;
import org.prgms.kdt.voucher.JdbcVoucherRepository;
import org.prgms.kdt.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

public class OrderTester {

    private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

    public static void main(String[] args) throws IOException {

        AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);

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
        logger.error("logger name => {} ", logger.getName());
        logger.warn("version {}", orderProperties.getVersion());
        logger.warn("supportVendors {} ", orderProperties.getSupportVendors());
        logger.warn("minimumOrder {} ", orderProperties.getMinimumOrderAmount());
        logger.warn("description {} ", orderProperties.getDescription());

        // Resource 가져오기
        var resource = context.getResource("application.yaml");
        var file = resource.getFile();
        var strings = Files.readAllLines(file.toPath());
        System.out.println("Strings = " + strings.stream().reduce("", (a, b) -> a + "\n" + b));

        var resource2 = context.getResource("file:sample.txt");
        strings = Files.readAllLines(resource2.getFile().toPath());
        System.out.println("Strings = " + strings.stream().reduce("", (a, b) -> a + "\n" + b));

        var resource3 = context.getResource("https://stackoverflow.com");
        var readableByteChannel = Channels.newChannel(resource3.getURL().openStream());
        var bufferedReader = new BufferedReader(Channels.newReader(readableByteChannel, StandardCharsets.UTF_8));
        var lines = bufferedReader.lines();
        var contents = lines.collect(Collectors.joining("\n"));
        System.out.println(contents);
        System.out.println("resource = " + resource3.getClass().getCanonicalName());

        var customerId = UUID.randomUUID();
        var voucherRepository = context.getBean(VoucherRepository.class);
        var voucher = voucherRepository.insert(new FixedAmountVoucher(10L, UUID.randomUUID()));

        System.out.println("is JDBC = " + (voucherRepository instanceof JdbcVoucherRepository));
        System.out.println("voucherRepository = " + voucherRepository.getClass().getName());

        var orderService = context.getBean(OrderService.class);
        var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
            add(new OrderItem(UUID.randomUUID(), 100L, 1));
        }}, voucher.getVoucherId());

        Assert.isTrue(order.totalAmount() == 90L,
                MessageFormat.format("totalAmount: {0} is not 90L", order.totalAmount()));
    }
}

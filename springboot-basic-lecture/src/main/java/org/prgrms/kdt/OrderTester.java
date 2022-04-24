package org.prgrms.kdt;

import org.prgrms.kdt.order.OrderItem;
import org.prgrms.kdt.order.OrderProperties;
import org.prgrms.kdt.order.OrderService;
import org.prgrms.kdt.voucher.FixedAmountVoucher;
import org.prgrms.kdt.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.UUID;

public class OrderTester {

  private static final Logger logger = LoggerFactory.getLogger(OrderTester.class);

  public static void main(String[] args) throws IOException {
    AnsiOutput.setEnabled(AnsiOutput.Enabled.ALWAYS);
    var applicationContext = new AnnotationConfigApplicationContext(AppConfiguration.class);

//    var environment = applicationContext.getEnvironment();
//    var version = environment.getProperty("kdt.version");
//    var minimumOrderAmount = environment.getProperty("kdt.minimum-order-amount", Integer.class);
//    var supportVendors = environment.getProperty("kdt.support-vendors", List.class);
//    var description = environment.getProperty("kdt.description", List.class);
//    System.out.println(MessageFormat.format("version -> {0}", version));
//    System.out.println(MessageFormat.format("minimumOrderAmount -> {0}", minimumOrderAmount));
//    System.out.println(MessageFormat.format("supportVendors -> {0}", supportVendors));
//    System.out.println(MessageFormat.format("description -> {0}", description));
    var orderProperties = applicationContext.getBean(OrderProperties.class);
    logger.error("logger name => {}", logger.getName());
    logger.warn("version -> {}", orderProperties.getVersion());
    logger.warn("minimumOrderAmount -> {}", orderProperties.getMinimumOrderAmount());
    logger.warn("supportVendors -> {}", orderProperties.getSupportVendors());
    logger.warn("description -> {}", orderProperties.getDescription());

    var customerId = UUID.randomUUID();
    var voucherRepository = BeanFactoryAnnotationUtils.qualifiedBeanOfType(applicationContext.getBeanFactory(), VoucherRepository.class, "memory");
    var voucher = voucherRepository.insert(new FixedAmountVoucher(UUID.randomUUID(), 10L));

    var orderService = applicationContext.getBean(OrderService.class);
    var order = orderService.createOrder(customerId, new ArrayList<OrderItem>() {{
      add(new OrderItem(UUID.randomUUID(), 100L, 1));
    }}, voucher.getVoucherId());

    Assert.isTrue(order.totalAmount() == 90L, MessageFormat.format("totalAmount {0} is not 90L", order.totalAmount()));

    applicationContext.close();
  }
}

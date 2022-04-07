package org.prgms.kdt;

import org.prgms.kdt.voucher.FixedAmountVoucher;
import org.prgms.kdt.voucher.JdbcVoucherRepository;
import org.prgms.kdt.voucher.VoucherRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.UUID;

@SpringBootApplication
@ComponentScan(
		basePackages = {"org.prgms.kdt.voucher", "org.prgms.kdt.order", "org.prgms.kdt.configuration"}
)
public class DemoApplication {

	public static void main(String[] args) {
		var springApplication = new SpringApplication(DemoApplication.class);
		springApplication.setAdditionalProfiles("dev");
		var applicationContext = springApplication.run(args);

//		var orderProperties = applicationContext.getBean(OrderProperties.class);
//		System.out.println("orderProperties.getDescription() = " + orderProperties.getDescription());
//		System.out.println("orderProperties.getVersion() = " + orderProperties.getVersion());
//		System.out.println("orderProperties.getSupportVendors() = " + orderProperties.getSupportVendors());
//		System.out.println("orderProperties.getMinimumOrderAmount() = " + orderProperties.getMinimumOrderAmount());


		var customerId = UUID.randomUUID();
		var voucherRepository = applicationContext.getBean(VoucherRepository.class);
		var voucher = voucherRepository.insert(new FixedAmountVoucher(10L, UUID.randomUUID()));

		System.out.println("is JDBC = " + (voucherRepository instanceof JdbcVoucherRepository));
		System.out.println("voucherRepository = " + voucherRepository.getClass().getName());
	}
}

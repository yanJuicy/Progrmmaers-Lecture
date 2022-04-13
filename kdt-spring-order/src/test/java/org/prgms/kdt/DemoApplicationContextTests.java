package org.prgms.kdt;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgms.kdt.order.OrderItem;
import org.prgms.kdt.order.OrderService;
import org.prgms.kdt.order.OrderStatus;
import org.prgms.kdt.voucher.FixedAmountVoucher;
import org.prgms.kdt.voucher.VoucherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration
@SpringJUnitConfig
@ActiveProfiles("test")
class DemoApplicationContextTests {

	@Configuration
	@ComponentScan(
			basePackages = {
					"org.prgms.kdt.voucher",
					"org.prgms.kdt.order"
			}
	)
	static class Config {
	}

	@Autowired
	OrderService orderService;

	@Autowired
	ApplicationContext context;

	@Autowired
	VoucherRepository voucherRepository;

	@Test
	@DisplayName("applicaionContext가 생성되야함")
	void testApplicationContext() {
		assertThat(context, notNullValue());
	}

	@Test
	@DisplayName("ucherRepository가 빈으로 등록되야함")
	void testVoucherRepositoryCreation() {
		VoucherRepository bean = context.getBean(VoucherRepository.class);
		assertThat(bean, notNullValue());
	}

	@Test
	@DisplayName("orderService를 사용해서 주문을 생성")
	void testOrderService() {
		// given
		var fixedAmountVoucher = new FixedAmountVoucher(100, UUID.randomUUID());
		voucherRepository.insert(fixedAmountVoucher);

		// when
		var order = orderService.createOrder(
				UUID.randomUUID(),
				List.of(new OrderItem(UUID.randomUUID(), 200, 1)),
				fixedAmountVoucher.getVoucherId());

		// then
		assertThat(order.totalAmount(), is(100L));
		assertThat(order.getVoucher().isEmpty(), is(false));
		assertThat(order.getVoucher().get().getVoucherId(), is(fixedAmountVoucher.getVoucherId()));
		assertThat(order.getOrderStatus(), is(OrderStatus.ACCEPTED));
	}

}

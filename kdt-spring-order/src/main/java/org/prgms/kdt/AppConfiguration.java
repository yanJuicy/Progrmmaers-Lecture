package org.prgms.kdt;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"org.prgms.kdt.voucher", "org.prgms.kdt.order"})
//@ComponentScan(excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = )})
public class AppConfiguration {

}

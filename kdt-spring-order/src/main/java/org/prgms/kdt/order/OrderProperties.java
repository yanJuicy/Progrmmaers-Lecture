package org.prgms.kdt.order;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderProperties implements InitializingBean {

    @Value("${kdt.version:v1.0.1}")
    private String version;

    @Value("${kdt.minimum-order-amount}")
    private int minimumOrderAmount;

    @Value("${kdt.support-vendors}")
    private List<String> supportVendors;

    @Value("${JAVA_HOME}")
    private String javaHome;

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("version = " + version);
        System.out.println("minimumOrderAmount = " + minimumOrderAmount);
        System.out.println("supportVendors = " + supportVendors);
        System.out.println("javaHome = " + javaHome);
    }
}

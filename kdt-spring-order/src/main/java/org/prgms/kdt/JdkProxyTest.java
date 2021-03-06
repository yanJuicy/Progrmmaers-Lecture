package org.prgms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class CalcualtorImpl implements Calculator {

    @Override
    public int add(int a, int b) {
        return a + b;
    }
}

interface Calculator {
    int add(int a, int b);
}

class LoggingInvocationHandler implements InvocationHandler {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInvocationHandler.class);

    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        logger.info("{} method executed in {}", method.getName(), target.getClass().getCanonicalName());
        return method.invoke(target, args);
    }
}

public class JdkProxyTest {

    private static final Logger logger = LoggerFactory.getLogger(JdkProxyTest.class);

    public static void main(String[] args) {
        var calculator = new CalcualtorImpl();
        Calculator proxyInstance = (Calculator) Proxy.newProxyInstance(
                LoggingInvocationHandler.class.getClassLoader(),
                new Class[]{Calculator.class},
                new LoggingInvocationHandler(calculator));

        var add = proxyInstance.add(1, 2);
        logger.info("Add -> {}", add);

    }
}

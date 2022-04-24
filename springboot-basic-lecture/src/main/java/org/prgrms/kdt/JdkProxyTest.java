package org.prgrms.kdt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class CalculatorImpl implements Calcuator {

  @Override
  public int add(int a, int b) {
    return a + b;
  }
}

interface Calcuator {
  int add(int a, int b);
}

class LoggingInvocationHandler implements InvocationHandler {

  private static final Logger log = LoggerFactory.getLogger(LoggingInvocationHandler.class);

  private final Object target;

  public LoggingInvocationHandler(Object target) {
    this.target = target;
  }

  @Override
  public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
    log.info("{} executed in {}", method.getName(), target.getClass().getCanonicalName());
    return method.invoke(target, args);
  }

}


public class JdkProxyTest {
  private static final Logger log = LoggerFactory.getLogger(JdkProxyTest.class);
  public static void main(String[] args) {
    var calculator = new CalculatorImpl();
    Calcuator proxyInstance = (Calcuator) Proxy.newProxyInstance(
      LoggingInvocationHandler.class.getClassLoader(),
      new Class[]{Calcuator.class},
      new LoggingInvocationHandler(calculator));
    var result = proxyInstance.add(1, 2);
    log.info("Add -> {}", result);
  }

}

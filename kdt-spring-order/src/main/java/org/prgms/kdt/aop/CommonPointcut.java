package org.prgms.kdt.aop;

import org.aspectj.lang.annotation.Pointcut;

public class CommonPointcut {

    @Pointcut("execution(public * org.prgms.kdt..*Service.*(..))")
    public void servicePublicMethodPointCut() {}

    @Pointcut("execution(public * org.prgms.kdt..*Repository.*(..))")
    public void repositoryPublicMethodPointCut() {}

    @Pointcut("execution(public * org.prgms.kdt..*Service.insert(..))")
    public void repositoryInsertMethodPointCut() {}
}

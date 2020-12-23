package com.arlenchen.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component //组件注解
public class ServiceLogAspect {
    public static final Logger logger = LoggerFactory.getLogger(ServiceLogAspect.class);

    /*
     *  AOP通知
     *  1.前置通知：在方法调用前执行
     *  2.后置通知：在方法调用后执行(出现异常时，不执行)
     *  3.环绕通知：在方法调用之前和之后，都分别可以执行的通知
     *  4.异常通知：如果方法调用过程中发生异常了，则通知
     *  5.最终通知：在方法调用之后执行(如果执行中发生异常，可在最终通知中执行)
     */

    /**
     * 切面表达式
     * execution 代表所要执行的表达式主体
     * 第一处 * 代表方法返回类型 *表示所有类型
     * 第二处 包名代表aop监控的类所在的包
     * 第三处 .. 代表该包下以及子包下的所有类方法
     * 第四处 * 代表类名，*表示所有类
     * 第五处 *（..) *代表类中的方法名，(..)表示方法中的任何参数
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("execution(* com.arlenchen.service.impl..*.*(..))")
    public Object recordTimeLog(ProceedingJoinPoint joinPoint) throws  Throwable {
        logger.info("===== 开始执行 {}～～{} =====", joinPoint.getTarget().getClass(), joinPoint.getSignature().getName());
        long beginTime=System.currentTimeMillis();
        Object result=joinPoint.proceed();
        long endTime=System.currentTimeMillis();
        long takeTime=endTime-beginTime;
        if(takeTime>3000){
            logger.error("===== error == 结束执行,耗时 {}毫秒 =====",takeTime);
        }else if(takeTime>2000){
            logger.warn("===== warn == 结束执行,耗时 {}毫秒 =====",takeTime);
        }else{
            logger.info("===== info == 结束执行,耗时 {}毫秒 =====",takeTime);
        }
        return  result;
    }
}

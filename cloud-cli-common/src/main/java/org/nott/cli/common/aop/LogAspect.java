package org.nott.cli.common.aop;

import com.alibaba.fastjson.JSON;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Nott
 * @date 2024-4-18
 */
@Aspect
@Component
public class LogAspect {

    private final Logger log = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 切点，包裹controller
     * execution public：public类型的方法
     * * 所有的返回类型
     * 包名
     * * 所有类
     * *(..) 表示所有的方法名，两个点表示任何入参
     */
    @Pointcut("execution(public * org.nott.cli.*.controller..*.*(..))")
    public void privilege() {}

    /**
     * 环绕通知
     *
     * @param pjd
     * @return
     * @throws Throwable
     */
    @Around("privilege()")
    public Object arount(ProceedingJoinPoint pjd) throws Throwable {
        long startTime = System.currentTimeMillis();
        // 类名
        String className = pjd.getTarget().getClass().getName();
        // 获取执行的方法名称
        String methodName = pjd.getSignature().getName();

        Object[] args = pjd.getArgs();
        try {
            String params = JSON.toJSONString(args[0]);
            log.info("{}.{}()[Method Param]：{}", className, methodName, params);
        } catch (Exception e) {
            log.info("{}.{}()[Method Param Print Error]：{}", className, methodName, e);
        }
        // 执行目标方法
        Object result = pjd.proceed();
        // 打印返回结果
        try {
            String s = JSON.toJSONString(result);
            log.info("{}.{}()[Method Result]：{}", className, methodName, s);
        } catch (Exception e) {
            log.info("{}.{}()[Method Result Print Error]：{}", className, methodName, e);
        }
        // 获取执行完的时间
        long time = System.currentTimeMillis() - startTime;
        log.info("{}.{}()[Method Executed]：{}{}", className, methodName, time, " ms");
        return result;
    }

}

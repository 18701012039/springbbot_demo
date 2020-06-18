package com.zxb.service.aop;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 日志打印
 */
@Aspect
@Component
@Order(1)
@Slf4j
public class LogAop {

    private final static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * 拦截所有的controller
     */
    @Pointcut("execution(public * com.zxb.service.controller..*.*(..))")
    public void pointcut(){}

    @Around("pointcut()")
    public Object doLog(ProceedingJoinPoint proceedingJoinPoint){
        Signature signature = proceedingJoinPoint.getSignature();
        //方法名
        String methodName = signature.getName();
        Class<?> aClass = proceedingJoinPoint.getTarget().getClass();
        String name = aClass.getName();
        log.info("调用的方法名{}",name+"."+methodName);
        long startTime=System.currentTimeMillis();
        log.info("开始执行时间{}",simpleDateFormat.format(new Date()));
        JSONObject object=new JSONObject();
        Object proceed =null;
        try {
            proceed=proceedingJoinPoint.proceed();
            if(proceed instanceof java.util.List){
                List<?> list=(List<?>)proceed;
                if (!CollectionUtils.isEmpty(list)) {
                    list.forEach(item-> log.info(item.toString()));
                }
                log.info("json对象{}", JSONObject.toJSON(list));
            }else{
                log.info(proceed.toString());
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        log.info("执行结束时间{}",simpleDateFormat.format(new Date()));
        long endTime=System.currentTimeMillis();
        log.info("执行时间是{}秒",endTime-startTime);
        return proceed;
    }
}

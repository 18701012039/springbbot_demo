package com.zxb.service.aop;


import com.alibaba.fastjson.JSONObject;
import com.zxb.utils.LogUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
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

    /**
     * @param proceedingJoinPoint
     * @return
     */
//    @Around("pointcut()")
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

    /**
     * aop+反射实现日志
     * @param proceedingJoinPoint
     * @return
     * @throws NoSuchMethodException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    @Around("pointcut()")
    public Object doLogTwo(ProceedingJoinPoint proceedingJoinPoint) throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Object aClass = proceedingJoinPoint.getTarget();
        String name = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        return LogUtils.addLog(aClass,name,args);
    }


}

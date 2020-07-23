package com.zxb.utils;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 *
 * @author admin
 * @create 2020/7/22
 * @since 1.0.0
 */
@Slf4j
public final class LogUtils{


    private LogUtils(){}

    /**
     * 记录日志
     * @param className
     * @param methodName
     * @param filedArr
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    public static Object addLog(Object className,String methodName,Object... filedArr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        long startTime=System.currentTimeMillis();
        Object invoke=null;
        Class<?> aClass = className.getClass();
        if (filedArr.length==0){
            Method declaredMethod = aClass.getDeclaredMethod(methodName);
            declaredMethod.setAccessible(true);
            invoke=declaredMethod.invoke(className);
        }else{
            Class<?>[] parameterArr=new Class[filedArr.length];
            for (int i = 0; i < filedArr.length; i++) {
                parameterArr[i]=filedArr[i].getClass();
            }
            Method declaredMethod = aClass.getDeclaredMethod(methodName, parameterArr);
            declaredMethod.setAccessible(true);
            invoke=declaredMethod.invoke(className, parameterArr);
        }
        log.info("执行的方法名"+methodName);
        log.info("执行的方法"+aClass.getName()+"."+methodName);
        long endTime=System.currentTimeMillis();
        log.info("执行时间{}",(endTime-startTime));
        log.info("返回的数据{}",invoke);
        return invoke;
    }
}

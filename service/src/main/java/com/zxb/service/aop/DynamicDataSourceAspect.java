package com.zxb.service.aop;

import com.zxb.anno.DataSourceAnn;
import com.zxb.api.DBChangeService;
import com.zxb.init.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * aop来实现切换数据源
 * @author zxb
 * @create 2020/7/16
 * @since 1.0.0
 */
@Component
@Aspect
@Slf4j
public class DynamicDataSourceAspect {

    @Resource
    private DBChangeService dbChangeService;

    @Pointcut("@annotation(com.zxb.anno.DataSourceAnn)")
    public void dataSourcePointcut(){}

    @Around("dataSourcePointcut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        DataSourceAnn ds = null;
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        //获取自定义注解
        ds = method.getAnnotation(DataSourceAnn.class);
        if (ds !=null) {
            //自定义存在,则按照注解的值去切换数据源
            DataSourceContextHolder.setDataSource(ds.value());
            //切换数据源
            dbChangeService.changeDb(ds.value());
            log.info("set datasource is " + ds.value());
            //切换数据源
        }
        return point.proceed();
    }

    /**
     * 切换数据源之前清楚数据源
     * @param point
     */
    @After(value = "dataSourcePointcut()")
    public void afterSwitchDS(JoinPoint point) {
        DataSourceContextHolder.clearDataSource();
        log.info("clean datasource");
    }
}

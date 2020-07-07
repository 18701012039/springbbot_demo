package com.zxb.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


 /**
 *  在上下文加载完创建一个线程池 就是项目启动完
 * @author zxb
 * @create 2020/4/25
 * @since 1.0.0
 */
@Component
@Slf4j
public class SyncExecutor implements ApplicationListener<ApplicationReadyEvent> {
    private ExecutorService executorService;

    public ExecutorService getExecutorService(){
        return executorService;
    }
    //int corePoolSize, 核心线程数
    //int maximumPoolSize,最大线程数
    //long keepAliveTime,非核心线程的存活时间
    //TimeUnit unit, 单位 与上面那个存活时间配套
    //BlockingQueue<Runnable> workQueue 队列  分为有界队列和无界队列
    //RejectedExecutionHandler handler 拒绝策略 4种拒绝策略
    //首先提交任务到线程池中先启用核心线程数===>如果核心线城市满了将任务提交到队列中===>队列满了启用临时队列（最大线程数-核心线程数）
    //如果最大线程数以及队列都满了就开启策略模式
    //线程池状态
    //        running 能接受新任务 以及处理已经添加的任务
    //        shutdown 不能接受新任务 可以处理已经添加的任务
    //        stop 不接受新任务 不处理已经添加的任务 并且中断正在处理的任务
    //        tidying 所有的任务已经终止 ctl记录的任务为0
    //        terminated 线程池状态彻底终止
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("项目启动完创建一个线程池 {} {}","是","朱小兵创建");
        if(executorService==null){
            int cpuCount = Runtime.getRuntime().availableProcessors();
            executorService = new ThreadPoolExecutor(cpuCount, cpuCount, 30, TimeUnit.SECONDS,
                    new LinkedBlockingDeque<>(), (r) -> new Thread(r, "sync-data-thread"),
                    //队列满后 抛一个异常
                    new ThreadPoolExecutor.AbortPolicy());
        }
    }
}


package com.zxb.service.lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量测试
 * @author admin
 * @create 2020/6/15
 * @since 1.0.0
 */
public class SemaphoreTest implements Runnable{
    //而对于Semaphore来说，它允许多个线程同时进入临界区。可以认为它是一个共享锁，但是共享的额度是有限制的，额度用完了，其他没有拿到额度的线程还是要阻塞在临界区外
    private static final Semaphore semaphore=new Semaphore(5);

    @Override
    public void run() {
        try {
            //比如5个线程全部进来之后才进行执行,如果没有达到峰值就一直不执行
            semaphore.acquire();
            Thread.sleep(6000);
            System.out.println(Thread.currentThread().getId());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            //刷新
            semaphore.release();
        }
    }

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        final SemaphoreTest t = new SemaphoreTest();
        for (int i = 0; i < 20; i++)
        {
            executorService.submit(t);
        }
    }
}

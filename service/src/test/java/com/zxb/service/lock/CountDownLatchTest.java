package com.zxb.service.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *
 * @author zxb
 * @create 2020/6/15
 * @since 1.0.0
 */
public class CountDownLatchTest implements Runnable{
    private static final CountDownLatch COUNT_DOWN_LATCH=new CountDownLatch(10);
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            System.out.println("k开始是====="+Thread.currentThread().getId());
            //减一
            COUNT_DOWN_LATCH.countDown();
            System.out.println("等待");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatchTest t=new CountDownLatchTest();
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(new Thread(t));
        }
        COUNT_DOWN_LATCH.await();
        System.out.println("结束");
        executorService.shutdown();
    }

}

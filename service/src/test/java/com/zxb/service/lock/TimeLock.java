package com.zxb.service.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 超时不能获取锁
 * @author admin
 * @create 2020/6/15
 * @since 1.0.0
 */
public class TimeLock implements Runnable{
    private static ReentrantLock lock=new ReentrantLock();

    @Override
    public void run() {
        try {
            //每个线程都只尝试5秒去获得锁。
            if (lock.tryLock(5, TimeUnit.SECONDS)){
                //等待锁超时
                System.out.println("获取到锁");
                Thread.sleep(6000);
                System.out.println(Thread.currentThread().getId());
            }else{
                System.out.println("等待锁释放"); 
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //是的等到锁
            if (lock.isHeldByCurrentThread()){
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        TimeLock timeLock=new TimeLock();
        new Thread(timeLock).start();
        new Thread(timeLock).start();
    }
}

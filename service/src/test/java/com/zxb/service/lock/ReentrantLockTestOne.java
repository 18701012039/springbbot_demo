package com.zxb.service.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 可重入锁
 * @author admin
 * @create 2020/6/15
 * @since 1.0.0
 */
public class ReentrantLockTestOne implements Runnable{
    private static ReentrantLock reentrantLock=new ReentrantLock();
    private static int i=0;

    @Override
    public void run() {
        //获取锁测试是否可以重入
        reentrantLock.lock();
        reentrantLock.lock();
        try {
            i++;
        } finally {
            //如果当前只释放一次锁就就会一直等待
            reentrantLock.unlock();
            reentrantLock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTestOne reentrantLockTestOne=new ReentrantLockTestOne();
        Thread thread = new Thread(reentrantLockTestOne);
        Thread thread1 = new Thread(reentrantLockTestOne);
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
        System.out.println(i);
    }
}

package com.zxb.service.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 这个只能在单个服务器上可以这样控制
 * 如果像现在的分布式，部署80,81两个后台服务器，就不能用这个就需要用
 * zk的分布式锁或者用redis的setnx或者用redisson来实现分布式锁
 * 同步锁
 * @author admin
 * @create 2020/6/15
 * @since 1.0.0
 */
public class ReentrantLockTest implements Runnable{
    public static ReentrantLock lock=new ReentrantLock();
    public static int i=0;
    @Override
    public void run() {
        for (int i1 = 0; i1 < 10000000; i1++) {
            //获取锁
            lock.lock();
            try {
                i++;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //释放锁
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ReentrantLockTest t=new ReentrantLockTest();
        Thread thread = new Thread(t, "zxb");
        Thread thread1=new Thread(t,"zxb1");
        thread.start();
        thread1.start();
        thread.join();
        thread1.join();
        System.out.println(i);

    }
}

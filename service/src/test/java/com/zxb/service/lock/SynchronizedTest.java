package com.zxb.service.lock;
/**
 * synchronized 也是可重入锁
 * @author admin
 * @create 2020/6/15
 * @since 1.0.0
 */
public class SynchronizedTest extends Test implements Runnable {
    //唯一实例
    final static SynchronizedTest SYNCHRONIZED_TEST=new SynchronizedTest();

    @Override
    public void run() {
        SYNCHRONIZED_TEST.doSomething();
    }
    public synchronized void doSomething(){
        System.out.println("1child第一次");
        doSomethingThree();
    }

    public synchronized void doSomethingThree(){
        super.doSomething();
        System.out.println("第三次");
    }

    public static void main(String[] args) {
        SynchronizedTest synchronizedTest=new SynchronizedTest();
        for (int i = 0; i < 5; i++) {
            new Thread(synchronizedTest).start();
        }
    }
}
class Test{
    public synchronized void doSomething(){
        System.out.println("第二次");
    }
}

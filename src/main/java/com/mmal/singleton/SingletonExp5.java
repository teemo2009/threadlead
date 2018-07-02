package com.mmal.singleton;

import com.mmal.annoations.NotThreadSafe;
import com.mmal.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 懒汉式
 * 双重检测机制
 */
@Slf4j
@ThreadSafe
public class SingletonExp5 {
    //私有化构造函数
    private SingletonExp5() {
        log.info("被创建1次");
    }
    //限制指令重排
    private static volatile  SingletonExp5 instance = null;

    public  static SingletonExp5 getInstance() {
        if (instance == null) {
            synchronized (SingletonExp5.class) {
                if (instance == null) {
                    //1.分配对象内存空间
                    //2.初始化对象
                    //3.设置instance指向刚分配的内存
                    instance = new SingletonExp5();
                }
            }
        }
        return instance;
    }


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1000);
        Semaphore semaphore = new Semaphore(200);
        ExecutorService service = Executors.newCachedThreadPool();
        for (int i = 0; i < 1000; i++) {
            service.execute(() -> {
                try {
                    semaphore.acquire();
                    SingletonExp5.getInstance();
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await();
        log.info("结束了");
    }
}

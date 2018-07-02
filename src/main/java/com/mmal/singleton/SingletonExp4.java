package com.mmal.singleton;

import com.mmal.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 懒汉式
 */
@Slf4j
@NotThreadSafe
public class SingletonExp4 {
    //私有化构造函数
    private SingletonExp4() {
        log.info("被创建1次");
    }

    private static SingletonExp4 instance = null;

    public  static SingletonExp4 getInstance() {
        if (instance == null) {
            synchronized (SingletonExp4.class) {
                if (instance == null) {
                    //1.分配对象内存空间
                    //2.初始化对象
                    //3.设置instance指向刚分配的内存

                    //JVM和CPU优化发生指令重排
                    //1.分配对象内存空间
                    //3.设置instance指向刚分配的内存
                    //2.初始化对象
                    instance = new SingletonExp4();
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
                    SingletonExp4.getInstance();
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

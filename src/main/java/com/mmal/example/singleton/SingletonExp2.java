package com.mmal.example.singleton;

import com.mmal.annoations.NotRecommend;
import com.mmal.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *  饿汉式
 *  类装载的时候就进行实例化
 *  如果初始化方法中有过多的方法时候，启动会非常慢
 *  如果初始化了，不去使用，会浪费资源
 * */
@Slf4j
@ThreadSafe
@NotRecommend
public class SingletonExp2 {
    //私有化构造函数
    private SingletonExp2(){
        log.info("初始化了一次");
    }

    private static SingletonExp2 instance=new SingletonExp2();

    public static SingletonExp2 getInstance(){
        return instance;
    }


    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(1000);
        Semaphore semaphore=new Semaphore(200);
        ExecutorService service= Executors.newCachedThreadPool();
        for(int i=0;i<1000;i++){
            service.execute(()->{
                try {
                    semaphore.acquire();
                    SingletonExp2.getInstance();
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

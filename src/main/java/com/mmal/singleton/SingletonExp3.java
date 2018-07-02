package com.mmal.singleton;

import com.mmal.annoations.NotRecommend;
import com.mmal.annoations.NotThreadSafe;
import com.mmal.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *  懒汉式
 *  同一时间类只运行一个线程访问，性能开销太严重
 * */
@Slf4j
@ThreadSafe
@NotRecommend
public class SingletonExp3 {
    //私有化构造函数
    private SingletonExp3(){

    }

    private static SingletonExp3 instance=null;

    public synchronized static SingletonExp3 getInstance(){
        if(instance==null){
            // 在构造函数的时候，可能会做一些初始化操作，这样会导致一些错误
            log.info("被创建1次");
            instance=new SingletonExp3();
        }
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
                    SingletonExp3.getInstance();
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

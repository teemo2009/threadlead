package com.mmal.example.singleton;

import com.mmal.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 *  懒汉式
 * */
@Slf4j
@NotThreadSafe
public class SingletonExp1 {
    //私有化构造函数
    private SingletonExp1(){

    }

    private static SingletonExp1 instance=null;

    public static SingletonExp1 getInstance(){
        if(instance==null){//多线程不安全——该对象被创建了多次，
            // 在构造函数的时候，可能会做一些初始化操作，这样会导致一些错误
            log.info("被创建1次");
            instance=new SingletonExp1();
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
                    SingletonExp1.getInstance();
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

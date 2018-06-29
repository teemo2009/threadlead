package com.mmal.atomic;

import com.mmal.annoations.ThreadSafe;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

@ThreadSafe
@Slf4j
public class AtomicExample6 {

    private static AtomicBoolean isHappend=new AtomicBoolean(false);
    public static int clientTotal=5000;
    //并发直线的线程总数
    public static int threadTotal=200;
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService= Executors.newCachedThreadPool();
        final Semaphore semaphore=new Semaphore(threadTotal);
        final CountDownLatch countDownLatch=new CountDownLatch(clientTotal);
        for(int i=0;i<clientTotal;i++){
            executorService.execute(()->{
                try {
                    semaphore.acquire();
                    test();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception",e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}",isHappend.get());
    }

    private static  void test(){
        System.out.println("test");
        if(isHappend.compareAndSet(false,true)){
            log.info("执行了");
        }
    }

}

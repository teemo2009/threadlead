package com.mmal;

import com.mmal.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@NotThreadSafe
public class ConcurrencyTest {
    public  int inc = 0;

    public void increase() {
        inc++;
    }

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch=new CountDownLatch(2);
        Semaphore semaphore=new Semaphore(10);
        ExecutorService executorService=Executors.newCachedThreadPool();
        final ConcurrencyTest test = new ConcurrencyTest();
        for(int i=0;i<2;i++){
            executorService.execute(()->{
                for(int j=0;j<100;j++){
                    try {
                        semaphore.acquire();
                        test.increase();
                        semaphore.release();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    countDownLatch.countDown();
                }
            });
        }
        countDownLatch.await();
        System.out.println(test.inc);
    }

}

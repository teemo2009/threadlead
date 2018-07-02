package com.mmal.publish;

import com.mmal.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@NotThreadSafe
public class UnSafePublish {
    /**
     *  这里的私有对象并不是 多线程安全的！！！
     * */
    private String[] states={"a","b","c"};
    public String[] getStates(){
        return this.states;
    }

    public static void main(String[] args) throws InterruptedException {
        int total=1000;
        int thread=200;
        UnSafePublish unSafePublish=new UnSafePublish();
        CountDownLatch countDownLatch=new CountDownLatch(total);
        final Semaphore semaphore=new Semaphore(thread);
        ExecutorService service=Executors.newCachedThreadPool();
        for (int i=0;i<total;i++) {
            service.execute(() -> {
                try {
                    log.info("{}", Arrays.toString(unSafePublish.getStates()));
                    semaphore.acquire();
                    unSafePublish.getStates()[0] = "d";
                    semaphore.release();
                    countDownLatch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
        countDownLatch.await();
        log.info("结束了{}", Arrays.toString(unSafePublish.getStates()));
    }
}

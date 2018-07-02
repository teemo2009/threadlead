package com.mmal.publish;

import com.mmal.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Slf4j
@NotThreadSafe
public class Escape {
    private int thisCanBeEscape=0;
    public Escape(){
        new InnerClass();
    }
    private  class  InnerClass{
        public InnerClass(){
            ExecutorService service= Executors.newCachedThreadPool();
            //this对象逃逸，多线程情况下 对象初始化为完成 将会出错  具体参见
            // https://coding.imooc.com/learn/questiondetail/47686.html
            service.execute(()-> log.info("{}",Escape.this.thisCanBeEscape));
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int total=1000;
        int thread=200;
        ExecutorService service= Executors.newCachedThreadPool();
        CountDownLatch countDownLatch=new CountDownLatch(total);
        Semaphore semaphore=new Semaphore(thread);
        for(int i=0;i<total;i++){
            service.execute(()->{
                try {
                    semaphore.acquire();
                    Escape escape=new Escape();
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

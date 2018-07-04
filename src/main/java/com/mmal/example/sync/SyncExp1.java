package com.mmal.example.sync;

import com.mmal.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@ThreadSafe
public class SyncExp1 {


    //修饰一个代码块
    public void test1(int j){
            synchronized (this){
                for(int i=0;i<10;i++){
                    log.info("test1-{}-{}",j,i);
                }
            }
    }

    //修饰一个方法
    public synchronized void  test2(int j){
        for(int i=0;i<10;i++){
            log.info("test2--{}-{}",j,i);
        }
    }


    public static void main(String[] args) {
        SyncExp1 syncExp1=new SyncExp1();
        SyncExp1 syncExp2=new SyncExp1();
        ExecutorService executorService= Executors.newCachedThreadPool();
        executorService.execute(()->{
            syncExp1.test2(1);
        });
        executorService.execute(()->{
            syncExp2.test2(2);
        });
    }
}

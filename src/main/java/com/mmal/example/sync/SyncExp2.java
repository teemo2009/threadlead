package com.mmal.example.sync;

import com.mmal.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@ThreadSafe
public class SyncExp2 {


    //修饰一个类
    public static void test1(int j){
            synchronized (SyncExp2.class){
                for(int i=0;i<10;i++){
                    log.info("test1-{}-{}",j,i);
                }
            }
    }

    //修饰一个静态方法
    public static synchronized void  test2(int j){
        for(int i=0;i<10;i++){
            log.info("test2--{}-{}",j,i);
        }
    }


    public static void main(String[] args) {
        SyncExp2 syncExp1=new SyncExp2();
        SyncExp2 syncExp2=new SyncExp2();
        ExecutorService executorService= Executors.newCachedThreadPool();
        executorService.execute(()->{
            syncExp1.test1(1);
        });
        executorService.execute(()->{
            syncExp2.test1(2);
        });
    }
}

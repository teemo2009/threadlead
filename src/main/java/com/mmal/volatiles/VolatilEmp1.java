package com.mmal.volatiles;

import com.mmal.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/***
 *  volatile 使用场景 用于标识符
 * */
@Slf4j
@NotThreadSafe
public class VolatilEmp1 {

    /**是否初始化标识符,不加volatile也能实现同样的效果（大多数情况下），
     * 为什么要加呢 https://coding.imooc.com/learn/questiondetail/56274.html*/
    private volatile  boolean isInitFinish=false;

    /**模拟初始化操作*/
    public void init(){
            loadSome();
            isInitFinish=true;
    }

    public void loadSome(){
        try {
            log.info("这里是初始化操作");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**初始化完成后执行操作**/
    public void operation(){
        while (!isInitFinish){
            log.info("初始化中。。。。");
        }
        log.info("初始化完成");
    }

    public static void main(String[] args) {
        VolatilEmp1 volatilEmp1=new VolatilEmp1();
        ExecutorService executorService= Executors.newCachedThreadPool();
        executorService.execute(()->{
            volatilEmp1.init();
        });
        executorService.execute(()->{
            volatilEmp1.operation();
        });
    }

}

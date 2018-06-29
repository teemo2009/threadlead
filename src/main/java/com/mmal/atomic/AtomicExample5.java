package com.mmal.atomic;

import com.mmal.annoations.ThreadSafe;
import lombok.Getter;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class AtomicExample5 {

    private static AtomicIntegerFieldUpdater<AtomicExample5> updater=AtomicIntegerFieldUpdater.newUpdater(AtomicExample5.class,"count");
    @Getter
    public  volatile  int count=100;

    private static AtomicExample5 atomicExample5=new AtomicExample5();

    public static void main(String[] args) {
        if(updater.compareAndSet(atomicExample5,100,120)){
            System.out.println("update success 1 "+atomicExample5.getCount());
        }
        if(updater.compareAndSet(atomicExample5,100,120)){
            System.out.println("update success 2 "+atomicExample5.getCount());
        }else{
            System.out.println("update fail 2 "+atomicExample5.getCount());
        }

    }


}

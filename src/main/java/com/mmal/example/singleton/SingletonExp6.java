package com.mmal.example.singleton;

import com.mmal.annoations.Recommend;
import com.mmal.annoations.ThreadSafe;
import lombok.extern.slf4j.Slf4j;

/**
 * 枚举模式
 * 最安全的
 */
@Slf4j
@ThreadSafe
@Recommend
public class SingletonExp6 {
  private SingletonExp6(){

  }

  public static SingletonExp6 getInstatnce(){
    return Singleton.INSTANCE.getInstatnce();
  }


  private enum Singleton{
      INSTANCE;
      private  SingletonExp6 singleton;
      //JVM保证只调用一次
      Singleton(){
          singleton=new SingletonExp6();
      }
      public SingletonExp6 getInstatnce(){
          return singleton;
      }
  }
}

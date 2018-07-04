package com.mmal.example.immutable;

import com.google.common.collect.Maps;
import com.mmal.annoations.NotThreadSafe;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@NotThreadSafe
public class ImmutableExp1 {
    private  final static  Integer a=1;
    private final static String b="2";
    private final static Map<Integer,Integer> map= Maps.newHashMap();
    static {
        map.put(1,2);
        map.put(3,4);
        map.put(5,6);
    }

    public static void main(String[] args) {
       /** 基础类型不能修改
        a=2;
        b="3";*/
       /**对象不允许重定向引用
        * map=Maps.newHashMap();*/
       map.put(1,3);
       log.info("{}",map.get(1));
    }

    /*private void test( final int a){
        a=1;
    }*/
}

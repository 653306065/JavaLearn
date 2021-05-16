package org.java.study.lock;

import com.alibaba.fastjson.JSON;

import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

/**
 * CopyOnWriteArrayList主要的实现原理是，在写操作的时候，由重入锁控制写为单个线程，复制原来数组的数组，然后进行写操作，把修改好的数组，覆盖原来的数组
 * 对读操作直接读类内部的数组，适用于对数据实时性要求不高的场景，写入的数据量过大，会导致读线程获取不到最新的数据，不断复制数组对内存的压力比较
 * 可能会触发GC操作
 */
public class CopyOnWriteArrayListTest {

    public static void main(String[] args) {
        CopyOnWriteArrayList<String> list=new CopyOnWriteArrayList<>();
        //读线程
        IntStream.range(0,10).forEach(value -> {
            new Thread(()->{
                while (true){

                    System.out.println(JSON.toJSONString(list.toArray()));
                }
            }).start();
        });

        //写线程
        Thread thread=new Thread(()->{
            while (true){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String uuid=UUID.randomUUID().toString();
                list.add(uuid);
                System.out.println("--------------"+uuid+"--------------");
            }
        });
        thread.start();
    }
}

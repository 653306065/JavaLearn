package org.java.study.thread;

import com.alibaba.fastjson.JSON;
import io.netty.util.internal.ConcurrentSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class ForkJoinPoolTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        List<String> list=new ArrayList<>();
        for(int i=0;i<100000;i++){
            list.add(String.valueOf(i));
        }
        Set<String> name=new ConcurrentSet<>();
        forkJoinPool.submit(()-> list.parallelStream().forEach(value->{
            System.out.println(Thread.currentThread().getName()+","+value);
            name.add(Thread.currentThread().getName());
        })).get();
        System.out.println(JSON.toJSONString(name));
        System.out.println(name.size());
        forkJoinPool.shutdown();
    }


}

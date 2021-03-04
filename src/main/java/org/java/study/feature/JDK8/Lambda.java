package org.java.study.feature.JDK8;

import java.util.*;
import java.util.stream.Collectors;

public class Lambda {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            list.add(UUID.randomUUID().toString());
        }
        list.stream().forEach(System.out::println);//循环
        list.stream().sorted().collect(Collectors.toList());//排序
        list.stream().distinct().collect(Collectors.toList());//去重
        list.stream().skip(0).limit(10).collect(Collectors.toList());//分页
        list.stream().filter(value-> Objects.isNull(value)).collect(Collectors.toList());//过滤
        Map<String, String> map = list.stream().collect(Collectors.toMap(value -> value, value -> value));//toMap
        Map<String, List<String>> mapList = list.stream().collect(Collectors.groupingBy(value -> value));//分组
        list.stream().max(Comparator.comparing(value->value)).get();//最大值
        list.stream().min(Comparator.comparing(value->value)).get();//最小值
        list.stream().count();//数量
        list.stream().collect(Collectors.toSet());//toSet
        Boolean b= list.stream().anyMatch(value->value.startsWith("a"));//匹配
        //并行流
        list.parallelStream().forEach(value->{
            System.out.println(Thread.currentThread().getName()+","+value);
        });

        //函数式接口
        Thread thread=new Thread(()->{
           System.out.println("run");
        });
        thread.start();
    }
}

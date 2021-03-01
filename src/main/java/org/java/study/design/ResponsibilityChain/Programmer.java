package org.java.study.design.ResponsibilityChain;

//程序员
public class Programmer extends Staff{

    public Programmer() {
        super("programmer", 666);
    }

    @Override
    public void work() {
        System.out.println(this.name+",完成了工作");
    }
}

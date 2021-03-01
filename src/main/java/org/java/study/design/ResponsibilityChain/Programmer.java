package org.java.study.design.ResponsibilityChain;

public class Programmer extends Staff{

    public Programmer() {
        super("programmer", 777);
    }

    @Override
    public void work() {
        System.out.println(this.name+",完成了工作");
    }
}

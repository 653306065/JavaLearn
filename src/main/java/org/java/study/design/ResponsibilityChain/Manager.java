package org.java.study.design.ResponsibilityChain;

public class Manager extends Staff{
    public Manager() {
        super("manager", 888);
    }

    @Override
    public void work() {
        System.out.println(this.name+",完成了工作");
    }
}

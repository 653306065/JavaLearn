package org.java.study.design.ResponsibilityChain;

public class Leader extends Staff{
    public Leader() {
        super("Leader", 777);
    }

    @Override
    public void work() {
        System.out.println(this.name+",完成了工作");
    }
}

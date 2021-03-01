package org.java.study.design.ResponsibilityChain;

//老板
public class Boss extends Staff{


    public Boss() {
        super("BOSS", 999);
    }

    @Override
    public void work() {
        System.out.println(this.name+",完成了工作");
    }
}

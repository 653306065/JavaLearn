package org.java.study.design.ResponsibilityChain;

import java.util.Objects;

//员工
public abstract class Staff {

    protected Integer level;

    protected String name;

    protected Staff nextStaff;

    public Staff(String name, Integer level) {
        this.name = name;
        this.level = level;
    }

    protected Staff setNextStaff(Staff staff) {
        this.nextStaff = staff;
        return nextStaff;
    }

    public void doWork() {
        if (Objects.nonNull(nextStaff) && this.level > nextStaff.level) {
            nextStaff.doWork();
        } else {
            work();
        }
    }

    public abstract void work();
}

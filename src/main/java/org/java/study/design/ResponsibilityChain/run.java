package org.java.study.design.ResponsibilityChain;

public class run {
    public static void main(String[] args) {
        Programmer programmer = new Programmer();
        Manager manager = new Manager();
        Boss boss = new Boss();
        boss.setNextStaff(manager);
        manager.setNextStaff(programmer);
        boss.doWork();
    }
}

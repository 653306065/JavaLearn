package org.java.study.design.ResponsibilityChain;

public class run {
    public static void main(String[] args) {
        Boss boss = new Boss();
        boss.setNextStaff(new Manager()).setNextStaff(new Leader()).setNextStaff(new Programmer());
        boss.doWork();
    }
}

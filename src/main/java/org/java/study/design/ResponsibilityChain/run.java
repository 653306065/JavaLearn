package org.java.study.design.ResponsibilityChain;


/**
 * 责任链模式,继承同一个父类的对象，具有一定的层级关系，对象中包含下一个的层级的对象，方法调用时，依次从上往下调用，层级最低的对象为方法的最终调用者
 */
public class run {
    public static void main(String[] args) {
        Boss boss = new Boss();
        boss.setNextStaff(new Manager()).setNextStaff(new Leader()).setNextStaff(new Programmer());
        boss.doWork();
    }
}

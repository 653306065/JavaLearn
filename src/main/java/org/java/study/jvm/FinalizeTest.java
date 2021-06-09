package org.java.study.jvm;

public class FinalizeTest {
    
    public void finalize(){
        System.out.println("被GC回收了");
    }
    
    public static void main(String[] args) {
        while (true){
            FinalizeTest finalizeTest=new FinalizeTest();
        }
    }
}

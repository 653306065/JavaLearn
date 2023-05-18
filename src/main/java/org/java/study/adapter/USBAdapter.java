package org.java.study.adapter;

public class USBAdapter extends USBA implements TypeC{
    @Override
    public String getIO() {
        String IO= super.getIO()+"-->"+"TYPE-C";
        System.out.println(IO);  ;
        return IO;
    }

    public static void main(String[] args) {
        USBAdapter usbAdapter=new USBAdapter();
        usbAdapter.getIO();
    }
}

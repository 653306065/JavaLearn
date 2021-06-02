package org.java.study.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelTest {
    public static void main(String[] args) throws IOException {
        FileChannel fileChannel = new FileInputStream("E:\\SMBD\\SMBD-155.mp4").getChannel();
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024*5);
        FileChannel fileChannel1 = new FileOutputStream("C:\\Users\\cookie\\Desktop\\test.mp4").getChannel();
        while (fileChannel.read(buffer) != -1) {
            buffer.flip();
            fileChannel1.write(buffer);
            buffer.clear();
        }
    }
}

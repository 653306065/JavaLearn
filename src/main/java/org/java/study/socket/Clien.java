package org.java.study.socket;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Clien {

    public static void main(String[] args) {
        String IP = "127.0.0.1";
        Integer port = 9090;
        for (int i = 0; i < 10000; i++) {
            new Thread(() -> {
                try {
                    Socket socket = new Socket(IP, port);
                    socket.setKeepAlive(true);
                    String message = Thread.currentThread().getName() + "," + System.currentTimeMillis();
                    OutputStream outputStream = socket.getOutputStream();
                    outputStream.write(message.getBytes(StandardCharsets.UTF_8));
                    outputStream.flush();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

package org.java.study.socket;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Clien {

    public static void main(String[] args) {
        String IP = "127.0.0.1";
        Integer port = 9090;
        for (int i = 0; i < 1; i++) {
            new Thread(() -> {
                try {
                    Socket socket = new Socket(IP, port);
                    socket.setKeepAlive(true);
                    while (true) {
                        String message = Thread.currentThread().getName() + "," + System.currentTimeMillis();
                        OutputStream outputStream = socket.getOutputStream();
                        PrintWriter out = new PrintWriter(outputStream,true);
                        out.println(message);
                        out.flush();

                        InputStream inputStream = socket.getInputStream();
                        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                        byte[] bytes = bufferedInputStream.readAllBytes();
                        String text = new String(bytes);
                        System.out.println(new String(bytes));



                        if ("exit".equals(text)) {
                            break;
                        }
                    }
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }
}

package org.java.study.socket;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class Server {


    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor threadPoolExecutor = getThreadPoolExecutor();
        ServerSocket serverSocket = new ServerSocket(9090);
        while (true) {
            Socket socket = serverSocket.accept();
            threadPoolExecutor.execute(() -> {
                try {
                    InputStream inputStream = socket.getInputStream();
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    byte[] bytes = bufferedInputStream.readAllBytes();
                    System.out.println(new String(bytes));
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public static ThreadPoolExecutor getThreadPoolExecutor() {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(500, 1000, 1, TimeUnit.MINUTES, new LinkedBlockingDeque(), new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                executor.execute(r);
            }
        });
        return threadPoolExecutor;
    }
}

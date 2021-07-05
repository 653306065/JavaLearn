package org.java.study.rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

public class MessageTest {

    static ConnectionFactory factory = new ConnectionFactory();

    static String queueName = "myQueue";

    static {
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
    }

    public static void main(String[] args) {
        new Thread(() -> {
            try {
                producer();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }).start();
        new Thread(() -> {
            try {
                consumer();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void producer() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        AMQP.Queue.DeclareOk queue = channel.queueDeclare(queueName, true, false, false, null);
        IntStream.range(0,10).forEach(value -> {
            String uuid = UUID.randomUUID().toString();
            try {
                channel.basicPublish("", queueName, null, uuid.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void consumer() throws IOException, TimeoutException {
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.basicConsume(queueName, true, new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag,
                                       Envelope envelope,
                                       AMQP.BasicProperties properties,
                                       byte[] body) {

                System.out.println(new String(body));
            }
        });
    }
}

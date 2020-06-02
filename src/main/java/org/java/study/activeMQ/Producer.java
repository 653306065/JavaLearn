package org.java.study.activeMQ;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class Producer {

	public static String queueName = "myQueue";

	public static String topicName = "myTopic";

	public static String address = "tcp://127.0.0.1:61616";

	public static MessageProducer QueueMessageProduce = getQueueMessageProduce();

	public static MessageProducer TopicMessageProduce = getQueueMessageProduce();

	public static void main(String[] args) throws JMSException {
		for (int i = 0; i < 100; i++) {
			sendQueueText("sendQueueText_" + i);
			sendTopicText("sendTopicText_" + i);
		}
	}

	/**
	 * 队列模式发文本消息
	 * 
	 * @param text
	 */
	public static void sendQueueText(String text) {
		try {
			TextMessage textMessage = new ActiveMQTextMessage();
			textMessage.setText(text);
			QueueMessageProduce.send(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void sendTopicText(String text) {
		try {
			TextMessage textMessage = new ActiveMQTextMessage();
			textMessage.setText(text);
			TopicMessageProduce.send(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static MessageProducer getQueueMessageProduce() {
		try {
			ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(address);
			Connection connection = activeMQConnectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Queue queue = session.createQueue(queueName);
			MessageProducer messageProducer = session.createProducer(queue);
			return messageProducer;
		} catch (Exception e) {
			return null;
		}
	}

	public static MessageProducer getTopicMessageProduce() {
		try {
			ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(address);
			Connection connection = activeMQConnectionFactory.createConnection();
			connection.start();
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			Topic topic = session.createTopic(topicName);
			MessageProducer messageProducer = session.createProducer(topic);
			messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
			return messageProducer;
		} catch (Exception e) {
			return null;
		}
	}
}

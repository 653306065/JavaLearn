package org.java.study.activeMQ;

import java.util.UUID;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class Producer {

	public static String queueName = "myQueue";

	public static String address = "tcp://127.0.0.1:61616";

	public static void main(String[] args) throws JMSException {
		ActiveMQConnectionFactory activeMQConnectionFactory=new ActiveMQConnectionFactory(address);
		Connection connection= activeMQConnectionFactory.createConnection();
		connection.start();
		Session session= connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue=session.createQueue(queueName);
		MessageProducer messageProducer=session.createProducer(queue);
		//ActiveMQTextMessage ActiveMQTextMessage =new ActiveMQTextMessage();
//		for(int i=0;i<1000;i++) {
//			TextMessage textMessage=session.createTextMessage("my text message");
//			messageProducer.send(textMessage);
//		}
		while(true) {
			TextMessage textMessage=session.createTextMessage(UUID.randomUUID().toString());
			messageProducer.send(textMessage);
		}
//		session.close();
//		connection.close();
	}
}

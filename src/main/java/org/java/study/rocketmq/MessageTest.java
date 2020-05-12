package org.java.study.rocketmq;

import java.io.File;
import java.util.List;
import java.util.UUID;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

public class MessageTest {

	public static String namesrvAddr = "127.0.0.1:9876";

	public static String toptic = "myTopic";

	public static void main(String[] args) throws Exception {
		DefaultMQProducer defaultMQProducer = new DefaultMQProducer("myMQProducer");
		defaultMQProducer.setNamesrvAddr(namesrvAddr);
		//defaultMQProducer.setDefaultTopicQueueNums(1);
		defaultMQProducer.start();
		for (int i = 0; i < 100; i++) {
			Message message = new Message(toptic, String.valueOf(UUID.randomUUID().toString()+"_"+i).getBytes());
			SendResult sendResult = defaultMQProducer.send(message,new MessageQueueSelector() {
				@Override
				public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
//					int orderId = (int)arg;
//					System.out.println(new String(msg.getBody()));	
//					System.out.println(orderId % mqs.size());
					return mqs.get( mqs.size()-1);
				}
			},i);
			// System.out.println(JSON.toJSONString(sendResult));
		}
		getDefaultMQPullConsumer("客户端1");
		//getDefaultMQPullConsumer("客户端2");
		//getDefaultMQPullConsumer("客户端2");

		defaultMQProducer.shutdown();
	}

	public static DefaultMQPushConsumer getDefaultMQPullConsumer(String name) throws Exception {
		DefaultMQPushConsumer defaultMQPullConsumer = new DefaultMQPushConsumer("myMQPushConsumer");
		defaultMQPullConsumer.setNamesrvAddr(namesrvAddr);
		defaultMQPullConsumer.setInstanceName(name);
		defaultMQPullConsumer.subscribe(toptic, "*");
		//defaultMQPullConsumer.
		defaultMQPullConsumer.registerMessageListener(new Listener() );
		defaultMQPullConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		defaultMQPullConsumer.setMessageModel(MessageModel.CLUSTERING);
//		defaultMQPullConsumer.setConsumeThreadMax(1);
//		defaultMQPullConsumer.setConsumeThreadMin(1);
		defaultMQPullConsumer.setPullBatchSize(1);
		
		defaultMQPullConsumer.start();
		return defaultMQPullConsumer;
	}
	
	
	public static class Listener implements MessageListenerOrderly{

		@Override
		public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
			for (MessageExt msg : msgs) {
				System.out.println(new String(msg.getBody()));	
			}
			return ConsumeOrderlyStatus.SUCCESS;
		}
		
	}
}

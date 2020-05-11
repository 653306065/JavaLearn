package org.java.study.rocketmq;

import java.util.List;
import java.util.UUID;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;

import com.alibaba.fastjson.JSON;

public class MessageTest {

	public static String namesrvAddr = "127.0.0.1:9876";

	public static String toptic = "ertiugdfk";

	public static void main(String[] args) throws Exception {
		DefaultMQProducer defaultMQProducer = new DefaultMQProducer("defaultMQProducer");
		defaultMQProducer.setNamesrvAddr(namesrvAddr);
		defaultMQProducer.start();
		getDefaultMQPullConsumer("Consumer1");
		getDefaultMQPullConsumer("Consumer2");
		getDefaultMQPullConsumer("Consumer3");
		getDefaultMQPullConsumer("Consumer4");
		for (int i = 0; i < 1000; i++) {
			Message message = new Message(toptic, String.valueOf(i).getBytes());
			SendResult sendResult = defaultMQProducer.send(message, new MessageQueueSelector() {
				@Override
				public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
					Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
				}
			}, 0);
			// System.out.println(JSON.toJSONString(sendResult));
		}
		// getDefaultMQPullConsumer();
		
	}

	public static DefaultMQPushConsumer getDefaultMQPullConsumer(String name) throws Exception {
		DefaultMQPushConsumer defaultMQPullConsumer = new DefaultMQPushConsumer(UUID.randomUUID().toString());
		defaultMQPullConsumer.setNamesrvAddr(namesrvAddr);
		defaultMQPullConsumer.setInstanceName(name);
		defaultMQPullConsumer.subscribe(toptic, "*");
		//defaultMQPullConsumer.
		
		defaultMQPullConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
		defaultMQPullConsumer.setMessageModel(MessageModel.CLUSTERING);
//		defaultMQPullConsumer.setConsumeThreadMax(1);
//		defaultMQPullConsumer.setConsumeThreadMin(1);
//		defaultMQPullConsumer.setPullBatchSize(1);
		defaultMQPullConsumer.registerMessageListener(new MessageListenerOrderly() {
			@Override
			public ConsumeOrderlyStatus consumeMessage(List<MessageExt> msgs, ConsumeOrderlyContext context) {
				context.setAutoCommit(true);
				for (MessageExt msg : msgs) {
					System.out.println(defaultMQPullConsumer.getInstanceName() + "," + new String(msg.getBody()));
				}
				return ConsumeOrderlyStatus.SUCCESS;
			}
		});
		defaultMQPullConsumer.start();
		return defaultMQPullConsumer;
	}
}

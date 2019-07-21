package com.xyp.activemq.topicPersist;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicSubscriber;

import org.apache.activemq.ActiveMQConnectionFactory;

public class PersistTopicConsumer {

	private static final String topic_name="persist_topic1";
	private static String brokerUrl="tcp://10.1.1.220:61616";
	private static String consumerName="持久化订阅者二号 ";
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory=new ActiveMQConnectionFactory(brokerUrl);
		Connection connection=factory.createConnection();
		
		connection.setClientID("demaxiya");
		
		
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic=session.createTopic(topic_name);
		TopicSubscriber topicSubscriber=session.createDurableSubscriber(topic, "demaxiya");
		connection.start();
		//MessageConsumer consumer=session.createConsumer(topic);
		while (true) {
			Message message=topicSubscriber.receive(4000);
			if(message!=null && message instanceof TextMessage){
				TextMessage textMessage=(TextMessage) message;
				System.out.println(consumerName+" 收到消息："+textMessage.getText());
			}else{
				break;
			}
		}
		
		System.out.println("消费者退出....");
		topicSubscriber.close();
		session.close();
		connection.close();
		
	}

}

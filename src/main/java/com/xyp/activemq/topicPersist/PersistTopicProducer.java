 package com.xyp.activemq.topicPersist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class PersistTopicProducer {
	
	private static String brokerUrl="tcp://10.1.1.220:61616";
	
	private static String topic_name="persist_topic1";
	
	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(brokerUrl);
		Connection connection=connectionFactory.createConnection();
		//connection.start();
		
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic=session.createTopic(topic_name);
		MessageProducer producer=session.createProducer(topic);
		 //在队列消息生产者端设置消息持久化，然后才开启connection
		producer.setDeliveryMode(DeliveryMode.PERSISTENT);
		connection.start();
		
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<3;i++){
			String now=format.format(new Date());
			//Message message=new ActiveMQTextMessage();
			Message message=session.createTextMessage("msg- "+i+" ,内容为 ：timestamp="+now);
			producer.send(message);
		}
		System.out.println("生产者发送消息完成....");
		producer.close();
		session.close();
		connection.close();
	}

}

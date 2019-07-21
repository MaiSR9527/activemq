package com.xyp.activemq.queue.persist;

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

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.command.ActiveMQTextMessage;

public class ProducerTest {
	
	private static String brokerUrl="tcp://10.1.1.220:61616";
	public static void main(String[] args) throws JMSException {
		ConnectionFactory connectionFactory=new ActiveMQConnectionFactory(brokerUrl);
		Connection connection=connectionFactory.createConnection();
		connection.start();
		
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		Queue queue=session.createQueue("msg-queue");
		MessageProducer producer=session.createProducer(queue);
		producer.setDeliveryMode(DeliveryMode.PERSISTENT); //设置持久化/非持久化
		DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		for(int i=0;i<3;i++){
			String now=format.format(new Date());
			Message message=session.createTextMessage("msg- "+i+" ,内容为 ：timestamp="+now);
			producer.send(message);
		}
		producer.close();
		session.close();
		connection.close();
	}

}

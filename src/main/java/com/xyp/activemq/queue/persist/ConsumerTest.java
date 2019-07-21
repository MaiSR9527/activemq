package com.xyp.activemq.queue.persist;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class ConsumerTest {

	private static final String queueName="msg-queue";
	private static String brokerUrl="tcp://10.1.1.220:61616";
	private static String consumerName="���������� ";
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory=new ActiveMQConnectionFactory(brokerUrl);
		Connection connection=factory.createConnection();
		connection.start();
		
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue queue=session.createQueue(queueName);
		MessageConsumer consumer=session.createConsumer(queue);
		//��ʽ�������һ������(��ʵ��һ��������һ�����߳�)
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				if(message!=null && message instanceof TextMessage){
					TextMessage textMessage=(TextMessage) message;
					try {
						System.out.println(textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		System.in.read();
		System.out.println("�������˳�....");
		consumer.close();
		session.close();
		connection.close();
		
	}

}

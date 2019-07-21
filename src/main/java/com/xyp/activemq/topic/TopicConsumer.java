package com.xyp.activemq.topic;

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

import org.apache.activemq.ActiveMQConnectionFactory;

public class TopicConsumer {

	private static final String topic_name="topic1";
	private static String brokerUrl="tcp://10.1.1.220:61616";
	private static String consumerName="���Ŷ��������ߣ� ";
	
	public static void main(String[] args) throws Exception {
		ConnectionFactory factory=new ActiveMQConnectionFactory(brokerUrl);
		Connection connection=factory.createConnection();
		connection.start();
		
		Session session=connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Topic topic=session.createTopic(topic_name);
		MessageConsumer consumer=session.createConsumer(topic);
		while (true) {
			Message message=consumer.receive();
			if(message!=null && message instanceof TextMessage){
				TextMessage textMessage=(TextMessage)message;
				System.out.println(consumerName+" �յ���Ϣ��"+textMessage.getText());
				System.out.println("��Ϣ isVIP���ԣ�"+textMessage.getBooleanProperty("isVIP"));
			}else{
				break;
			}
		}
		//��ʽ�������һ������(��ʵ��һ��������һ�����߳�)
		/*consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
			
				if(message!=null && message instanceof TextMessage){
					TextMessage textMessage=(TextMessage) message;
					try {
						System.out.println(consumerName+"���ĵõ���Ϣ��" +textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		System.in.read();*/
		System.out.println("�������˳�....");
		consumer.close();
		session.close();
		connection.close();
		
	}

}

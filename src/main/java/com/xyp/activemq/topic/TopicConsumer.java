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
	private static String consumerName="二号订阅消费者： ";
	
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
				System.out.println(consumerName+" 收到消息："+textMessage.getText());
				System.out.println("消息 isVIP属性："+textMessage.getBooleanProperty("isVIP"));
			}else{
				break;
			}
		}
		//方式二：添加一个监听(其实是一个新启动一个子线程)
		/*consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
			
				if(message!=null && message instanceof TextMessage){
					TextMessage textMessage=(TextMessage) message;
					try {
						System.out.println(consumerName+"订阅得到消息：" +textMessage.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			}
		});
		System.in.read();*/
		System.out.println("消费者退出....");
		consumer.close();
		session.close();
		connection.close();
		
	}

}

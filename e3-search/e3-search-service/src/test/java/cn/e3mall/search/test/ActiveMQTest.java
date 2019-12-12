package cn.e3mall.search.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.junit.Test;

public class ActiveMQTest {

	// 消息生产者
	@Test
	public void topicProducer() throws Exception {
		// 创建工厂对象
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://192.168.25.130:61616");
		// 创建一个Connection连接对象
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();

		// 创建session对象
		// 第一个参数：是否开启事务。true：开启事务，第二个参数忽略。
		// 第二个参数：当第一个参数为false时，才有意义。消息的应答模式。1、自动应答2、手动应答。一般是自动应答。
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		// 使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
		Topic topic = session.createTopic("test-1-topic");
		// 创建生产者
		MessageProducer producer = session.createProducer(topic);
		// 创建一个message.创建一个TextMessage
		TextMessage message = session
				.createTextMessage("active topic:---->session1 resp");
		// 发送消息
		producer.send(message);

		// 关闭资源
		producer.close();
		session.close();
		connection.close();
	}

	// 消息消费者
	@Test
	public void topicConsumer() throws Exception {
		// 创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
				"tcp://192.168.25.130:61616");
		// 创建conection对象
		Connection connection = connectionFactory.createConnection();
		// 开启连接
		connection.start();

		// 创建session对象
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		// 使用Session对象创建一个Destination对象（topic、queue），此处创建一个Queue对象。
		Topic topic = session.createTopic("test-1-topic");
		// 创建消费者
		MessageConsumer consumer = session.createConsumer(topic);
		// 接受消息
		consumer.setMessageListener(new MessageListener() {
			public void onMessage(Message message) {
				// 转换子类
				TextMessage textMessage = (TextMessage) message;
				// 获取消息内容
				try {
					String text = textMessage.getText();
					// 打印
					System.out.println(text);
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("consumer waiting...");
		System.in.read();
		// 第九步：关闭资源
		consumer.close();
		session.close();
		connection.close();
	}

}
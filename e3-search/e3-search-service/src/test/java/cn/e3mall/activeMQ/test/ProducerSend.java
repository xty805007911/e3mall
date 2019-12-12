package cn.e3mall.activeMQ.test;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

//生产者
public class ProducerSend {

	@Test
	public void runProducer() throws Exception {
		ApplicationContext app = new ClassPathXmlApplicationContext("/spring/applicationContext-activeMQ.xml");
		//获取模板对象
		JmsTemplate jmsTemplate = (JmsTemplate) app.getBean("jmsTemplate");
		//获取Destination
		Destination destination = (Destination) app.getBean("topicDestination");
		//发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage("springMessage");
				return textMessage;
			}
		});
	}
	@Test
	public void runConsumer() throws Exception {
		ApplicationContext app = new ClassPathXmlApplicationContext("/spring/applicationContext-activeMQ.xml");
		System.out.println("等待接受消息....");
		System.in.read();
	}
	
}

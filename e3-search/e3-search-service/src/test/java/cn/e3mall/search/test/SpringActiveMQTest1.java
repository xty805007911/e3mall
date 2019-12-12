package cn.e3mall.search.test;

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
public class SpringActiveMQTest1 {
	
	@Test
	public void testProducer() throws Exception {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-activeMQ.xml");
		//从spring容器中获取jsmTmeplate对象
		JmsTemplate jmsTemplate = app.getBean(JmsTemplate.class);
		//从spring中获取Destination对象
		Destination destination = (Destination) app.getBean("topicDestination");
		//发送消息
		jmsTemplate.send(destination, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {
				TextMessage message = session.createTextMessage("spring message");
				return message;
			}
		});
	}
}

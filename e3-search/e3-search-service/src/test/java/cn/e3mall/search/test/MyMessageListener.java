package cn.e3mall.search.test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

//消费者接受消息
public class MyMessageListener implements MessageListener{
	
	//接受消息
	public void onMessage(Message message) {
		//取消息内容
		try {
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println(text);
		} catch (JMSException e) {
			e.printStackTrace();
			System.out.println("err");
		}
	}
	
}


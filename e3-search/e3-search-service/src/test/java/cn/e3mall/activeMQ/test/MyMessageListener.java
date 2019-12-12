package cn.e3mall.activeMQ.test;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

//接受消息监听器
public class MyMessageListener implements MessageListener{

	public void onMessage(Message message) {
		try {
			System.out.println("============接收消息开始============");
			TextMessage textMessage = (TextMessage) message;
			String text = textMessage.getText();
			System.out.println(text);
			System.out.println("============接收消息结束============");
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}

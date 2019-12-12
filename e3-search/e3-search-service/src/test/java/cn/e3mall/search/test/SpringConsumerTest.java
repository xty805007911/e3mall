package cn.e3mall.search.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringConsumerTest {
	@Test
	public void testConsumer() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-activeMQ.xml");
		// 等待
		System.out.println("wating..");
		System.in.read();
		System.out.println("read err");
	}
}

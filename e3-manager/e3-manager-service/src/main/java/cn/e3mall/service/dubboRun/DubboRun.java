package cn.e3mall.service.dubboRun;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboRun {

	/**
	 * 不使用tomcat运行dubbo服务
	 * <p>Title: main</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月20日
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext*.xml");
		System.out.println("=====================manager业务层运行dubbo服务=====================");
		while(true) {
			Thread.sleep(1000L);
		}
	}
}
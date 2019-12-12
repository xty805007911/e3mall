package cn.e3mall.jdbc.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.pojo.SearchItem;
import cn.e3mall.search.mapper.TbItemMapperCustomer;

public class Test1 {
	
	@Test
	public void test1() {
		ApplicationContext app = new ClassPathXmlApplicationContext("spring/applicationContext-dao.xml");
		TbItemMapperCustomer mapper = app.getBean(TbItemMapperCustomer.class);
		SearchItem item = mapper.findItemById1(1355561L);
		System.out.println(item.getId());
	}
}

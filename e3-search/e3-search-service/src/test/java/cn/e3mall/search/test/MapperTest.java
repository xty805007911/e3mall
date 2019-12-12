package cn.e3mall.search.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.pojo.SearchItem;
import cn.e3mall.search.mapper.TbItemMapperCustomer;

public class MapperTest {
	
	@Test
	public void test1() {
		ApplicationContext app = new ClassPathXmlApplicationContext("spring/applicationContext-dao.xml");
		TbItemMapperCustomer mapper = app.getBean(TbItemMapperCustomer.class);
		List<SearchItem> list = mapper.selectItemList();
		System.out.println(list.size());
	}

}

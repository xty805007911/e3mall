package cn.e3mall.pageHelper.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemExample;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class PageHelpertest {

	@Test
	public void test1() {
		ApplicationContext app = new ClassPathXmlApplicationContext("spring/applicationContext-dao.xml");
		TbItemMapper mapper = app.getBean(TbItemMapper.class);
		//开始分页
		PageHelper.startPage(2, 10);
		//分页该条数据
		List<TbItem> list = mapper.selectByExample(new TbItemExample());
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(list);
		//总条数
		System.out.println(pageInfo.getTotal());
		//每页显示条数
		System.out.println(pageInfo.getPageSize());
		//总页数
		System.out.println(pageInfo.getPages());
		//该页信息数据
		List<TbItem> pageList = pageInfo.getList();
		for (TbItem tbItem : pageList) {
			System.out.println(tbItem.getTitle());
		}
	}
	/**
	 * 分页查询商品信息
	 */
	@Test
	public void findItemList() {
		int page = 1;
		int rows = 10;
		ApplicationContext app = new ClassPathXmlApplicationContext("spring/applicationContext-dao.xml");
		TbItemMapper itemMapper = app.getBean(TbItemMapper.class);
		//开始分页
		PageHelper.startPage(page, rows);
		//查询所有
		List<TbItem> pageList = itemMapper.selectByExample(new TbItemExample());
		//封装数据
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(pageList);
		//获取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(pageList);
		long total = pageInfo.getTotal();
		result.setTotal(Integer.parseInt(total+""));
		System.out.println(result.getTotal());
	}
}

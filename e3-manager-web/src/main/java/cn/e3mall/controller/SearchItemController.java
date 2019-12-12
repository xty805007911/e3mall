package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.service.SearchItemService;

@Controller
public class SearchItemController {

	@Autowired
	private SearchItemService searchItemService;
	
	/**
	 * 导入商品数据索引
	 * <p>Title: importItemsIndex</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月23日
	 * @return
	 */
	@RequestMapping(value="/index/item/import")
	@ResponseBody
	public E3Result importItemsIndex() {
		E3Result result = searchItemService.importItemsIndex();
		return result;
	}
}

package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.pojo.EasyUITreeNode;
import cn.e3mall.service.ItemCatService;

/**
 * 商品分类表现层
* Title: ItemCatController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月17日
 */
@Controller
public class ItemCatController {
	@Autowired
	private ItemCatService itemCatService;
	
	/**
	 * 获取分类列表
	 * <p>Title: getItemCatList</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月17日
	 * @return
	 */
	@RequestMapping(value="/item/cat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(@RequestParam(defaultValue="0",name="id") Long parentId) {
		List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
		return list;
	}
	
}

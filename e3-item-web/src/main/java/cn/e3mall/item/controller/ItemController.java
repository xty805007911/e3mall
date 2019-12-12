package cn.e3mall.item.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.service.ItemService;

/**
 * 商品表现层
* Title: ItemController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月27日
 */
@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	/**
	 * 商品详细信息
	 * <p>Title: itemInfo</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月27日
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value="/item/{itemId}.html")
	public String itemInfo(@PathVariable Long itemId,Model model) {
		TbItem item = itemService.findItemById(itemId);
		TbItemDesc itemDesc = itemService.findItemDescById(itemId);
		model.addAttribute("item", item);
		model.addAttribute("itemDesc", itemDesc);
		return "item.jsp";
	}
}

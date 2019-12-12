package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.service.ItemService;

/**
 * 商品管理表现层
* Title: ItemController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月14日
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	/** 
	 * 根据id查询商品信息返回json
	 * <p>Title: findItemById</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月14日
	 * @param model
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/item/{id}")
	@ResponseBody
	public TbItem findItemById(Model model,@PathVariable Long id) {
		TbItem item = itemService.findItemById(id);
		return item;
	}
	
	/**
	 * 分页查询所有商品
	 * <p>Title: findItemListByPage</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月18日
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping(value="/item/list")
	@ResponseBody
	public EasyUIDataGridResult findItemListByPage(Integer page,Integer rows) {
		//查询数据
		EasyUIDataGridResult result = itemService.findItemList(page, rows);
		return result;
	}
	
	/**
	 * 添加商品
	 * <p>Title: saveItem</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月18日
	 * @param item
	 * @return
	 */
	@RequestMapping(value="/item/save")
	@ResponseBody
	public E3Result saveItem(TbItem item,String desc) {
		E3Result result = itemService.addItem(item, desc);
		return result;
	}
	
	/**
	 * 删除商品集合
	 * <p>Title: deleteList</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月18日
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/item/delete")
	@ResponseBody
	public E3Result deleteList(String ids) {
		//处理字符串
		String []idsStrArr = ids.split(",");
		Long []idsLongArr = new Long[idsStrArr.length];
		//封装id参数
		int i = 0;
		for (String id : idsStrArr) {
			idsLongArr[i++] = Long.parseLong(id);
		}
		//执行删除业务
		E3Result result = itemService.deleteItemsByIds(idsLongArr);
		return result;
	}
	
	/**
	 * 商品下架
	 * <p>Title: inStock</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月18日
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/item/instock")
	@ResponseBody
	public E3Result inStock(String ids) {
		// 处理字符串
		String[] idsStrArr = ids.split(",");
		Long[] idsLongArr = new Long[idsStrArr.length];
		// 封装id参数
		int i = 0;
		for (String id : idsStrArr) {
			idsLongArr[i++] = Long.parseLong(id);
		}
		E3Result result = itemService.downTOItems(idsLongArr);
		return result;
	}
	
	/**
	 * 商品上架
	 * <p>Title: inStock</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月18日
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/rest/item/reshelf")
	@ResponseBody
	public E3Result reshelfItems(String ids) {
		// 处理字符串
		String[] idsStrArr = ids.split(",");
		Long[] idsLongArr = new Long[idsStrArr.length];
		// 封装id参数
		int i = 0;
		for (String id : idsStrArr) {
			idsLongArr[i++] = Long.parseLong(id);
		}
		E3Result result = itemService.upTOItems(idsLongArr);
		return result;
	}
	
}

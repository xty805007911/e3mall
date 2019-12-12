package cn.e3mall.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;

/**
 * 商品业务层接口
* Title: ItemService <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月14日
 */
public interface ItemService {
	// 根据id查询商品信息
	TbItem findItemById(Long id);
	// 根据id查询商品详细信息
	TbItemDesc findItemDescById(Long id);
	//分页查询所有商品
	EasyUIDataGridResult findItemList(Integer page,Integer rows);
	//添加商品
	E3Result addItem(TbItem item,String desc);
	//删除多个商品
	E3Result deleteItemsByIds(Long []ids);
	//下架多个商品
	E3Result downTOItems(Long []ids);
	//上架多个商品
	E3Result upTOItems(Long []ids);
}

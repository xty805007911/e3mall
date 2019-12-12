package cn.e3mall.service;

import java.util.List;

import cn.e3mall.pojo.EasyUITreeNode;

/**
 * 商品分类业务层接口
* Title: ItemCatService <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月17日
 */
public interface ItemCatService {
	//根据pid查询分类列表
	public List<EasyUITreeNode> getItemCatList(Long parentId);
}

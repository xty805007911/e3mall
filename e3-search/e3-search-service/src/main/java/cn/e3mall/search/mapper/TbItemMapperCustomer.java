package cn.e3mall.search.mapper;

import java.util.List;

import cn.e3mall.pojo.SearchItem;

/**
 * 自定义商品Mapper
* Title: TbItemMapper <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月23日
 */
public interface TbItemMapperCustomer {
	//自定义关联查询商品集合
	List<SearchItem> selectItemList();
	//根据id查询搜索商品信息
	SearchItem findItemById1(Long itemId);
}
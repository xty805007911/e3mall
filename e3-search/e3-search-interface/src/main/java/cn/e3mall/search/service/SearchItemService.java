package cn.e3mall.search.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.SearchResult;

/**
 * 搜索商品业务层接口
* Title: SearchItemService <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月23日
 */
public interface SearchItemService {
	//导入全部商品索引数据
	E3Result importItemsIndex();
	
	//根据关键词查询商品
	SearchResult searchItemListByKeyword(String keyword,Integer page,Integer rows) throws Exception;
	
	//根据商品id导入一个索引文档
	E3Result addDocumentByItemId(Long itemId);

}

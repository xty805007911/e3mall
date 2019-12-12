package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbContent;

/**
 * 内容管理接口
* Title: ContentService <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月20日
 */
public interface ContentService {
	//分页查询内容列表
	EasyUIDataGridResult contentPageQuery(Integer page,Integer rows,Long categoryId);
	//添加内容
	E3Result addContent(TbContent content);
	//删除内容
	E3Result deleteContentById(String ids);
	//根据categoryId查询内容列表
	List<TbContent> selectContentlistByCartgoryId(Long category_id);

}

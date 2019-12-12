package cn.e3mall.content.service;

import java.util.List;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.EasyUITreeNode;
/**
 * 内容分类接口
* Title: ContentCategoryService <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月20日
 */
public interface ContentCategoryService {

	//根据父节点id查询内容分类列表
	List<EasyUITreeNode> findContentCategoryByParentId(Long parentId);
	//添加内容结点
	E3Result addContentNode(String name,Long parentId);
	//结点重命名
	E3Result reNameContentNode(Long id,String name);
	//结点删除
	E3Result deleteContentNode(Long id);
}

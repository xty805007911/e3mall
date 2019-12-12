package cn.e3mall.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.pojo.EasyUITreeNode;

/**
 * 内容分类
* Title: ContentCategoryController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月20日
 */
@Controller
public class ContentCategoryController {
	
	@Autowired
	 private ContentCategoryService contentCategoryService;
	
	/**
	 * 内容分类
	 * <p>Title: list</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月20日
	 */
	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> contentCategorylist(@RequestParam(defaultValue="0",name="id") Long parentId) {
		List<EasyUITreeNode> result = contentCategoryService.findContentCategoryByParentId(parentId);
		return result;
	}
	
	/**
	 * 添加结点
	 * <p>Title: addContentNode</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月20日
	 * @param name
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value="/content/category/create")
	@ResponseBody
	public E3Result addContentNode(String name,Long parentId) {
		E3Result result = contentCategoryService.addContentNode(name, parentId);
		return result;
	}
	
	/**
	 * 重命名
	 * <p>Title: addContentNode</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月20日
	 * @param name
	 * @param parentId
	 * @return
	 */
	@RequestMapping(value="/content/category/update")
	@ResponseBody
	public E3Result reNameContentNode(String name,Long id) {
		E3Result result = contentCategoryService.reNameContentNode(id, name);
		return result;
	}
	
	/**
	 * 删除结点
	 * <p>Title: deleteContentNode</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月20日
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/content/category/delete")
	@ResponseBody
	public E3Result deleteContentNode(Long id) {
		E3Result result = contentCategoryService.deleteContentNode(id);
		return result;
	}
	
	
}

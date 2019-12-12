package cn.e3mall.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbContent;

/**
 * 内容管理表现层
* Title: ContentController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月20日
 */
@Controller
public class ContentController {
	@Autowired
	private ContentService contentService;

	/**
	 * 分页查询内容列表
	 * <p>Title: contentPageQuerylist</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月20日
	 * @param page
	 * @param rows
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value="/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult contentPageQuerylist(Integer page,Integer rows,Long categoryId) {
		EasyUIDataGridResult result = contentService.contentPageQuery(page, rows, categoryId);
		return result;
	}
	
	/**
	 * 添加内容
	 * <p>Title: addContent</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月20日
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/content/save")
	@ResponseBody
	public E3Result addContent(TbContent content) {
		E3Result result = contentService.addContent(content);
		return result;
	}
	
	/**
	 * 删除内容
	 * <p>Title: deleteContent</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月20日
	 * @param ids
	 * @return
	 */
	@RequestMapping(value="/content/delete")
	@ResponseBody
	public E3Result deleteContent(String ids) {
		E3Result result = contentService.deleteContentById(ids);
		return result;
	}
	
}

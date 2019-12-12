package cn.e3mall.portal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.content.service.ContentService;
import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbContent;

/**
 * 首页后缀路径请求
* Title: IndexController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月20日
 */
@Controller
public class IndexController {
	@Autowired
	private ContentService contentService;
	
	@Value("${CATEGORY_ID}")
	private Long CATEGORY_ID;

	/**
	 * 首页展示
	 * <p>Title: showIndex</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月20日
	 * @return
	 */
	@RequestMapping(value="index.html")
	public String showIndex(Model model) {
		List<TbContent> ad1List = contentService.selectContentlistByCartgoryId(CATEGORY_ID);
		model.addAttribute("ad1List", ad1List);
		return "index.jsp";
	}
}

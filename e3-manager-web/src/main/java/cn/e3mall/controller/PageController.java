package cn.e3mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转
* Title: PageController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月16日
 */
@Controller
public class PageController {
	
	/**
	 * 后台首页页面跳转
	 * <p>Title: showIndex</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月16日
	 * @return
	 */
	@RequestMapping("/")
	public String showIndex() {
		return "index.jsp";
	}
	/**
	 * 其他页面跳转
	 * <p>Title: showPage</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月16日
	 * @param pageName
	 * @return
	 */
	@RequestMapping("/{pageName}")
	public String showPage(@PathVariable String pageName) {
		return pageName+".jsp";
	}
}

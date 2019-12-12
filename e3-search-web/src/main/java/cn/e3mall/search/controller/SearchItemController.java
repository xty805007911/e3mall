package cn.e3mall.search.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import cn.e3mall.pojo.SearchResult;
import cn.e3mall.search.service.SearchItemService;

/**
 * 搜索商品表现层
* Title: SearchItemController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月23日
 */
@Controller
public class SearchItemController {
	@Autowired
	private SearchItemService searchItemService;
	
	/**
	 * 根据关键词查询
	 * <p>Title: importItemsIndex</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月23日
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/search.html")
	public String searchItem(String keyword,@RequestParam(defaultValue="1")Integer page,Model model) throws Exception {
		// 需要转码
		keyword = new String(keyword.getBytes("iso8859-1"), "utf-8");
		// 调用Service查询商品信息
		SearchResult result = searchItemService.searchItemListByKeyword(keyword, page, 40);
		// 把结果传递给jsp页面
		model.addAttribute("query", keyword);
		model.addAttribute("totalPages", result.getTotalPage());
		model.addAttribute("recourdCount", result.getCount());
		model.addAttribute("page", page);
		model.addAttribute("itemList", result.getItemList());
		// 返回逻辑视图
		return "/search.jsp";
	}
	
}

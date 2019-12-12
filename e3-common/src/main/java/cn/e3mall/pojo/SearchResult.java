package cn.e3mall.pojo;

import java.io.Serializable;
import java.util.List;
/**
 * 搜索结果集映射
* Title: SearchResult <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月23日
 */
public class SearchResult implements Serializable{

	/** serialVersionUID*/  
	private static final long serialVersionUID = 1L;
	
	private List<SearchItem> itemList;
	private Integer totalPage;
	private Integer count;
	public List<SearchItem> getItemList() {
		return itemList;
	}
	public void setItemList(List<SearchItem> itemList) {
		this.itemList = itemList;
	}
	public Integer getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}

}

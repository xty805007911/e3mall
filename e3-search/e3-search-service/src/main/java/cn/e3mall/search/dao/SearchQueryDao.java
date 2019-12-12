package cn.e3mall.search.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.e3mall.pojo.SearchItem;
import cn.e3mall.pojo.SearchResult;

/**
 * 查询商品持久层
* Title: SearchQueryDao <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月23日
 */
@Repository
public class SearchQueryDao {
	@Autowired
	private HttpSolrServer httpSolrServer;
	
	/**
	 * 根据查询条件查询商品结果
	 * <p>Title: searchItem</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月23日
	 * @param query
	 * @return
	 * @throws SolrServerException 
	 */
	public SearchResult searchItem(SolrQuery query) throws SolrServerException {
		SearchResult result = new SearchResult();
		//根据查询结果返回对象
		QueryResponse response = httpSolrServer.query(query);
		SolrDocumentList documentList = response.getResults();
		//查询总记录数
		long numCount = documentList.getNumFound();
		//设置总记录数
		result.setCount((int)numCount);
		
		//创建商品列表对象
		List<SearchItem> itemList = new ArrayList<SearchItem>();
		
		//取高亮显示的结果
		Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
		//遍历查询结果
		for (SolrDocument solrDocument : documentList) {
			//取商品信息
			SearchItem searchItem = new SearchItem();
			searchItem.setCategory_name((String) solrDocument.get("item_category_name"));
			searchItem.setId((String) solrDocument.get("id"));
			searchItem.setImage((String) solrDocument.get("item_image"));
			searchItem.setPrice((long) solrDocument.get("item_price"));
			searchItem.setSell_point((String) solrDocument.get("item_sell_point"));
			//取高亮结果
			List<String> list = highlighting.get(solrDocument.get("id")).get("item_title");
			String itemTitle = null;
			if(list != null && list.size() > 0) {
				itemTitle = list.get(0);
			}else {
				itemTitle = (String) solrDocument.get("item_title");
			}
			//设置标题高亮
			searchItem.setTitle(itemTitle);
			//添加到集合
			itemList.add(searchItem);
		}
		result.setItemList(itemList);
		return result;
	}

}

package cn.e3mall.search.service.impl;

import java.io.IOException;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.SearchItem;
import cn.e3mall.pojo.SearchResult;
import cn.e3mall.search.dao.SearchQueryDao;
import cn.e3mall.search.mapper.TbItemMapperCustomer;
import cn.e3mall.search.service.SearchItemService;

/**
 * 搜索商品业务层实现
* Title: SearchItemServiceImpl <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月23日
 */
@Service
@Transactional
public class SearchItemServiceImpl implements SearchItemService{
	@Autowired
	private TbItemMapperCustomer itemMapper;
	@Autowired
	private HttpSolrServer httpSolrServer;
	@Autowired
	private SearchQueryDao searchQueryDao;
	
	/**
	 * 导入全部索引
	 */
	public E3Result importItemsIndex() {
		try {
			//查询所有数据
			List<SearchItem> list = itemMapper.selectItemList();
			for (SearchItem data : list) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", data.getId());
				document.addField("item_title", data.getTitle());
				document.addField("item_sell_point", data.getSell_point());
				document.addField("item_price", data.getPrice());
				document.addField("item_image", data.getImage());
				document.addField("item_category_name", data.getCategory_name());
				httpSolrServer.add(document);
			}
			//提交
			httpSolrServer.commit();
			return E3Result.ok();
		} catch (Exception e) {
			return E3Result.build(500, "导入索引失败");
		}
	}

	/**
	 * 根据关键词查询商品
	 */
	public SearchResult searchItemListByKeyword(String keyword,Integer page,Integer rows) throws Exception {
		//创建一个solrquery对象
		SolrQuery query = new SolrQuery();
		//设置查询条件
		query.setQuery(keyword);
		//设置分页条件
		query.setStart((page-1)*rows);
		query.setRows(rows);
		//设置默认搜索域
		query.set("df", "item_title");
		//设置高亮显示
		query.setHighlight(true);
		query.addHighlightField("item_title");
		query.setHighlightSimplePre("<span style='color: red'>");
		query.setHighlightSimplePost("</span>");
		
		//计算总页数
		SearchResult result = searchQueryDao.searchItem(query);
		Integer count = result.getCount();
		int pages = count/rows;
		if(count%rows!=0) pages++;
		result.setTotalPage(pages);
		return result;
	}

	/**
	 * 根据商品id导入一个索引文档
	 */
	public E3Result addDocumentByItemId(Long itemId) {
		//根据id查询
		SearchItem item = itemMapper.findItemById1(itemId);
		SolrInputDocument document = new SolrInputDocument();
		document.addField("id", item.getId());
		document.addField("item_title", item.getTitle());
		document.addField("item_sell_point", item.getSell_point());
		document.addField("item_price", item.getPrice());
		document.addField("item_image", item.getImage());
		document.addField("item_category_name", item.getCategory_name());
		try {
			httpSolrServer.add(document);
			httpSolrServer.commit();
			return E3Result.ok();
		} catch (SolrServerException | IOException e) {
			e.printStackTrace();
			return E3Result.build(500, "导入失败");
		}
	}
	

}

package cn.e3mall.search.test;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrJTest {

	//添加文档
	@Test
	public void test1() throws SolrServerException, IOException {
		//连接服务器
		SolrServer solrServer = new HttpSolrServer("http://192.168.25.129:8080/solr/");
		//创建document
		SolrInputDocument document = new SolrInputDocument();
		//添加域
		document.addField("id", "testTitle");
		document.addField("item_title", "testTitle");
		document.addField("item_sell_point", "testItem_sell_point");
		document.addField("item_price", 12600);
		document.addField("item_image", "test_item_image");
		document.addField("item_category_name", "test_item_category_name");
		//将文档添加到索引库中
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
}

package cn.e3mall.search.test;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class SolrCloudTest {

	@Test
	public void testAdd() throws Exception {
		CloudSolrServer cloudSolrServer = new CloudSolrServer(
				"192.168.25.129:2181,192.168.25.129:2182,192.168.25.129:2183");
		cloudSolrServer.setDefaultCollection("collection1");
		// 创建document
		SolrInputDocument document = new SolrInputDocument();
		// 添加域
		document.addField("id", "testTitle");
		document.addField("item_title", "testTitle");
		document.addField("item_sell_point", "testItem_sell_point");
		document.addField("item_price", 12600);
		document.addField("item_image", "test_item_image");
		document.addField("item_category_name", "test_item_category_name");
		// 将文档添加到索引库中
		cloudSolrServer.add(document);
		// 提交
		cloudSolrServer.commit();
	}
}

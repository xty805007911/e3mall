package cn.e3mall.search.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.search.service.SearchItemService;

/**
 * 商品更改监听器
* Title: ItemChangeListener <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月27日
 */
public class ItemChangeListener implements MessageListener{
	
	@Autowired
	private SearchItemService searchItemService;

	@Override
	public void onMessage(Message message) {
		TextMessage textMessage = null;
		Long itemId = null;
		if(message instanceof TextMessage) {
			try {
				//等待事务提交
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				textMessage = (TextMessage) message;
				String text = textMessage.getText();
				itemId = new Long(text);
				System.out.println("已监听：itemId = " + itemId);
				//添加文档
				E3Result result = searchItemService.addDocumentByItemId(itemId);
				if(result.getStatus() == 200) {
					System.out.println("监听商品更改事件：添加索引成功！");
				}else {
					System.out.println("监听商品更改事件：添加索引失败！");
				}
			} catch (JMSException e) {
				e.printStackTrace();
			}
		}
	}

}

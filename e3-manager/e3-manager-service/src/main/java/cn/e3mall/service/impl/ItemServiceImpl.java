package cn.e3mall.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.IDUtils;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemDescMapper;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbItemDesc;
import cn.e3mall.pojo.TbItemExample;
import cn.e3mall.service.ItemService;

/**
 * 商品业务层实现 Title: ItemServiceImpl
 * <p>
 * Description:
 * <p>
 * 
 * @author Tianyu Xiao
 * @date 2018年11月14日
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private JmsTemplate jmsTemplate;
	//根据id注入
	@Resource
	private Destination topicDestination;
	@Autowired
	private JedisClient jedisClient;
	@Value("${ITEM_INFO_PRE}")
	private String ITEM_INFO_PRE;
	@Value("${ITEM_INFO_EXPIRE}")
	private int ITEM_INFO_EXPIRE;

	/**
	 * 根据id查询
	 */
	public TbItem findItemById(Long id) {
		try {
			//查询缓存
			String json = jedisClient.get(ITEM_INFO_PRE+id+""+"BASE");
			if(json != null && !json.equals("")) {
				System.out.println("商品详情基本查询缓存");
				TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
				return item;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("商品详情基本数据库查询");
		TbItem item = itemMapper.selectByPrimaryKey(id);
		try {
			//设置缓存
			String json = JsonUtils.objectToJson(item);
			jedisClient.set(ITEM_INFO_PRE+id+""+"BASE", json);
			//设置过期时间
			jedisClient.expire(ITEM_INFO_PRE+id+""+"BASE", ITEM_INFO_EXPIRE);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	/**
	 * 分页查询商品信息
	 */
	public EasyUIDataGridResult findItemList(Integer page, Integer rows) {
		// 开始分页
		PageHelper.startPage(page, rows);
		// 查询所有
		List<TbItem> pageList = itemMapper.selectByExample(new TbItemExample());
		// 封装数据
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(pageList);
		// 获取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<TbItem>(pageList);
		long total = pageInfo.getTotal();
		result.setTotal(Integer.parseInt(total + ""));
		return result;
	}

	/**
	 * 添加商品
	 */
	public E3Result addItem(TbItem item, String desc) {

		// 生成商品id
		Long id = IDUtils.genItemId();
		Date date = new Date();

		// 添加商品基本信息
		item.setCreated(new Date());
		// 商品状态，1-正常，2-下架，3-删除
		item.setStatus(new Byte("1"));
		item.setId(id);
		item.setUpdated(date);
		itemMapper.insert(item);

		// 添加商品描述信息
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setCreated(date);
		itemDesc.setItemDesc(desc);
		itemDesc.setItemId(id);
		itemDesc.setUpdated(date);
		itemDescMapper.insert(itemDesc);
		
		//商品添加完成后发送消息
		jmsTemplate.send(topicDestination, new MessageCreator() {
			@Override
			public Message createMessage(Session session) throws JMSException {
				TextMessage textMessage = session.createTextMessage(id + "");
				return textMessage;
			}
		});

		return E3Result.ok();
	}

	/**
	 * 删除多个商品
	 */
	public E3Result deleteItemsByIds(Long []ids) {
		if(ids != null && ids.length>0) {
			for (Long id : ids) {
				itemMapper.deleteByPrimaryKey(id);
				itemDescMapper.deleteByPrimaryKey(id);
			}
			return E3Result.ok();
		}
		return null;
	}

	/**
	 * 下架多个商品
	 */
	public E3Result downTOItems(Long []ids) {
		if(ids != null && ids.length>0) {
			for (Long id : ids) {
				//根据id查询
				TbItem item = itemMapper.selectByPrimaryKey(id);
				item.setStatus(new Byte("0"));
				itemMapper.updateByPrimaryKey(item);
			}
			return E3Result.ok();
		}
		return null;
	}

	/**
	 * 上架多个商品
	 */
	public E3Result upTOItems(Long []ids) {
		if(ids != null && ids.length>0) {
			for (Long id : ids) {
				//根据id查询
				TbItem item = itemMapper.selectByPrimaryKey(id);
				item.setStatus(new Byte("1"));
				itemMapper.updateByPrimaryKey(item);
			}
			return E3Result.ok();
		}
		return null;
	}

	/**
	 * 根据id查询商品详细信息
	 */
	public TbItemDesc findItemDescById(Long id) {
		String key = ITEM_INFO_PRE+id+""+"DESC";
		try {
			//查询缓存
			String json = jedisClient.get(key);
			if(json != null && !json.trim().equals("")) {
				System.out.println("商品详情详细查询缓存");
				TbItemDesc desc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
				return desc;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		System.out.println("商品详情详细数据库查询");
		try {
			//设置缓存
			String json = JsonUtils.objectToJson(itemDesc);
			jedisClient.set(key, json);
			//设置过期时间
			jedisClient.expire(key, ITEM_INFO_EXPIRE);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

}

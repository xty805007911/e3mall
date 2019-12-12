package cn.e3mall.order.service;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbOrderItemMapper;
import cn.e3mall.mapper.TbOrderMapper;
import cn.e3mall.mapper.TbOrderShippingMapper;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.order.service.pojo.OrderInfo;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;

/**
 * 订单业务接口实现
* Title: OrderServiceImpl <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年12月5日
 */
@Service
@Transactional
public class OrderServiceImpl implements OrderService {
	@Autowired
	private TbOrderMapper orderMapper;
	@Autowired
	private TbOrderItemMapper orderItemMapper;
	@Autowired
	private TbOrderShippingMapper orderShippingMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${REDIS_ORDER_ID_GEN_KEY}")
	private String REDIS_ORDER_ID_GEN_KEY;
	@Value("${REDIS_ORDER_ID_GEN_KEY_FIRST_VALUE}")
	private String REDIS_ORDER_ID_GEN_KEY_FIRST_VALUE;
	@Value("${REDIS_ORDER_ITEM_ID_GEN_KET}")
	private String REDIS_ORDER_ITEM_ID_GEN_KET;
	@Value("${REDIS_ORDER_ITEM_ID_GEN_KET_FIRST_VALUE}")
	private String REDIS_ORDER_ITEM_ID_GEN_KET_FIRST_VALUE;

	/**
	 * 生成订单
	 */
	public E3Result createOrder(OrderInfo orderInfo) {
		//生成订单id，通过redis的incr
		//如果redis中自动生成orderid的键不存在，则创建
		if(!jedisClient.exists(REDIS_ORDER_ID_GEN_KEY)) {
			jedisClient.set(REDIS_ORDER_ID_GEN_KEY, REDIS_ORDER_ID_GEN_KEY_FIRST_VALUE);
		}
		//获取orderId
		String orderId = jedisClient.incr(REDIS_ORDER_ID_GEN_KEY).toString();
		//封装订单数据
		orderInfo.setOrderId(orderId);
		//状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
		orderInfo.setStatus(1);
		Date currentDate = new Date();
		orderInfo.setCreateTime(currentDate);
		orderInfo.setUpdateTime(currentDate);
		//1.插入订单表
		orderMapper.insert(orderInfo);
		//2.插入订单-商品表(一个订单包含多个商品)
		List<TbOrderItem> itemList = orderInfo.getOrderItemList();
		if(!jedisClient.exists(REDIS_ORDER_ITEM_ID_GEN_KET)) {
			jedisClient.set(REDIS_ORDER_ITEM_ID_GEN_KET, REDIS_ORDER_ITEM_ID_GEN_KET_FIRST_VALUE);
		}
		for (TbOrderItem item : itemList) {
			String orderItemId = jedisClient.incr(REDIS_ORDER_ITEM_ID_GEN_KET).toString();
			item.setId(orderItemId);
			item.setOrderId(orderId);
			orderItemMapper.insert(item);
		}
		//3.插入订单-物流表
		TbOrderShipping orderShipping = orderInfo.getOrderShipping();
		orderShipping.setOrderId(orderId);
		orderShipping.setCreated(currentDate);
		orderShipping.setUpdated(currentDate);
		orderShippingMapper.insert(orderShipping);
		return E3Result.ok(orderInfo);
	}

}

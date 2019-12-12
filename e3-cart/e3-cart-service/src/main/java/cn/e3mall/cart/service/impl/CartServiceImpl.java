package cn.e3mall.cart.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbItemMapper;
import cn.e3mall.pojo.TbItem;

/**
 * 购物车接口实现
* Title: CartServiceImpl <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年12月1日
 */
@Service
@Transactional
public class CartServiceImpl implements CartService{
	@Autowired
	private JedisClient jedisClient;
	@Autowired
	private TbItemMapper itemMapper;
	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;

	/**
	 * 添加购物车
	 */
	public E3Result addCart(long userId, long itemId, int num) {
		//定义购物车中hash的key
		String key = REDIS_CART_PRE+":"+userId+"";
		//判断商品是否存在redis中
		Boolean keyIsExist = jedisClient.hexists(key, itemId+"");
		//如果itemId不存在于数据库中，添加商品到数据库
		if(!keyIsExist) {
			//根据商品id查询商品
			TbItem item = itemMapper.selectByPrimaryKey(itemId);
			//封装购物车数据
			item.setNum(num);
			item.setImage(item.getImage()==null||item.getImage().equals("")?"":item.getImage().split(",")[0]);
			//写入redis
			String json = JsonUtils.objectToJson(item);
			jedisClient.hset(key, itemId+"", json);
			return E3Result.ok();
		}
		//如果itemId存在于数据库中，数量相加，更新数据库
		if(keyIsExist) {
			String json = jedisClient.hget(key, itemId+"");
			TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
			//封装购物车数据
			item.setNum(num+item.getNum());
			item.setImage(item.getImage()==null||item.getImage().equals("")?"":item.getImage().split(",")[0]);
			json = JsonUtils.objectToJson(item);
			jedisClient.hset(key, itemId+"", json);
		}
		return E3Result.ok();
	}
	
	/**
	 * 查看购物车
	 * <p>Title: cartList</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年12月2日
	 * @return
	 */
	public E3Result cartList(long userId) {
		String key = REDIS_CART_PRE + ":" + userId;
		List<TbItem> itemList = new LinkedList<TbItem>();
		
		//获取该用户存在的所有商品
		List<String> jsonList = jedisClient.hvals(key);
		for (String json : jsonList) {
			TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
			itemList.add(item);
		}
		return E3Result.ok(itemList);
	}
	
	/**
	 * 合并购物车
	 * <p>Title: mergeCart</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年12月2日
	 * @param userId
	 * @param cookieItemList
	 * @return
	 */
	public E3Result mergeCart(long userId,List<TbItem> cookieItemList) {
		//直接调用add方法
		for (TbItem item : cookieItemList) {
			addCart(userId, item.getId(), item.getNum());
		}
		return E3Result.ok();
	}

	/**
	 * 修改购物车
	 */
	public E3Result updateCart(long userId, long itemId ,int num) {
		String key = REDIS_CART_PRE + ":" + userId;
		String field = itemId+"";
		//从数据库中获取商品
		String json = jedisClient.hget(key, field);
		TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
		//修改
		item.setNum(num);
		json = JsonUtils.objectToJson(item);
		jedisClient.hset(key, field, json);
		return E3Result.ok();
	}

	/**
	 * 删除
	 */
	public E3Result deleteCartItem(long userId, long itemId) {
		String key = REDIS_CART_PRE + ":" + userId;
		String field = itemId+"";
		jedisClient.hdel(key, field);
		return E3Result.ok();
	}
	

}

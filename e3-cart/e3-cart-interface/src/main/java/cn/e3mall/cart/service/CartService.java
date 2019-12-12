package cn.e3mall.cart.service;

import java.util.List;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbItem;

/**
 * 购物车业务接口
* Title: CartService <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年12月1日
 */
public interface CartService {
	//添加购物车
	E3Result addCart(long userId,long itemId,int num);
	//查看购物车
	E3Result cartList(long userId);
	//合并购物车
	E3Result mergeCart(long userId,List<TbItem> cookieItemList);
	//修改购物车
	E3Result updateCart(long userId,long itemId,int num);
	//删除购物车商品
	E3Result deleteCartItem(long userId,long itemId);
}

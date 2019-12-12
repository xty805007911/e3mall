package cn.e3mall.cart.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.service.ItemService;

/**
 * 购物车表现层接口
* Title: CartController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年12月1日
 */
@Controller
public class CartController {

	//注入service
	@Autowired
	private CartService cartService;
	@Autowired
	private ItemService itemService;
	//存放购物车cookie的键
	@Value("${COOKIE_KEY}")
	private String COOKIE_KEY;
	@Value("${COOKIE_EXPIRE_TIME}")
	private int COOKIE_EXPIRE_TIME;
	@Value("REDIS_CART_PRE")
	private String REDIS_CART_PRE;
	
	 
	
	/**
	 * 添加购物车
	 * <p>Title: addCartItem</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年12月1日
	 * @return
	 */
	@RequestMapping(value="/cart/add/{itemId}.html")
	public String addCartItem(@PathVariable Long itemId,@RequestParam(name="num",defaultValue="1") int num,HttpServletRequest request,HttpServletResponse response) {
		//=====================user存在时===================
		//从拦截器中获取user
		TbUser user = (TbUser) request.getAttribute("user");
		//如果用户已登陆，直接将购物车保存到redis中
		if(user != null) {
			cartService.addCart(user.getId(), itemId, num);
			return "/cartSuccess.jsp";
		}
		//=====================user存在时===================
		//从cookie中查询商品，如果不存在，将商品信息json添加到cookie
		List<TbItem> cartItemList = getCartListFromCookie(request);
		if(cartItemList == null || cartItemList.size() == 0) {
			TbItem item = itemService.findItemById(itemId);
			item.setNum(num);
			item.setImage(item.getImage()==null||"".equals(item.getImage())?"":item.getImage().split(",")[0]);
			cartItemList.add(item);
			String json = JsonUtils.objectToJson(cartItemList);
			CookieUtils.setCookie(request, response, COOKIE_KEY, json, COOKIE_EXPIRE_TIME, true);
			return "/cartSuccess.jsp";
		}
		//如果存在
		//遍历购物车列表，if有id相同的商品，数量相加
		//判断添加的商品是否存在标记
		boolean hasItem = false;
		for (TbItem item : cartItemList) {
			if(item.getId().longValue() == itemId) {
				item.setNum(num+item.getNum());
				hasItem = true;
				break;
			}
		}
		//if没有id相同的商品，添加商品到list
		if(!hasItem) {
			TbItem item = itemService.findItemById(itemId);
			//设置新添加商品的属性
			item.setNum(num);
			item.setImage(item.getImage()==null||"".equals(item.getImage())?"":item.getImage().split(",")[0]);
			//添加到购物车
			cartItemList.add(item);
		}
		//将购物车写入cookie
		CookieUtils.setCookie(request, response, COOKIE_KEY, JsonUtils.objectToJson(cartItemList), COOKIE_EXPIRE_TIME, true);
		return "/cartSuccess.jsp";
	}
	
	/**
	 * 获取cookie中的购物车
	 * <p>Title: getCartListFromCookie</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年12月1日
	 * @param request
	 * @return
	 */
	private List<TbItem> getCartListFromCookie(HttpServletRequest request) {
		String json = CookieUtils.getCookieValue(request, COOKIE_KEY, true);
		if(json != null && !json.trim().equals("")) {
			List<TbItem> cartItemList = JsonUtils.jsonToList(json, TbItem.class);
			return cartItemList;
		}else {
			return new ArrayList<TbItem>();
		}
	}
	/**
	 * 购物车列表
	 * <p>Title: cartList</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年12月2日
	 * @return
	 */
	@RequestMapping(value="/cart/cart.html")
	public String cartList(HttpServletRequest request,HttpServletResponse response) {
		// =====================user存在时===================
		// 从拦截器中获取user
		TbUser user = (TbUser) request.getAttribute("user");
		// 如果用户已登陆，从redis中查询
		if (user != null) {
			//合并购物车
			//获取cookie中的购物车列表
			List<TbItem> cookieCartList = getCartListFromCookie(request);
			if(cookieCartList.size() > 0) {
				cartService.mergeCart(user.getId(), cookieCartList);
			}
			//****清除cookie中的购物车****
			CookieUtils.deleteCookie(request, response, COOKIE_KEY);
			E3Result result = cartService.cartList(user.getId());
			//获取数据库中的列表
			@SuppressWarnings("unchecked")
			List<TbItem> itemList = (List<TbItem>) result.getData();
			//打到页面
			request.setAttribute("cartList", itemList);
			return "cart.jsp";
		}
		// =====================user存在时===================
		
		List<TbItem> list = getCartListFromCookie(request);
		request.setAttribute("cartList", list);
		return "cart.jsp";
	}
	/**
	 * 更新购物车数量
	 * <p>Title: updateCart</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年12月2日
	 * @param itemId
	 * @param num
	 * @return
	 */
	@RequestMapping(value="cart/update/num/{itemId}/{num}.action")
	public String updateCart(@PathVariable Long itemId,@PathVariable int num,HttpServletRequest request,HttpServletResponse response) {
		// =====================user存在时===================
		// 从拦截器中获取user
		TbUser user = (TbUser) request.getAttribute("user");
		// 如果用户已登陆，直接将购物车保存到redis中
		if (user != null) {
			cartService.updateCart(user.getId(), itemId, num);
			return "cart.jsp";
		}
		// =====================user存在时===================
		
		//获取购物车列表
		List<TbItem> itemList = getCartListFromCookie(request);
		//遍历购物车，if找到id为itemId的商品，设置数量
		for (TbItem item : itemList) {
			if(item.getId().longValue() == itemId) {
				item.setNum(num);
				break;
			}
		}
		//写入cookie
		CookieUtils.setCookie(request, response, COOKIE_KEY, JsonUtils.objectToJson(itemList), COOKIE_EXPIRE_TIME, true);
		return "cart.jsp";
	}
	
	/**
	 * 删除商品
	 * <p>Title: deleteCartItemById</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年12月2日
	 * @return
	 */
	@RequestMapping(value="/cart/delete/{itemId}.html")
	public String deleteCartItemById(@PathVariable Long itemId,HttpServletRequest request,HttpServletResponse response) {
		// =====================user存在时===================
		// 从拦截器中获取user
		TbUser user = (TbUser) request.getAttribute("user");
		// 如果用户已登陆，直接将购物车保存到redis中
		if (user != null) {
			cartService.deleteCartItem(user.getId(), itemId);
			return "redirect:/cart/cart.html";
		}
		// =====================user存在时===================
		
		//获取cookie中的购物车列表
		List<TbItem> cartList = getCartListFromCookie(request);
		//遍历购物车列表，如果找到该商品，cartList移除
		for (TbItem tbItem : cartList) {
			if(tbItem.getId().longValue() == itemId) {
				cartList.remove(tbItem);
				break;
			}
		}
		//写入cookie
		CookieUtils.setCookie(request, response, COOKIE_KEY, JsonUtils.objectToJson(cartList), COOKIE_EXPIRE_TIME, true);
		return "redirect:/cart/cart.html";
	}
	
}

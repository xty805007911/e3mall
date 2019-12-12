package cn.e3mall.order.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.order.service.OrderService;
import cn.e3mall.order.service.pojo.OrderInfo;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;

/**
 * 订单表现层
* Title: OrderController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年12月5日
 */
@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	@Autowired
	private CartService cartService;
	
	/**
	 * 订单列表
	 * <p>Title: orderCart</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年12月6日
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/order/order-cart.html")
	public String orderCart(HttpServletRequest request) {
		//获取User
		TbUser user = (TbUser) request.getAttribute("user");
		//获取购物车列表
		E3Result result = cartService.cartList(user.getId());
		@SuppressWarnings("unchecked")
		List<TbItem> cartlist = (List<TbItem>) result.getData();
		request.setAttribute("cartList", cartlist);
		return "order-cart.jsp";
	}
	
	/**
	 * 提交订单
	 * <p>Title: createOrder</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年12月6日
	 * @return
	 */
	@RequestMapping(value="/order/create.html")
	public String createOrder(OrderInfo orderInfo,HttpServletRequest request) {
		TbUser user = (TbUser) request.getAttribute("user");
		orderInfo.setUserId(user.getId());
		orderInfo.setBuyerNick(user.getUsername());
		//通过service查询
		E3Result result = orderService.createOrder(orderInfo);
		orderInfo = (OrderInfo) result.getData();
		
		//打到页面上
		request.setAttribute("orderId", orderInfo.getOrderId());
		request.setAttribute("payment", orderInfo.getPayment());
		return "success.jsp";
	}

}

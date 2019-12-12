package cn.e3mall.order.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.order.service.pojo.OrderInfo;
import cn.e3mall.pojo.TbOrder;

/**
 * 订单业务接口
* Title: OrderService <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年12月5日
 */
public interface OrderService {
	//生成订单
	E3Result createOrder(OrderInfo orderInfo);
}

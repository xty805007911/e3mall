package cn.e3mall.order.service.pojo;

import java.io.Serializable;
import java.util.List;

import cn.e3mall.pojo.TbOrder;
import cn.e3mall.pojo.TbOrderItem;
import cn.e3mall.pojo.TbOrderShipping;

/**
 * 订单详细
* Title: OrderInfo <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年12月6日
 */
public class OrderInfo extends TbOrder implements Serializable{
	private static final long serialVersionUID = 1L;
	
	//一个订单有多个商品信息
	private List<TbOrderItem> orderItemList;
	//一个订单对应一个物流信息
	private TbOrderShipping orderShipping;
	public List<TbOrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<TbOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}
	public TbOrderShipping getOrderShipping() {
		return orderShipping;
	}
	public void setOrderShipping(TbOrderShipping orderShipping) {
		this.orderShipping = orderShipping;
	}
}

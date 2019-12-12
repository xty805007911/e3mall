package cn.e3mall.order.interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.cart.service.CartService;
import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbItem;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * 用户登陆拦截器
* Title: LoginInterceptor <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年12月5日
 */
public class LoginInterceptor implements HandlerInterceptor{
	@Autowired
	private TokenService tokenService;
	@Autowired
	private CartService cartService;
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	@Value("${SSO_URL}")
	private String SSO_URL;
	@Value("${REDIS_CART_PRE}")
	private String REDIS_CART_PRE;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//从cookie中取token
		String token = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
		//如果不存在，重定向跳转到登陆页面
		if(!StringUtils.isNotBlank(token)) {
			response.sendRedirect(SSO_URL + "/page/login" + "?redirect=" + request.getRequestURL());
			return false;
		}
		//如果存在
		//从redis中根据token查询user
		E3Result result = tokenService.getUserInfoByToken(token);
		//如果user不存在
		//跳转到登陆页面
		if(result.getStatus() != 200) {
			response.sendRedirect(SSO_URL + "/page/login" + "?redirect=" + request.getRequestURL());
			return false;
		}
		
		//如果user存在，将信息保存到req中，放行
		TbUser user = (TbUser)result.getData();
		request.setAttribute("user", user);
		
		//判断cookie中是否有商品，有的话合并购物车
		String cartList = CookieUtils.getCookieValue(request, REDIS_CART_PRE, true);
		//如果cookie中的购物车不为空
		if(StringUtils.isNotBlank(cartList)) {
			//合并购物车
			cartService.mergeCart(user.getId(), JsonUtils.jsonToList(cartList, TbItem.class));
		}
		//放行
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		if(ex != null) {
			ex.printStackTrace();
		}
	}

}

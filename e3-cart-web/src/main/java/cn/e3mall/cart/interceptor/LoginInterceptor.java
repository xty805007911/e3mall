package cn.e3mall.cart.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * 用户登陆拦截器
* Title: LoginInterceptor <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年12月2日
 */
public class LoginInterceptor implements HandlerInterceptor {
	
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	
	@Autowired
	private TokenService tokenService;

	// 业务处理
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		//获取cookie中的token
		String token = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
		//如果token不存在，直接放行
		if(token == null || "".equals(token.trim())) {
			return true;
		}
		//如果token存在
		//根据token查询用户信息(调用sso服务)
		E3Result result = tokenService.getUserInfoByToken(token);
		//如果没有查询到，放行
		if(result == null || result.getStatus() != 200) {
			return true;
		}
		//如果查询到，将user设置request域
		TbUser user  = (TbUser)result.getData();
		request.setAttribute("user", user);
		//放行
		return true;
	}

	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// 执行handler方法之后，并且是返回ModelAndView对象之前

	}

	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// 返回ModelAndView之后。可以捕获异常。
		if(ex != null)
			ex.printStackTrace();
	}
}

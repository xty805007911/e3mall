package cn.e3mall.sso.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.e3mall.common.utils.CookieUtils;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.LoginService;
import cn.e3mall.sso.service.RegisterService;
import cn.e3mall.sso.service.TokenService;

/**
 * 用户管理表现层
* Title: UserController <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月28日
 */
@Controller
public class UserController {
	@Autowired
	private LoginService loginService;
	@Autowired
	private TokenService tokenService;
	@Autowired
	private RegisterService registerService;
	@Value("${COOKIE_TOKEN_KEY}")
	private String COOKIE_TOKEN_KEY;
	
	/**
	 * 登陆页面显示
	 * <p>Title: showLogin</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月28日
	 * @return
	 */
	@RequestMapping(value={"page/login"})
	public String showLogin(String redirect,HttpServletRequest request) {
		request.setAttribute("redirect", redirect);
		return "login.jsp";
	}
	
	/**
	 * 注册页面显示
	 * <p>Title: showRegister</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月28日
	 * @return
	 */
	@RequestMapping(value={"page/register"})
	public String showRegister() {
		return "register.jsp";
	}
	
	/**
	 * ajax校验注册数据
	 * <p>Title: ajaxCheckRegisterData</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月29日
	 * @param param
	 * @param type
	 * @return
	 */
	@RequestMapping(value="/user/check/{param}/{type}")
	@ResponseBody
	public E3Result ajaxCheckRegisterData(@PathVariable String param ,@PathVariable Integer type) {
		E3Result result = registerService.checkData(param, type);
		return result;
	}
	
	/**
	 * 用户注册功能
	 * <p>Title: userRegister</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月29日
	 * @param user
	 * @return
	 */
	@RequestMapping(value="/user/register")
	@ResponseBody
	public E3Result userRegister(TbUser user) {
		E3Result result = registerService.register(user);
		return result;
	}
	
	/**
	 * 用户单点登陆
	 * <p>Title: userLogin</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月29日
	 * @param username
	 * @param password
	 * @return
	 */
	@RequestMapping(value="/user/login",method=RequestMethod.POST)
	@ResponseBody
	public E3Result userLogin(String username,String password,HttpServletRequest request,HttpServletResponse response) {
		E3Result result = loginService.login(username, password);
		if(result != null && result.getData() != null) {
			//将token放置cookie
			String token = result.getData().toString();//uuid
			CookieUtils.setCookie(request, response, COOKIE_TOKEN_KEY, token);
		}
		return result;
	}
	
	/**
	 * 根据token获取用户信息
	 * <p>Title: userInfo</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月29日
	 * @return
	 */
	@RequestMapping(value="/user/token/{token}")
	@ResponseBody
	public String userInfoByToken(@PathVariable String token,String callback,HttpServletRequest request) {
		E3Result result = tokenService.getUserInfoByToken(token);
		//解决跨域问题
		if(callback!=null && !callback.trim().equals("")) {
			String strResult = callback + "(" + JsonUtils.objectToJson(result)+");";
			return strResult;
		}
		return JsonUtils.objectToJson(result);
	}
	/**
	 * 安全退出
	 * <p>Title: userLogout</p>  
	 * <p>Description: </p>  
	 * @author Tianyu Xiao  
	 * @date 2018年11月29日
	 * @return
	 */
	@RequestMapping(value="/user/logout")
	@ResponseBody
	public E3Result userLogout(String token,HttpServletRequest request) {
		//获取cookie中的token
		String value = CookieUtils.getCookieValue(request, COOKIE_TOKEN_KEY);
		tokenService.userLoginout(value);
		return null;
	}
	
}

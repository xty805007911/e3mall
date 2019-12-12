package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;

/**
 * 登陆业务接口
* Title: LoginService <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月28日
 */
public interface LoginService {
	
	//根据id查询
	public TbUser findById(Long id);
	
	//登陆
	public E3Result login(String username,String password);
	
	
	
}

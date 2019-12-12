package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;

/**
 * 处理tokenservice接口
* Title: TokenService <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月29日
 */
public interface TokenService {
	
	//根据token取用户信息
	E3Result getUserInfoByToken(String token);
	//安全退出
	E3Result userLoginout(String token);
}

package cn.e3mall.sso.service;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.pojo.TbUser;

/**
 * 注册业务接口
* Title: RegisterService <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月29日
 */
public interface RegisterService {
	
	//校验注册数据
	E3Result checkData(String param, Integer type);
	//注册
	E3Result register(TbUser user);
}

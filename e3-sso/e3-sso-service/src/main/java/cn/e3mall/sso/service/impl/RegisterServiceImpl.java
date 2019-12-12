package cn.e3mall.sso.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.RegisterService;

/**
 * 注册业务实现类
* Title: RegisterServiceImpl <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月29日
 */
@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {
	@Autowired
	private TbUserMapper userMapper;

	/**
	 * 校验注册数据
	 */
	public E3Result checkData(String param, Integer type) {
		//创建查询条件对象
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		
		//type :  1:用户名		2：手机号		3：邮箱
		if(type == 1) {
			String username = param.trim();
			criteria.andUsernameEqualTo(username);
		} else if(type == 2) {
			String phone = param.trim();
			criteria.andPhoneEqualTo(phone);
		} else if( type ==3) {
			String email = param.trim();
			criteria.andEmailEqualTo(email);
		} else {//防止用户自定义输入参数
			return E3Result.build(400, "非法参数异常");
		}
		//执行查询
		List<TbUser> list = userMapper.selectByExample(example);
		//如果对象不存在，返回true
		if(list == null || list.size() == 0) {
			return E3Result.ok(true);
		}
		//如果对象存在，返回false
		return E3Result.ok(false);
	}

	/**
	 * 注册
	 */
	public E3Result register(TbUser user) {
		//数据封装
		String username = user.getUsername()==null?"":user.getUsername().trim();
		String phone = user.getPhone()==null?"":user.getPhone().trim();
		String email = user.getEmail()==null?"":user.getEmail().trim();
		String password = user.getPassword();
		//加密
		password = DigestUtils.md5DigestAsHex(password.getBytes());
		Date currentDate = new Date();
		
		user.setPassword(password);
		user.setUsername(username);
		user.setPhone(phone);
		user.setUpdated(currentDate);
		user.setCreated(currentDate);
		//后端二次校验
		//type :  1:用户名		2：手机号		3：邮箱
		E3Result result = checkData(username,1);
		if(!(boolean)result.getData()) {
			return E3Result.build(400, "用户名已被占用");
		}
		result = checkData(phone, 2);
		if(!(boolean)result.getData()) {
			return E3Result.build(400, "手机号已被占用");
		}
		result = checkData(email, 2);
		if(!(boolean)result.getData()) {
			return E3Result.build(400, "邮箱被占用");
		}
		//添加
		userMapper.insert(user);
		return E3Result.ok();
	}

}

package cn.e3mall.sso.service.impl;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;







import org.springframework.util.DigestUtils;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.mapper.TbUserMapper;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.pojo.TbUserExample;
import cn.e3mall.pojo.TbUserExample.Criteria;
import cn.e3mall.sso.service.LoginService;

/**
 * 登陆业务实现
* Title: LoginServiceImpl <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月28日
 */
@Service
@Transactional
public class LoginServiceImpl implements LoginService{
	@Autowired
	private TbUserMapper userMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${PREFIX}")
	private String PREFIX;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	/**
	 * 根据id查询
	 */
	public TbUser findById(Long id) {
		TbUser user = userMapper.selectByPrimaryKey(id);
		return user;
	}

	/**
	 * 登陆
	 */
	public E3Result login(String username, String password) {
		//处理前端请求数据
		username = username == null?"":username.trim();
		password = password == null?DigestUtils.md5DigestAsHex("".getBytes()):DigestUtils.md5DigestAsHex(password.trim().getBytes());
		
		//创建查询条件
		TbUserExample example = new TbUserExample();
		Criteria criteria = example.createCriteria();
		
		//根据用户名查询
		criteria.andUsernameEqualTo(username);
		List<TbUser> list = userMapper.selectByExample(example);
		if(list == null || list.size() == 0) {
			return E3Result.build(400, "用户名错误");
		}
		TbUser user = list.get(0);
		//根据密码查询
		if(!password.equals(user.getPassword())) {
			return E3Result.build(400, "密码错误");
		}
		
		//生成token
		String token = UUID.randomUUID().toString();
		user.setPassword(null);
		String json = JsonUtils.objectToJson(user);
		
		//redis中的key
		String key = PREFIX + ":" + token;
		//设置redis中的key与value
		jedisClient.set(key, json);
		//设置过期时间
		jedisClient.expire(key, SESSION_EXPIRE);
		return E3Result.ok(token);
	}

}

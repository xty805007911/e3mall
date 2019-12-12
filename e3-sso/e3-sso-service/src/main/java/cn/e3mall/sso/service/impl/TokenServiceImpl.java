package cn.e3mall.sso.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.pojo.TbUser;
import cn.e3mall.sso.service.TokenService;

/**
 * 处理tokenservice业务实现
* Title: TokenServiceImpl <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月29日
 */
@Service
@Transactional
public class TokenServiceImpl implements TokenService{
	@Autowired
	private JedisClient jedisClient;
	@Value("${PREFIX}")
	private String PREFIX;
	@Value("${SESSION_EXPIRE}")
	private Integer SESSION_EXPIRE;

	/**
	 * 根据token取用户信息
	 */
	public E3Result getUserInfoByToken(String token) {
		if(token == null) {
			return E3Result.build(400, "用户session信息已过期");
		}
		String redisKey = PREFIX + ":" + token;
		//从redis中获取用户信息json
		String userJson = jedisClient.get(redisKey);
		//设置redis的过期时间
		jedisClient.expire(redisKey, SESSION_EXPIRE);
		TbUser user = JsonUtils.jsonToPojo(userJson, TbUser.class);
		return E3Result.ok(user);
	}

	/**
	 * 安全退出
	 */
	public E3Result userLoginout(String token) {
		if(token == null) {
			return E3Result.build(201, "当前用户不存在");
		}
		String key = PREFIX +":" +  token;
		jedisClient.del(key);
		return E3Result.ok();
	}

}

package cn.e3mall.test.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.jedis.JedisClientPool;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {

	//单机版
	@Test
	public void test1() {
		//获取jedis实例
		Jedis jedis = new Jedis("192.168.25.129", 6379);
		//操作
		jedis.set("jedisTest", "my first redis");
		String res = jedis.get("jedisTest");
		System.out.println(res);
		//关流
		jedis.close();
	}
	
	//连接池
	@Test
	public void test2() {
		//创建连接池
		JedisPool jedisPool = new JedisPool("192.168.25.129", 6379);
		//获得一个链接
		Jedis jedis = jedisPool.getResource();
		//操作
		String res = jedis.get("jedisTest");
		System.out.println(res);
		//关流
		jedis.close();
		jedisPool.close();
	}
	
	//集群版
	@Test
	public void test3() {
		//创建集群连接对象
		Set<HostAndPort> nodes = new HashSet<>();
		//添加结点
		nodes.add(new HostAndPort("192.168.25.129", 7001));
		nodes.add(new HostAndPort("192.168.25.129", 7002));
		nodes.add(new HostAndPort("192.168.25.129", 7003));
		nodes.add(new HostAndPort("192.168.25.129", 7004));
		nodes.add(new HostAndPort("192.168.25.129", 7005));
		nodes.add(new HostAndPort("192.168.25.129", 7006));
		//创建jedis操作对象
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("test1", "123");
		String res = jedisCluster.get("test1");
		System.out.println(res);
		jedisCluster.close();
	}
	
	@Test
	public void testSingle() {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClientPool pool =  (JedisClientPool) app.getBean("jedisClientPool");
		String res = pool.get("jedisTest");
		System.out.println(res);
	}
	@Test
	public void testCluster() {
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-redis.xml");
		JedisClient jedis =  (JedisClient) app.getBean(JedisClient.class);
		String res = jedis.get("test1");
		System.out.println(res);
	}
}

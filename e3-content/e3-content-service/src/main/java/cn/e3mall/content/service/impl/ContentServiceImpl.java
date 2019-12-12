package cn.e3mall.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

import cn.e3mall.common.jedis.JedisClient;
import cn.e3mall.common.utils.E3Result;
import cn.e3mall.common.utils.JsonUtils;
import cn.e3mall.content.service.ContentService;
import cn.e3mall.mapper.TbContentMapper;
import cn.e3mall.pojo.EasyUIDataGridResult;
import cn.e3mall.pojo.TbContent;
import cn.e3mall.pojo.TbContentExample;
import cn.e3mall.pojo.TbContentExample.Criteria;

/**
 * 内容管理接口实现
* Title: ContentServiceImpl <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月20日
 */
@Service
@Transactional
public class ContentServiceImpl implements ContentService {
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private JedisClient jedisClient;
	@Value("${CONTENT_KEY}")
	private String CONTENT_KEY;
	

	/**
	 * 分页查询内容列表
	 */
	public EasyUIDataGridResult contentPageQuery(Integer page, Integer rows, Long categoryId) {
		//开始分页
		PageHelper.startPage(page, rows);
		//拼接结果集查询条件
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(categoryId);
		List<TbContent> list = contentMapper.selectByExample(example);
		//封装结果集
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		//分页详情
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		Integer total = Integer.parseInt(pageInfo.getTotal()+"");
		result.setTotal(total);
		return result;
	}

	/**
	 * 添加内容
	 */
	public E3Result addContent(TbContent content) {
		//补全数据
		Date date = new Date();
		content.setCreated(date);
		content.setUpdated(date);
		contentMapper.insertSelective(content);
		//更新缓存
		jedisClient.hdel(CONTENT_KEY, content.getCategoryId()+"");
		return E3Result.ok();
	}

	/**
	 * 删除内容
	 */
	public E3Result deleteContentById(String ids) {
		//处理参数中接受的字符串
		if(ids == null || ids.length() == 0) {
			return null;
		}
		//分割字符串
		String[] arr = ids.split(",");
		//查询内容对象
		TbContent content = contentMapper.selectByPrimaryKey(Long.parseLong(arr[0]));
		String cId = content.getCategoryId()+"";
		
		for (String idStr : arr) {
			Long id = Long.parseLong(idStr);
			//根据id删除
			contentMapper.deleteByPrimaryKey(id);
		}
		//更新缓存
		jedisClient.hdel(CONTENT_KEY, cId);
		return E3Result.ok();
	}

	/**
	 * 根据categoryId查询内容列表
	 */
	public List<TbContent> selectContentlistByCartgoryId(Long category_id) {
		// 查询缓存
		try {
			String json = jedisClient.hget(CONTENT_KEY, category_id + "");
			if (json != null && !json.trim().equals("")) {
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				System.out.println("loger--------------------->查询缓存");
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 查询数据库
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(category_id);
		List<TbContent> list = contentMapper.selectByExample(example);
		System.out.println("loger--------------------->查询数据库");
		// 添加缓存
		try {
			String json = JsonUtils.objectToJson(list);
			jedisClient.hset(CONTENT_KEY, category_id+"", json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}

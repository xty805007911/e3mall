package cn.e3mall.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.text.html.parser.ContentModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.e3mall.common.utils.E3Result;
import cn.e3mall.content.service.ContentCategoryService;
import cn.e3mall.mapper.TbContentCategoryMapper;
import cn.e3mall.pojo.EasyUITreeNode;
import cn.e3mall.pojo.TbContentCategory;
import cn.e3mall.pojo.TbContentCategoryExample;
import cn.e3mall.pojo.TbContentCategoryExample.Criteria;

/**
 * 内容分类业务层实现
* Title: ContentCategoryServiceImpl <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月20日
 */
@Service
@Transactional
public class ContentCategoryServiceImpl implements ContentCategoryService{
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;

	/**
	 * 根据父节点id查询内容分类
	 */
	public List<EasyUITreeNode> findContentCategoryByParentId(Long parentId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//内容分类集合
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		
		
		//封装数据
		List<EasyUITreeNode> resultList = new ArrayList<EasyUITreeNode>();
		for (TbContentCategory category : list) {
			EasyUITreeNode result = new EasyUITreeNode();
			result.setId(category.getId());
			result.setText(category.getName());
			result.setState(category.getIsParent()?"closed":"open");
			resultList.add(result);
		}
		return resultList;
	}

	/**
	 * 添加内容结点
	 */
	public E3Result addContentNode(String name, Long parentId) {
		// 添加结点
		Date date = new Date();
		TbContentCategory category = new TbContentCategory();
		category.setCreated(date);
		category.setIsParent(false);
		category.setName(name);
		category.setParentId(parentId);
		category.setSortOrder(1);
		// 1正常 2删除
		category.setStatus(1);
		category.setUpdated(date);
		//插入数据
		contentCategoryMapper.insertSelective(category);
		// 根据该结点的父id查询内容
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(parentId);
		// 如果父节点是主结点，不做处理；
		// 如果父节点是根节点，将根结点置为主结点
		if (parentNode.getIsParent() == false) {
			parentNode.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(parentNode);
		}
		return E3Result.ok(category);
	}

	/**
	 * 重命名
	 */
	public E3Result reNameContentNode(Long id, String name) {
		//根据id查询
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		//重命名
		category.setName(name);
		//更新数据
		contentCategoryMapper.updateByPrimaryKeySelective(category);
		return E3Result.ok(category);
	}

	/**
	 * 结点删除
	 */
	public E3Result deleteContentNode(Long id) {
		/**
		 * 如果该节点是父节点，不让删除
		 * 
		 * 删除后:
		 * 检查该结点下没有结点，如果为0，则修改为主结点
		 */
		
		//判断禁止删除条件
		TbContentCategory category = contentCategoryMapper.selectByPrimaryKey(id);
		if(category.getIsParent() == true) {
			return E3Result.ok("禁止删除");
		}
		
		//删除
		contentCategoryMapper.deleteByPrimaryKey(id);
		
		//查询父节点
		TbContentCategory parentNode = contentCategoryMapper.selectByPrimaryKey(category.getParentId());
		
		//根据父节点id查询分类
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentNode.getId());
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		if(list == null || list.size() == 0) {
			parentNode.setIsParent(false);
			contentCategoryMapper.updateByPrimaryKey(parentNode);
			return E3Result.ok();
		}else {
			return E3Result.ok("禁止删除");
		}
		
	}

}

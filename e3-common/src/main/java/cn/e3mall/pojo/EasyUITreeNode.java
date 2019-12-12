package cn.e3mall.pojo;

import java.io.Serializable;

/**
 * easyUI异步树插件返回json
* Title: EasyUITreeNode <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月17日
 */
public class EasyUITreeNode implements Serializable{

	/** serialVersionUID*/  
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String text;
	private String state;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	
}

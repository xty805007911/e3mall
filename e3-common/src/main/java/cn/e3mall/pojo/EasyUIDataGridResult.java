package cn.e3mall.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 响应数据表格的json
* Title: EasyUIDataGridResult <p>
* Description:   <p>
* @author Tianyu Xiao  
* @date 2018年11月16日
 */
public class EasyUIDataGridResult implements Serializable{
	/** serialVersionUID*/  
	private static final long serialVersionUID = 1L;
	//总条数
	private Integer total;
	//数据集合
	private List<?> rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}
}

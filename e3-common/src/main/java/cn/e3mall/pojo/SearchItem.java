package cn.e3mall.pojo;

import java.io.Serializable;

public class SearchItem implements Serializable{

	/** serialVersionUID*/  
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String title;
	private String sell_point;
	private long price;
	private String image;
	private String category_name;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSell_point() {
		return sell_point;
	}
	public void setSell_point(String sell_point) {
		this.sell_point = sell_point;
	}
	public long getPrice() {
		return price;
	}
	public void setPrice(long price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getCategory_name() {
		return category_name;
	}
	public void setCategory_name(String category_name) {
		this.category_name = category_name;
	}
	//jsp显示第一张图片
	public String getImages() {
		if(image == null) {
			return null; 
		}else {
			String[] split = image.split(",");
			return split[0];
		}
	}

}

package vn.edu.fpt.fts.model;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GoodsCategory implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1836064745456550995L;
	private int goodsCategoryId;
	private String Name;
	
	public GoodsCategory() {
		// TODO Auto-generated constructor stub
	}
	
	public GoodsCategory(int goodsCategoryId, String name) {
		super();
		this.goodsCategoryId = goodsCategoryId;
		Name = name;
	}

	public int getGoodsCategoryId() {
		return goodsCategoryId;
	}
	public void setGoodsCategoryId(int goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}

	
}

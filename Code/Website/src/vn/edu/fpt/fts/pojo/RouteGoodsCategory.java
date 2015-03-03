/**
 * 
 */
package vn.edu.fpt.fts.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Huy
 *
 */
@XmlRootElement
public class RouteGoodsCategory implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8486213753223506260L;
	private int routeID;
	private int goodsCategoryID;
	private GoodsCategory goodsCategory;

	public RouteGoodsCategory() {
		// TODO Auto-generated constructor stub
	}

	public RouteGoodsCategory(int routeID, int goodsCategoryID,
			GoodsCategory goodsCategory) {
		super();
		this.routeID = routeID;
		this.goodsCategoryID = goodsCategoryID;
		this.goodsCategory = goodsCategory;
	}

	public RouteGoodsCategory(int goodsCategoryID, GoodsCategory goodsCategory) {
		super();
		this.goodsCategoryID = goodsCategoryID;
		this.goodsCategory = goodsCategory;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public int getGoodsCategoryID() {
		return goodsCategoryID;
	}

	public void setGoodsCategoryID(int goodsCategoryID) {
		this.goodsCategoryID = goodsCategoryID;
	}

	public GoodsCategory getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(GoodsCategory goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public RouteGoodsCategory(int routeID, int goodsCategoryID) {
		super();
		this.routeID = routeID;
		this.goodsCategoryID = goodsCategoryID;
	}

	public RouteGoodsCategory(int goodsCategoryID) {
		super();
		this.goodsCategoryID = goodsCategoryID;
	}

}

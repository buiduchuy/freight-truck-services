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
	private String routeID;
	private int goodsCategoryID;

	public RouteGoodsCategory() {
		// TODO Auto-generated constructor stub
	}

	public String getRouteID() {
		return routeID;
	}

	public void setRouteID(String routeID) {
		this.routeID = routeID;
	}

	public int getGoodsCategoryID() {
		return goodsCategoryID;
	}

	public void setGoodsCategoryID(int goodsCategoryID) {
		this.goodsCategoryID = goodsCategoryID;
	}

	public RouteGoodsCategory(String routeID, int goodsCategoryID) {
		super();
		this.routeID = routeID;
		this.goodsCategoryID = goodsCategoryID;
	}

	public RouteGoodsCategory(int goodsCategoryID) {
		super();
		this.goodsCategoryID = goodsCategoryID;
	}
	
}

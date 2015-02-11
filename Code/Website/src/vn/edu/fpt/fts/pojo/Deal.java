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
public class Deal implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7427665539357586169L;
	private int dealID;
	private double price;
	private String notes;
	private String createTime;
	private String createBy;
	private int routeID;
	private int goodsID;
	private int dealStatusID;
	private int refDealID;
	private int active;

	private Goods goods;
	private Route route;
	private DealStatus dealStatus;

	public Deal() {
		// TODO Auto-generated constructor stub
	}

	public Deal(double price, String notes, String createTime, String createBy,
			int routeID, int goodsID, int refDealID, int dealStatusID,
			int active) {
		super();
		this.price = price;
		this.notes = notes;
		this.createTime = createTime;
		this.createBy = createBy;
		this.routeID = routeID;
		this.goodsID = goodsID;
		this.refDealID = refDealID;
		this.dealStatusID = dealStatusID;
		this.active = active;
	}

	public Deal(int dealID, double price, String notes, String createTime,
			String createBy, int routeID, int goodsID, int refDealID,
			int dealStatusID, int active) {
		super();
		this.dealID = dealID;
		this.price = price;
		this.notes = notes;
		this.createTime = createTime;
		this.createBy = createBy;
		this.routeID = routeID;
		this.goodsID = goodsID;
		this.refDealID = refDealID;
		this.dealStatusID = dealStatusID;
		this.active = active;
	}

	public int getDealID() {
		return dealID;
	}

	public void setDealID(int dealID) {
		this.dealID = dealID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public int getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(int goodsID) {
		this.goodsID = goodsID;
	}

	public int getRefDealID() {
		return refDealID;
	}

	public void setRefDealID(int refDealID) {
		this.refDealID = refDealID;
	}

	public int getDealStatusID() {
		return dealStatusID;
	}

	public void setDealStatusID(int dealStatusID) {
		this.dealStatusID = dealStatusID;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Goods getGoods() {
		return goods;
	}

	public void setGoods(Goods goods) {
		this.goods = goods;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public DealStatus getDealStatus() {
		return dealStatus;
	}

	public void setDealStatus(DealStatus dealStatus) {
		this.dealStatus = dealStatus;
	}

}

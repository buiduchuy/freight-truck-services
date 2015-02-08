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
	private int orderID;
	private String sender;
	private int routeID;
	private int goodsID;
	private int active;

	public Deal() {
		// TODO Auto-generated constructor stub
	}

	public Deal(int dealID, double price, String notes, String createTime,
			int orderID, String sender, int routeID, int goodsID, int active) {
		super();
		this.dealID = dealID;
		this.price = price;
		this.notes = notes;
		this.createTime = createTime;
		this.orderID = orderID;
		this.sender = sender;
		this.routeID = routeID;
		this.goodsID = goodsID;
		this.active = active;
	}

	public Deal(double price, String notes, String createTime, int orderID,
			String sender, int routeID, int goodsID, int active) {
		super();
		this.price = price;
		this.notes = notes;
		this.createTime = createTime;
		this.orderID = orderID;
		this.sender = sender;
		this.routeID = routeID;
		this.goodsID = goodsID;
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

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
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

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

}

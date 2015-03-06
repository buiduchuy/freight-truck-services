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
public class Order implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3733670181329818420L;
	private int orderID;
	private double price;
	private String createTime;
	private int orderStatusID;
	private int active;

	private Deal deal;

	public Order() {
		// TODO Auto-generated constructor stub
	}

	public Order(int orderID, double price, String createTime,
			int orderStatusID, int active, Deal deal) {
		super();
		this.orderID = orderID;
		this.price = price;
		this.createTime = createTime;
		this.orderStatusID = orderStatusID;
		this.active = active;
		this.deal = deal;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getOrderStatusID() {
		return orderStatusID;
	}

	public void setOrderStatusID(int orderStatusID) {
		this.orderStatusID = orderStatusID;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

}

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
	private boolean staffDeliveryStatus;
	private boolean driverDeliveryStatus;
	private boolean ownerDeliveryStatus;
	private String createTime;
	private int orderStatusID;
	
	private Deal deal;

	public Order() {
		// TODO Auto-generated constructor stub
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

	public boolean isStaffDeliveryStatus() {
		return staffDeliveryStatus;
	}

	public void setStaffDeliveryStatus(boolean staffDeliveryStatus) {
		this.staffDeliveryStatus = staffDeliveryStatus;
	}

	public boolean isDriverDeliveryStatus() {
		return driverDeliveryStatus;
	}

	public void setDriverDeliveryStatus(boolean driverDeliveryStatus) {
		this.driverDeliveryStatus = driverDeliveryStatus;
	}

	public boolean isOwnerDeliveryStatus() {
		return ownerDeliveryStatus;
	}

	public void setOwnerDeliveryStatus(boolean ownerDeliveryStatus) {
		this.ownerDeliveryStatus = ownerDeliveryStatus;
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
	
	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

	public Order(double price, boolean staffDeliveryStatus,
			boolean driverDeliveryStatus, boolean ownerDeliveryStatus,
			String createTime, int orderStatusID) {
		super();
		this.price = price;
		this.staffDeliveryStatus = staffDeliveryStatus;
		this.driverDeliveryStatus = driverDeliveryStatus;
		this.ownerDeliveryStatus = ownerDeliveryStatus;
		this.createTime = createTime;
		this.orderStatusID = orderStatusID;
	}

	public Order(int orderID, double price, boolean staffDeliveryStatus,
			boolean driverDeliveryStatus, boolean ownerDeliveryStatus,
			String createTime, int orderStatusID) {
		super();
		this.orderID = orderID;
		this.price = price;
		this.staffDeliveryStatus = staffDeliveryStatus;
		this.driverDeliveryStatus = driverDeliveryStatus;
		this.ownerDeliveryStatus = ownerDeliveryStatus;
		this.createTime = createTime;
		this.orderStatusID = orderStatusID;
	}
	
}

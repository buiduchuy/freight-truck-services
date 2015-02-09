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
public class DealOrder implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2815843647522348190L;
	private int dealOrderID;
	private int dealID;
	private int orderID;
	
	public DealOrder() {
		// TODO Auto-generated constructor stub
	}

	public DealOrder(int dealOrderID, int dealID, int orderID) {
		super();
		this.dealOrderID = dealOrderID;
		this.dealID = dealID;
		this.orderID = orderID;
	}

	public DealOrder(int dealID, int orderID) {
		super();
		this.dealID = dealID;
		this.orderID = orderID;
	}

	public int getDealOrderID() {
		return dealOrderID;
	}

	public void setDealOrderID(int dealOrderID) {
		this.dealOrderID = dealOrderID;
	}

	public int getDealID() {
		return dealID;
	}

	public void setDealID(int dealID) {
		this.dealID = dealID;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	
}

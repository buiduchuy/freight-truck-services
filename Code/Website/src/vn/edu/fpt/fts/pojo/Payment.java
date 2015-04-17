/**
 * 
 */
package vn.edu.fpt.fts.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Duc Huy
 *
 */
@XmlRootElement
public class Payment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -585786020037068330L;
	private int paymentID;
	private int paypalID;
	private String createTime;
	private int status;
	private int orderID;
	
	public Payment() {
		// TODO Auto-generated constructor stub
	}

	public Payment(int paymentID, int paypalID, String createTime,
			int status, int orderID) {
		super();
		this.paymentID = paymentID;
		this.paypalID = paypalID;
		this.createTime = createTime;
		this.status = status;
		this.orderID = orderID;
	}

	public int getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	public int getPaypalID() {
		return paypalID;
	}

	public void setPaypalID(int paypalID) {
		this.paypalID = paypalID;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
}

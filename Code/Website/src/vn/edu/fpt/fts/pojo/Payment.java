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
	private String paypalID;
	private String PaypalAccount;
	private String description;
	private String createTime;
	private int orderID;
	
	public Payment() {
		// TODO Auto-generated constructor stub
	}

	public Payment(int paymentID, String paypalID, String paypalAccount,
			String description, String createTime, int orderID) {
		super();
		this.paymentID = paymentID;
		this.paypalID = paypalID;
		PaypalAccount = paypalAccount;
		this.description = description;
		this.createTime = createTime;
		this.orderID = orderID;
	}

	public int getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	public String getPaypalID() {
		return paypalID;
	}

	public void setPaypalID(String paypalID) {
		this.paypalID = paypalID;
	}

	public String getPaypalAccount() {
		return PaypalAccount;
	}

	public void setPaypalAccount(String paypalAccount) {
		PaypalAccount = paypalAccount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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
}

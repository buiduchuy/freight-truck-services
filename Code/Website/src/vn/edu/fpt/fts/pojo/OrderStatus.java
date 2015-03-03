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
public class OrderStatus implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7833228392990812176L;
	private int orderStatusID;
	private String orderStatusName;

	public OrderStatus() {
		// TODO Auto-generated constructor stub
	}

	public OrderStatus(int orderStatusID, String orderStatusName) {
		super();
		this.orderStatusID = orderStatusID;
		this.orderStatusName = orderStatusName;
	}

	public int getOrderStatusID() {
		return orderStatusID;
	}

	public void setOrderStatusID(int orderStatusID) {
		this.orderStatusID = orderStatusID;
	}

	public String getOrderStatusName() {
		return orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

}

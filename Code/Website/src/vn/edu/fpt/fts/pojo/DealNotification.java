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
public class DealNotification implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -763745534603494540L;

	private int notificationID;
	private String message;
	private int active;
	private String createTime;
	private int dealID;
	private Deal deal;
	
	public DealNotification() {
		// TODO Auto-generated constructor stub
	}

	public DealNotification(int notificationID, String message, int active,
			String createTime, int dealID, Deal deal) {
		super();
		this.notificationID = notificationID;
		this.message = message;
		this.active = active;
		this.createTime = createTime;
		this.dealID = dealID;
		this.deal = deal;
	}

	public int getNotificationID() {
		return notificationID;
	}

	public void setNotificationID(int notificationID) {
		this.notificationID = notificationID;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getDealID() {
		return dealID;
	}

	public void setDealID(int dealID) {
		this.dealID = dealID;
	}

	public Deal getDeal() {
		return deal;
	}

	public void setDeal(Deal deal) {
		this.deal = deal;
	}

}

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
public class Notification implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -763745534603494540L;

	private int notificationID;
	private String message;
	private int active;
	private String createTime;
	private String type;
	private String email;

	public Notification() {
		// TODO Auto-generated constructor stub
	}

	public Notification(int notificationID, String message, int active,
			String createTime, String type, String email) {
		super();
		this.notificationID = notificationID;
		this.message = message;
		this.active = active;
		this.createTime = createTime;
		this.type = type;
		this.email = email;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}

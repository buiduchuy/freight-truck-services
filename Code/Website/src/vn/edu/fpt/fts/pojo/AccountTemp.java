/**
 * 
 */
package vn.edu.fpt.fts.pojo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Duc Huy
 *
 */
@XmlRootElement
public class AccountTemp implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8289567919221488817L;
	private String email;
	private String address;
	private String contact;
	private String createTime;
	private String type;
	
	public AccountTemp() {
		// TODO Auto-generated constructor stub
	}

	public AccountTemp(String email, String address, String contact,
			String createTime, String type) {
		super();
		this.email = email;
		this.address = address;
		this.contact = contact;
		this.createTime = createTime;
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
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
	
}

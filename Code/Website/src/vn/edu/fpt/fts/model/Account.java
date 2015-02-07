package vn.edu.fpt.fts.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8903802688006955800L;
	private int userID;
	private String email;
	private String password;
	private int roleID;

	public Account() {
		// TODO Auto-generated constructor stub
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleID() {
		return roleID;
	}

	public void setRoleID(int roleID) {
		this.roleID = roleID;
	}

	public Account(int userID, String email, String password, int roleID) {
		super();
		this.userID = userID;
		this.email = email;
		this.password = password;
		this.roleID = roleID;
	}

}

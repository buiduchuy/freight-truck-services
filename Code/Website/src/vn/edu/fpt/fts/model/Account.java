package vn.edu.fpt.fts.model;

public class Account implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int UserID;
	private String Email;
	private String Password;
	private int RoleID;
	
	public Account() {
		// TODO Auto-generated constructor stub
	}
	
	public Account(int userID, String email, String password, int roleID) {
		super();
		UserID = userID;
		Email = email;
		Password = password;
		RoleID = roleID;
	}


	public int getUserID() {
		return UserID;
	}
	public void setUserID(int userID) {
		UserID = userID;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public int getRoleID() {
		return RoleID;
	}
	public void setRoleID(int roleID) {
		RoleID = roleID;
	}
}

package vn.edu.fpt.fts.classes;

public class ListItem {
	private String idOfType;
	private String message;
	private String type;
	
	public ListItem(String idOfType, String message, String type) {
		// TODO Auto-generated constructor stub
		this.idOfType = idOfType;
		this.message = message;
		this.type = type;
	}
	public String getIdOfType() {
		return idOfType;
	}
	public void setIdOfType(String idOfType) {
		this.idOfType = idOfType;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	

	
}

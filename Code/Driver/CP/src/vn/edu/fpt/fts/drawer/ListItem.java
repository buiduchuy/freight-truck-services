package vn.edu.fpt.fts.drawer;

public class ListItem {
	private String info;
	private String title;
	private String description;
	private String date;
	
	public ListItem(String info, String title, String description, String date) {
		super();
		this.info = info;
		this.title = title;
		this.description = description;
		this.date = date;
	}
	
	public ListItem(String info, String title) {
		super();
		this.info = info;
		this.title = title;
	}
	
	public ListItem(String title, String description, String date) {
		super();
		this.title = title;
		this.description = description;
		this.date = date;
	}
	
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
}

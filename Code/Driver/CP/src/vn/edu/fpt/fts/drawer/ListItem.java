package vn.edu.fpt.fts.drawer;

public class ListItem {
	private String date;
	private String description;
	private String info;
	private String title;
	private String title2;
	
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
	
	public ListItem(String info, String title, String description, String date) {
		super();
		this.info = info;
		this.title = title;
		this.description = description;
		this.date = date;
	}
	
	public ListItem(String info, String title, String title2, String description, String date) {
		super();
		this.info = info;
		this.title = title;
		this.title2 = title2;
		this.description = description;
		this.date = date;
	}
	
	public String getDate() {
		return date;
	}
	public String getDescription() {
		return description;
	}
	public String getInfo() {
		return info;
	}
	public String getTitle() {
		return title;
	}
	public String getTitle2() {
		return title2;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTitle2(String title2) {
		this.title2 = title2;
	}
}

package vn.edu.fpt.fts.classes;

public class SuggestModel {
	private String date;
	private String driver;
	private String name;
	public SuggestModel(String name, String date, String driver) {
		super();
		this.name = name;
		this.date = date;
		this.driver = driver;
	}
	public String getDate() {
		return date;
	}
	public String getDriver() {
		return driver;
	}
	public String getName() {
		return name;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

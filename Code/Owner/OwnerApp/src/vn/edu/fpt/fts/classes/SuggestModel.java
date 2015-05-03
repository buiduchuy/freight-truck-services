package vn.edu.fpt.fts.classes;

public class SuggestModel {
	private String name;
	private String date;
	private String driver;
	public SuggestModel(String name, String date, String driver) {
		super();
		this.name = name;
		this.date = date;
		this.driver = driver;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getDriver() {
		return driver;
	}
	public void setDriver(String driver) {
		this.driver = driver;
	}
	
}

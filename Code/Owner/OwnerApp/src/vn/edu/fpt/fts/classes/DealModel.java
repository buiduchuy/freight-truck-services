package vn.edu.fpt.fts.classes;

public class DealModel {
	private String date;
	private String name;
	private String price;
	private String status;
	public DealModel(String name, String date, String price, String status) {
		super();
		this.name = name;
		this.date = date;
		this.price = price;
		this.status = status;
	}
	public String getDate() {
		return date;
	}
	public String getName() {
		return name;
	}
	public String getPrice() {
		return price;
	}
	public String getStatus() {
		return status;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
}

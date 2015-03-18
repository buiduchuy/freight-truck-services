package vn.edu.fpt.fts.classes;

import java.io.Serializable;

public class Order implements Serializable{
	private String orderID;
	private String category;
	private String weight;
	private String price;
	
	public Order() {
	
	}
	
	public Order(String orderID, String category, String weight, String price) {
		this.orderID = orderID;
		this.category = category;
		this.weight = weight;
		this.price = price;
	}
	public String getOrderID() {
		return orderID;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getWeight() {
		return weight;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
}

package vn.edu.fpt.fts.classes;

import java.io.Serializable;

public class Order implements Serializable{
	private String category;
	private String orderID;
	private String price;
	private String weight;
	
	public Order() {
	
	}
	
	public Order(String orderID, String category, String weight, String price) {
		this.orderID = orderID;
		this.category = category;
		this.weight = weight;
		this.price = price;
	}
	public String getCategory() {
		return category;
	}
	public String getOrderID() {
		return orderID;
	}
	public String getPrice() {
		return price;
	}
	public String getWeight() {
		return weight;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public void setOrderID(String orderID) {
		this.orderID = orderID;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public void setWeight(String weight) {
		this.weight = weight;
	}
	
}

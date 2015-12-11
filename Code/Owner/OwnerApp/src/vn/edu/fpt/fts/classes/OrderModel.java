package vn.edu.fpt.fts.classes;

public class OrderModel {
	private String date;
	private String name;
	private String price;
	private String status;
	private String weight;

	public OrderModel(String name, String weight, String date, String price,
			String status) {
		super();
		this.name = name;
		this.weight = weight;
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

	public String getWeight() {
		return weight;
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

	public void setWeight(String weight) {
		this.weight = weight;
	}
}

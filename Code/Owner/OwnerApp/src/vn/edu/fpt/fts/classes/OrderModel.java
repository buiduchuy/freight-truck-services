package vn.edu.fpt.fts.classes;

public class OrderModel {
	private String name;
	private String weight;
	private String date;
	private String price;
	private String status;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

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

	public void setDate(String date) {
		this.date = date;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

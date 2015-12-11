package vn.edu.fpt.fts.classes;

public class GoodsModel {
	private String date;
	private String name;
	private String price;
	private String weight;	
public GoodsModel() {
	// TODO Auto-generated constructor stub
}
	public GoodsModel(String name, String weight, String date, String price) {
		super();
		this.name = name;
		this.weight = weight;
		this.date = date;
		this.price = price;
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

	public void setWeight(String weight) {
		this.weight = weight;
	}
	
	
}

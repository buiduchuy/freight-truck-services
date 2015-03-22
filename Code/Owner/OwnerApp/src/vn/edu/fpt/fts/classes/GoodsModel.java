package vn.edu.fpt.fts.classes;

public class GoodsModel {
	private String name;
	private String weight;
	private String date;
	private String price;	
public GoodsModel() {
	// TODO Auto-generated constructor stub
}
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

	public GoodsModel(String name, String weight, String date, String price) {
		super();
		this.name = name;
		this.weight = weight;
		this.date = date;
		this.price = price;
	}
	
	
}

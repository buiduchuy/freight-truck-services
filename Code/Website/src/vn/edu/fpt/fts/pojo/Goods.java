package vn.edu.fpt.fts.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Goods implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3929332099032600939L;
	private int goodsID;
	private int weight;
	private double price;
	private String pickupTime;
	private String pickupAddress;
	private String deliveryTime;
	private String deliveryAddress;
	private double pickupMarkerLongtitude;
	private double pickupMarkerLatidute;
	private double deliveryMarkerLongtitude;
	private double deliveryMarkerLatidute;
	private String notes;
	private String createTime;
	private int active;
	private int ownerID;
	private int goodsCategoryID;

	private Owner owner;

	private GoodsCategory goodsCategory;

	public Goods(int weight, double price, String pickupTime,
			String pickupAddress, String deliveryTime, String deliveryAddress,
			double pickupMarkerLongtitude, double pickupMarkerLatidute,
			double deliveryMarkerLongtitude, double deliveryMarkerLatidute,
			String notes, String createTime, int active, int ownerID,
			int goodsCategoryID, GoodsCategory goodsCategory, Owner owner) {
		super();
		this.weight = weight;
		this.price = price;
		this.pickupTime = pickupTime;
		this.pickupAddress = pickupAddress;
		this.deliveryTime = deliveryTime;
		this.deliveryAddress = deliveryAddress;
		this.pickupMarkerLongtitude = pickupMarkerLongtitude;
		this.pickupMarkerLatidute = pickupMarkerLatidute;
		this.deliveryMarkerLongtitude = deliveryMarkerLongtitude;
		this.deliveryMarkerLatidute = deliveryMarkerLatidute;
		this.notes = notes;
		this.createTime = createTime;
		this.active = active;
		this.ownerID = ownerID;
		this.goodsCategoryID = goodsCategoryID;
		this.goodsCategory = goodsCategory;
		this.owner = owner;
	}

	public Goods(int goodsID, int weight, double price, String pickupTime,
			String pickupAddress, String deliveryTime, String deliveryAddress,
			double pickupMarkerLongtitude, double pickupMarkerLatidute,
			double deliveryMarkerLongtitude, double deliveryMarkerLatidute,
			String notes, String createTime, int active, int ownerID,
			int goodsCategoryID, GoodsCategory goodsCategory, Owner owner) {
		super();
		this.goodsID = goodsID;
		this.weight = weight;
		this.price = price;
		this.pickupTime = pickupTime;
		this.pickupAddress = pickupAddress;
		this.deliveryTime = deliveryTime;
		this.deliveryAddress = deliveryAddress;
		this.pickupMarkerLongtitude = pickupMarkerLongtitude;
		this.pickupMarkerLatidute = pickupMarkerLatidute;
		this.deliveryMarkerLongtitude = deliveryMarkerLongtitude;
		this.deliveryMarkerLatidute = deliveryMarkerLatidute;
		this.notes = notes;
		this.createTime = createTime;
		this.active = active;
		this.ownerID = ownerID;
		this.goodsCategoryID = goodsCategoryID;
		this.goodsCategory = goodsCategory;
		this.owner = owner;
	}

	public Goods(int weight, String notes, int goodsCategoryID) {
		super();
		this.weight = weight;
		this.notes = notes;
		this.goodsCategoryID = goodsCategoryID;
	}

	public Goods(String pickupTime, String pickupAddress, String deliveryTime,
			String deliveryAddress) {
		super();
		this.pickupTime = pickupTime;
		this.pickupAddress = pickupAddress;
		this.deliveryTime = deliveryTime;
		this.deliveryAddress = deliveryAddress;
	}

	public Goods(int weight, double price, String pickupTime,
			String pickupAddress, String deliveryTime, String deliveryAddress,
			double pickupMarkerLongtitude, double pickupMarkerLatidute,
			double deliveryMarkerLongtitude, double deliveryMarkerLatidute,
			String notes, String createTime, int active, int ownerID,
			int goodsCategoryID) {
		super();
		this.weight = weight;
		this.price = price;
		this.pickupTime = pickupTime;
		this.pickupAddress = pickupAddress;
		this.deliveryTime = deliveryTime;
		this.deliveryAddress = deliveryAddress;
		this.pickupMarkerLongtitude = pickupMarkerLongtitude;
		this.pickupMarkerLatidute = pickupMarkerLatidute;
		this.deliveryMarkerLongtitude = deliveryMarkerLongtitude;
		this.deliveryMarkerLatidute = deliveryMarkerLatidute;
		this.notes = notes;
		this.createTime = createTime;
		this.active = active;
		this.ownerID = ownerID;
		this.goodsCategoryID = goodsCategoryID;
	}

	public Goods(int goodsID, int weight, double price, String pickupTime,
			String pickupAddress, String deliveryTime, String deliveryAddress,
			double pickupMarkerLongtitude, double pickupMarkerLatidute,
			double deliveryMarkerLongtitude, double deliveryMarkerLatidute,
			String notes, String createTime, int active, int ownerID,
			int goodsCategoryID) {
		super();
		this.goodsID = goodsID;
		this.weight = weight;
		this.price = price;
		this.pickupTime = pickupTime;
		this.pickupAddress = pickupAddress;
		this.deliveryTime = deliveryTime;
		this.deliveryAddress = deliveryAddress;
		this.pickupMarkerLongtitude = pickupMarkerLongtitude;
		this.pickupMarkerLatidute = pickupMarkerLatidute;
		this.deliveryMarkerLongtitude = deliveryMarkerLongtitude;
		this.deliveryMarkerLatidute = deliveryMarkerLatidute;
		this.notes = notes;
		this.createTime = createTime;
		this.active = active;
		this.ownerID = ownerID;
		this.goodsCategoryID = goodsCategoryID;
	}

	public Goods() {
		// TODO Auto-generated constructor stub
	}

	public int getGoodsID() {
		return goodsID;
	}

	public void setGoodsID(int goodsID) {
		this.goodsID = goodsID;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getPickupAddress() {
		return pickupAddress;
	}

	public void setPickupAddress(String pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	public String getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public String getDeliveryAddress() {
		return deliveryAddress;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public double getPickupMarkerLongtitude() {
		return pickupMarkerLongtitude;
	}

	public void setPickupMarkerLongtitude(double pickupMarkerLongtitude) {
		this.pickupMarkerLongtitude = pickupMarkerLongtitude;
	}

	public double getPickupMarkerLatidute() {
		return pickupMarkerLatidute;
	}

	public void setPickupMarkerLatidute(double pickupMarkerLatidute) {
		this.pickupMarkerLatidute = pickupMarkerLatidute;
	}

	public double getDeliveryMarkerLongtitude() {
		return deliveryMarkerLongtitude;
	}

	public void setDeliveryMarkerLongtitude(double deliveryMarkerLongtitude) {
		this.deliveryMarkerLongtitude = deliveryMarkerLongtitude;
	}

	public double getDeliveryMarkerLatidute() {
		return deliveryMarkerLatidute;
	}

	public void setDeliveryMarkerLatidute(double deliveryMarkerLatidute) {
		this.deliveryMarkerLatidute = deliveryMarkerLatidute;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public int getOwnerID() {
		return ownerID;
	}

	public void setOwnerID(int ownerID) {
		this.ownerID = ownerID;
	}

	public int getGoodsCategoryID() {
		return goodsCategoryID;
	}

	public void setGoodsCategoryID(int goodsCategoryID) {
		this.goodsCategoryID = goodsCategoryID;
	}

	public GoodsCategory getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(GoodsCategory goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public Owner getOwner() {
		return owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

}

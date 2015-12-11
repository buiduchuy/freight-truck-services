package vn.edu.fpt.fts.classes;

import java.io.Serializable;

public class Goods implements Serializable{
	private int Active;
	private String CreateTime;
	private String DeliveryAddress;
	private float DeliveryMarkerLatidute;
	private float DeliveryMarkerLongtitude;
	private String DeliveryTime;
	private String GoodsCategory;
	private int GoodsCategoryID;
	private int GoodsID;
	private String Notes;
	private int OwnerID;
	private String PickupAddress;
	private float PickupMarkerLatidute;
	private float PickupMarkerLongtitude;
	private String PickupTime;
	private double Price;
	private int Weight;
	
	public Goods() {
		// TODO Auto-generated constructor stub
	}
	
	public Goods(int goodsID, int weight, double price, String pickupTime,
			String pickupAddress, String deliveryTime, String deliveryAddress,
			float pickupMarkerLongtitude, float pickupMarkerLatidute,
			float deliveryMarkerLongtitude, float deliveryMarkerLatidute,
			String notes, String createTime, int active, int ownerID,
			int goodsCategoryID, String GoodsCategory) {
		super();
		GoodsID = goodsID;
		Weight = weight;
		Price = price;
		PickupTime = pickupTime;
		PickupAddress = pickupAddress;
		DeliveryTime = deliveryTime;
		DeliveryAddress = deliveryAddress;
		PickupMarkerLongtitude = pickupMarkerLongtitude;
		PickupMarkerLatidute = pickupMarkerLatidute;
		DeliveryMarkerLongtitude = deliveryMarkerLongtitude;
		DeliveryMarkerLatidute = deliveryMarkerLatidute;
		Notes = notes;
		CreateTime = createTime;
		Active = active;
		OwnerID = ownerID;
		GoodsCategoryID = goodsCategoryID;
		this.GoodsCategory = GoodsCategory;
	}

	public int getActive() {
		return Active;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public String getDeliveryAddress() {
		return DeliveryAddress;
	}

	public float getDeliveryMarkerLatidute() {
		return DeliveryMarkerLatidute;
	}

	public float getDeliveryMarkerLongtitude() {
		return DeliveryMarkerLongtitude;
	}

	public String getDeliveryTime() {
		return DeliveryTime;
	}

	public String getGoodsCategory() {
		return GoodsCategory;
	}

	public int getGoodsCategoryID() {
		return GoodsCategoryID;
	}

	public int getGoodsID() {
		return GoodsID;
	}

	public String getNotes() {
		return Notes;
	}

	public int getOwnerID() {
		return OwnerID;
	}

	public String getPickupAddress() {
		return PickupAddress;
	}

	public float getPickupMarkerLatidute() {
		return PickupMarkerLatidute;
	}

	public float getPickupMarkerLongtitude() {
		return PickupMarkerLongtitude;
	}

	public String getPickupTime() {
		return PickupTime;
	}

	public double getPrice() {
		return Price;
	}

	public int getWeight() {
		return Weight;
	}

	public void setActive(int active) {
		Active = active;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public void setDeliveryAddress(String deliveryAddress) {
		DeliveryAddress = deliveryAddress;
	}

	public void setDeliveryMarkerLatidute(float deliveryMarkerLatidute) {
		DeliveryMarkerLatidute = deliveryMarkerLatidute;
	}

	public void setDeliveryMarkerLongtitude(float deliveryMarkerLongtitude) {
		DeliveryMarkerLongtitude = deliveryMarkerLongtitude;
	}

	public void setDeliveryTime(String deliveryTime) {
		DeliveryTime = deliveryTime;
	}

	public void setGoodsCategory(String goodsCategory) {
		GoodsCategory = goodsCategory;
	}

	public void setGoodsCategoryID(int goodsCategoryID) {
		GoodsCategoryID = goodsCategoryID;
	}

	public void setGoodsID(int goodsID) {
		GoodsID = goodsID;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

	public void setOwnerID(int ownerID) {
		OwnerID = ownerID;
	}

	public void setPickupAddress(String pickupAddress) {
		PickupAddress = pickupAddress;
	}

	public void setPickupMarkerLatidute(float pickupMarkerLatidute) {
		PickupMarkerLatidute = pickupMarkerLatidute;
	}

	public void setPickupMarkerLongtitude(float pickupMarkerLongtitude) {
		PickupMarkerLongtitude = pickupMarkerLongtitude;
	}

	public void setPickupTime(String pickupTime) {
		PickupTime = pickupTime;
	}

	public void setPrice(double price) {
		Price = price;
	}

	public void setWeight(int weight) {
		Weight = weight;
	}
	
	
}

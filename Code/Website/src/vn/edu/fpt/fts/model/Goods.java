package vn.edu.fpt.fts.model;

// Generated Feb 4, 2015 3:30:12 PM by Hibernate Tools 3.6.0

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Goods generated by hbm2java
 */
public class Goods implements java.io.Serializable {

	private int goodsId;
	private GoodsCategory goodsCategory;
	private Owner owner;
	private Serializable type;
	private Integer weight;
	private Serializable price;
	private Date pickupTime;
	private Serializable pickupAddress;
	private Date deliveryTime;
	private Serializable deliveryAddress;
	private Serializable pickupMarkerLocation;
	private Serializable deliveryMarkerLocation;
	private Serializable notes;
	private Serializable createTime;
	private Serializable isActive;
	private Set<Deal> deals = new HashSet<Deal>(0);

	public Goods() {
	}

	public Goods(int goodsId, GoodsCategory goodsCategory, Owner owner,
			Serializable type, Date deliveryTime, Serializable deliveryAddress) {
		this.goodsId = goodsId;
		this.goodsCategory = goodsCategory;
		this.owner = owner;
		this.type = type;
		this.deliveryTime = deliveryTime;
		this.deliveryAddress = deliveryAddress;
	}

	public Goods(int goodsId, GoodsCategory goodsCategory, Owner owner,
			Serializable type, Integer weight, Serializable price,
			Date pickupTime, Serializable pickupAddress, Date deliveryTime,
			Serializable deliveryAddress, Serializable pickupMarkerLocation,
			Serializable deliveryMarkerLocation, Serializable notes,
			Serializable createTime, Serializable isActive, Set<Deal> deals) {
		this.goodsId = goodsId;
		this.goodsCategory = goodsCategory;
		this.owner = owner;
		this.type = type;
		this.weight = weight;
		this.price = price;
		this.pickupTime = pickupTime;
		this.pickupAddress = pickupAddress;
		this.deliveryTime = deliveryTime;
		this.deliveryAddress = deliveryAddress;
		this.pickupMarkerLocation = pickupMarkerLocation;
		this.deliveryMarkerLocation = deliveryMarkerLocation;
		this.notes = notes;
		this.createTime = createTime;
		this.isActive = isActive;
		this.deals = deals;
	}

	public int getGoodsId() {
		return this.goodsId;
	}

	public void setGoodsId(int goodsId) {
		this.goodsId = goodsId;
	}

	public GoodsCategory getGoodsCategory() {
		return this.goodsCategory;
	}

	public void setGoodsCategory(GoodsCategory goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

	public Owner getOwner() {
		return this.owner;
	}

	public void setOwner(Owner owner) {
		this.owner = owner;
	}

	public Serializable getType() {
		return this.type;
	}

	public void setType(Serializable type) {
		this.type = type;
	}

	public Integer getWeight() {
		return this.weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Serializable getPrice() {
		return this.price;
	}

	public void setPrice(Serializable price) {
		this.price = price;
	}

	public Date getPickupTime() {
		return this.pickupTime;
	}

	public void setPickupTime(Date pickupTime) {
		this.pickupTime = pickupTime;
	}

	public Serializable getPickupAddress() {
		return this.pickupAddress;
	}

	public void setPickupAddress(Serializable pickupAddress) {
		this.pickupAddress = pickupAddress;
	}

	public Date getDeliveryTime() {
		return this.deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Serializable getDeliveryAddress() {
		return this.deliveryAddress;
	}

	public void setDeliveryAddress(Serializable deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}

	public Serializable getPickupMarkerLocation() {
		return this.pickupMarkerLocation;
	}

	public void setPickupMarkerLocation(Serializable pickupMarkerLocation) {
		this.pickupMarkerLocation = pickupMarkerLocation;
	}

	public Serializable getDeliveryMarkerLocation() {
		return this.deliveryMarkerLocation;
	}

	public void setDeliveryMarkerLocation(Serializable deliveryMarkerLocation) {
		this.deliveryMarkerLocation = deliveryMarkerLocation;
	}

	public Serializable getNotes() {
		return this.notes;
	}

	public void setNotes(Serializable notes) {
		this.notes = notes;
	}

	public Serializable getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Serializable createTime) {
		this.createTime = createTime;
	}

	public Serializable getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Serializable isActive) {
		this.isActive = isActive;
	}

	public Set<Deal> getDeals() {
		return this.deals;
	}

	public void setDeals(Set<Deal> deals) {
		this.deals = deals;
	}

}

package vn.edu.fpt.fts.model;

// Generated Feb 4, 2015 3:30:12 PM by Hibernate Tools 3.6.0

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * PaymentType generated by hbm2java
 */
public class PaymentType implements java.io.Serializable {

	private int paymentTypeId;
	private Serializable name;
	private Serializable discription;
	private Set<Order> orders = new HashSet<Order>(0);

	public PaymentType() {
	}

	public PaymentType(int paymentTypeId, Serializable name,
			Serializable discription) {
		this.paymentTypeId = paymentTypeId;
		this.name = name;
		this.discription = discription;
	}

	public PaymentType(int paymentTypeId, Serializable name,
			Serializable discription, Set<Order> orders) {
		this.paymentTypeId = paymentTypeId;
		this.name = name;
		this.discription = discription;
		this.orders = orders;
	}

	public int getPaymentTypeId() {
		return this.paymentTypeId;
	}

	public void setPaymentTypeId(int paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Serializable getName() {
		return this.name;
	}

	public void setName(Serializable name) {
		this.name = name;
	}

	public Serializable getDiscription() {
		return this.discription;
	}

	public void setDiscription(Serializable discription) {
		this.discription = discription;
	}

	public Set<Order> getOrders() {
		return this.orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

}

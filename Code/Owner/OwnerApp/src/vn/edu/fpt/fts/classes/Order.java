package vn.edu.fpt.fts.classes;

import java.io.Serializable;

public class Order implements Serializable{
	private int orderID;
	private double price;
	private boolean staffDeliveryStatus;
	private boolean driverDeliveryStatus;
	private boolean ownerDeliveryStatus;
	private String createTime;
	private int orderStatusID;
	private int active;
}

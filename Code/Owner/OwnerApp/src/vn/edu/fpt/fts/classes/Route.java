package vn.edu.fpt.fts.classes;

import java.io.Serializable;

public class Route implements Serializable {
	private int active;
	private String category;
	private String createTime;
	private String destinationAddress;
	private String driverID;
	private String finishTime;
	private String notes;
	private int RouteID;
	private String startingAddress;
	private String startTime;
	private int weight;

	public Route() {
		// TODO Auto-generated constructor stub
	}

	public Route(int routeID, String startingAddress,
			String destinationAddress, String startTime, String finishTime,
			String notes, int weight, String createTime, int active,
			String driverID) {
		super();
		this.RouteID = routeID;
		this.startingAddress = startingAddress;
		this.destinationAddress = destinationAddress;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.notes = notes;
		this.weight = weight;
		this.createTime = createTime;
		this.active = active;
		this.driverID = driverID;
	}

	public Route(String startingAddress, String destinationAddress,
			String startTime, String finishTime, String category) {
		// TODO Auto-generated constructor stub
		this.startingAddress = startingAddress;
		this.destinationAddress = destinationAddress;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.category = category;
	}

	public int getActive() {
		return active;
	}

	public String getCategory() {
		return category;
	}

	public String getCreateTime() {
		return createTime;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public String getDriverID() {
		return driverID;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public String getNotes() {
		return notes;
	}

	public int getRouteID() {
		return RouteID;
	}

	public String getStartingAddress() {
		return startingAddress;
	}

	public String getStartTime() {
		return startTime;
	}

	public int getWeight() {
		return weight;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public void setDriverID(String driverID) {
		this.driverID = driverID;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setRouteID(int routeID) {
		RouteID = routeID;
	}

	public void setStartingAddress(String startingAddress) {
		this.startingAddress = startingAddress;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}

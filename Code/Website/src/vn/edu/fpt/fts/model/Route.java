package vn.edu.fpt.fts.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Route implements java.io.Serializable {

	private int RouteID;
	private String StartingAddress;
	private String Marker1;
	private String Marker2;
	private String DestinationAddress;
	private String StartTime;
	private String FinishTime;
	private String Notes;
	private String CreateTime;
	private int Active;
	private int VehicleID;
	private int DriverID;
	
	public Route() {
		// TODO Auto-generated constructor stub
	}
	
	public Route(int routeID, String startingAddress, String marker1,
			String marker2, String destinationAddress, String startTime,
			String finishTime, String notes, String createTime, int active,
			int vehicleID, int driverID) {
		super();
		RouteID = routeID;
		StartingAddress = startingAddress;
		Marker1 = marker1;
		Marker2 = marker2;
		DestinationAddress = destinationAddress;
		StartTime = startTime;
		FinishTime = finishTime;
		Notes = notes;
		CreateTime = createTime;
		Active = active;
		VehicleID = vehicleID;
		DriverID = driverID;
	}

	public int getRouteID() {
		return RouteID;
	}
	public void setRouteID(int routeID) {
		RouteID = routeID;
	}
	public String getStartingAddress() {
		return StartingAddress;
	}
	public void setStartingAddress(String startingAddress) {
		StartingAddress = startingAddress;
	}
	public String getMarker1() {
		return Marker1;
	}
	public void setMarker1(String marker1) {
		Marker1 = marker1;
	}
	public String getMarker2() {
		return Marker2;
	}
	public void setMarker2(String marker2) {
		Marker2 = marker2;
	}
	public String getDestinationAddress() {
		return DestinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		DestinationAddress = destinationAddress;
	}
	public String getStartTime() {
		return StartTime;
	}
	public void setStartTime(String startTime) {
		StartTime = startTime;
	}
	public String getFinishTime() {
		return FinishTime;
	}
	public void setFinishTime(String finishTime) {
		FinishTime = finishTime;
	}
	public String getNotes() {
		return Notes;
	}
	public void setNotes(String notes) {
		Notes = notes;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public int getActive() {
		return Active;
	}
	public void setActive(int active) {
		Active = active;
	}
	public int getVehicleID() {
		return VehicleID;
	}
	public void setVehicleID(int vehicleID) {
		VehicleID = vehicleID;
	}
	public int getDriverID() {
		return DriverID;
	}
	public void setDriverID(int driverID) {
		DriverID = driverID;
	}
	
}
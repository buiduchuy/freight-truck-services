package vn.edu.fpt.fts.model;

/**
 * @author Huy
 *
 */
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Route implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7515339075887353188L;
	private int routeID;
	private String startingAddress;
	private int routeMarkerID;
	private String destinationAddress;
	private String startTime;
	private String finishTime;
	private String notes;
	private String createTime;
	private int active;
	private int vehicleID;
	private int driverID;

	public Route() {
		// TODO Auto-generated constructor stub
	}

	public Route(int routeID, String startingAddress, int routeMarkerID,
			String destinationAddress, String startTime, String finishTime,
			String notes, String createTime, int active, int vehicleID,
			int driverID) {
		super();
		this.routeID = routeID;
		this.startingAddress = startingAddress;
		this.routeMarkerID = routeMarkerID;
		this.destinationAddress = destinationAddress;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.notes = notes;
		this.createTime = createTime;
		this.active = active;
		this.vehicleID = vehicleID;
		this.driverID = driverID;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

	public String getStartingAddress() {
		return startingAddress;
	}

	public void setStartingAddress(String startingAddress) {
		this.startingAddress = startingAddress;
	}

	public int getRouteMarkerID() {
		return routeMarkerID;
	}

	public void setRouteMarkerID(int routeMarkerID) {
		this.routeMarkerID = routeMarkerID;
	}

	public String getDestinationAddress() {
		return destinationAddress;
	}

	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
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

	public int getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}

	public int getDriverID() {
		return driverID;
	}

	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}

}
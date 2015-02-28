package vn.edu.fpt.fts.pojo;

import java.util.List;

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
	private int RouteID;
	private String startingAddress;
	private String destinationAddress;
	private String startTime;
	private String finishTime;
	private String notes;
	private int weight;
	private String createTime;
	private int active;
	private int driverID;

	private Driver driver;
	private List<RouteMarker> routeMarkers;
	private List<Vehicle> vehicles;
	private List<RouteGoodsCategory> routeGoodsCategory;
	private List<GoodsCategory> goodsCategory;

	public Route() {
		// TODO Auto-generated constructor stub
	}

	public Route(String startingAddress, String destinationAddress,
			String startTime, String finishTime, String notes, int weight,
			String createTime, int active, int driverID, Driver driver,
			List<RouteMarker> routeMarkers, List<Vehicle> vehicles,
			List<RouteGoodsCategory> routeGoodsCategory,
			List<GoodsCategory> goodsCategory) {
		super();
		this.startingAddress = startingAddress;
		this.destinationAddress = destinationAddress;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.notes = notes;
		this.weight = weight;
		this.createTime = createTime;
		this.active = active;
		this.driverID = driverID;
		this.driver = driver;
		this.routeMarkers = routeMarkers;
		this.vehicles = vehicles;
		this.routeGoodsCategory = routeGoodsCategory;
		this.goodsCategory = goodsCategory;
	}

	public Route(int routeID, String startingAddress,
			String destinationAddress, String startTime, String finishTime,
			String notes, int weight, String createTime, int active,
			int driverID, Driver driver, List<RouteMarker> routeMarkers,
			List<Vehicle> vehicles,
			List<RouteGoodsCategory> routeGoodsCategory,
			List<GoodsCategory> goodsCategory) {
		super();
		RouteID = routeID;
		this.startingAddress = startingAddress;
		this.destinationAddress = destinationAddress;
		this.startTime = startTime;
		this.finishTime = finishTime;
		this.notes = notes;
		this.weight = weight;
		this.createTime = createTime;
		this.active = active;
		this.driverID = driverID;
		this.driver = driver;
		this.routeMarkers = routeMarkers;
		this.vehicles = vehicles;
		this.routeGoodsCategory = routeGoodsCategory;
		this.goodsCategory = goodsCategory;
	}

	public int getRouteID() {
		return RouteID;
	}

	public void setRouteID(int routeID) {
		RouteID = routeID;
	}

	public String getStartingAddress() {
		return startingAddress;
	}

	public void setStartingAddress(String startingAddress) {
		this.startingAddress = startingAddress;
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

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
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

	public int getDriverID() {
		return driverID;
	}

	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}

	public Driver getDriver() {
		return driver;
	}

	public void setDriver(Driver driver) {
		this.driver = driver;
	}

	public List<RouteMarker> getRouteMarkers() {
		return routeMarkers;
	}

	public void setRouteMarkers(List<RouteMarker> routeMarkers) {
		this.routeMarkers = routeMarkers;
	}

	public List<Vehicle> getVehicles() {
		return vehicles;
	}

	public void setVehicles(List<Vehicle> vehicles) {
		this.vehicles = vehicles;
	}

	public List<RouteGoodsCategory> getRouteGoodsCategory() {
		return routeGoodsCategory;
	}

	public void setRouteGoodsCategory(
			List<RouteGoodsCategory> routeGoodsCategory) {
		this.routeGoodsCategory = routeGoodsCategory;
	}

	public List<GoodsCategory> getGoodsCategory() {
		return goodsCategory;
	}

	public void setGoodsCategory(List<GoodsCategory> goodsCategory) {
		this.goodsCategory = goodsCategory;
	}

}
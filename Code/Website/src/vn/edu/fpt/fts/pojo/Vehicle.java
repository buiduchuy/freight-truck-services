/**
 * 
 */
package vn.edu.fpt.fts.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Huy
 *
 */
@XmlRootElement
public class Vehicle implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7012060922669470124L;
	private int vehicleID;
	private String identNumber;
	private String type;
	private int routeID;

	public Vehicle() {
		// TODO Auto-generated constructor stub
	}

	public Vehicle(String identNumber, String type, int routeID) {
		super();
		this.identNumber = identNumber;
		this.type = type;
		this.routeID = routeID;
	}

	public Vehicle(int vehicleID, String identNumber, String type, int routeID) {
		super();
		this.vehicleID = vehicleID;
		this.identNumber = identNumber;
		this.type = type;
		this.routeID = routeID;
	}

	public int getVehicleID() {
		return vehicleID;
	}

	public void setVehicleID(int vehicleID) {
		this.vehicleID = vehicleID;
	}

	public String getIdentNumber() {
		return identNumber;
	}

	public void setIdentNumber(String identNumber) {
		this.identNumber = identNumber;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

}

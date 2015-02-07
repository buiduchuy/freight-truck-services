/**
 * 
 */
package vn.edu.fpt.fts.pojo;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Huy
 *
 */
@XmlRootElement
public class RouteMarker implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1290999686255066264L;
	private int routeMarkerID;
	private String routeMarkerLocation;
	private int routeID;

	public RouteMarker() {
		// TODO Auto-generated constructor stub
	}

	public RouteMarker(int routeMarkerID, String routeMarkerLocation,
			int routeID) {
		super();
		this.routeMarkerID = routeMarkerID;
		this.routeMarkerLocation = routeMarkerLocation;
		this.routeID = routeID;
	}

	public int getRouteMarkerID() {
		return routeMarkerID;
	}

	public void setRouteMarkerID(int routeMarkerID) {
		this.routeMarkerID = routeMarkerID;
	}

	public String getRouteMarkerLocation() {
		return routeMarkerLocation;
	}

	public void setRouteMarkerLocation(String routeMarkerLocation) {
		this.routeMarkerLocation = routeMarkerLocation;
	}

	public int getRouteID() {
		return routeID;
	}

	public void setRouteID(int routeID) {
		this.routeID = routeID;
	}

}

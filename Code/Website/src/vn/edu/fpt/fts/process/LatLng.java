/**
 * 
 */
package vn.edu.fpt.fts.process;

/**
 * @author Huy
 *
 */
public class LatLng {
	private double latitude;
	private double longitude;

	public LatLng() {
		// TODO Auto-generated constructor stub
	}

	public LatLng(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

}

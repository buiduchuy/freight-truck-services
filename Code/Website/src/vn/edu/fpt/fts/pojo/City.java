package vn.edu.fpt.fts.pojo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Huy
 *
 */
@XmlRootElement
public class City implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3004320563271851232L;
	private int cityID;
	private String cityName;
	private float latitude;
	private float longitude;

	public City() {
		// TODO Auto-generated constructor stub
	}

	public City(int cityID, String cityName, float latitude, float longitude) {
		super();
		this.cityID = cityID;
		this.cityName = cityName;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public int getCityID() {
		return cityID;
	}

	public void setCityID(int cityID) {
		this.cityID = cityID;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

}

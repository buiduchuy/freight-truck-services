/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Huy
 *
 */
@XmlRootElement
public class StepRoute implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6976013257542493068L;
	private Distance distance;
	private Duration duration;
	private StartLocation startLocation;
	private EndLocation endLocation;

	public StepRoute() {
		// TODO Auto-generated constructor stub
	}

	public StepRoute(Distance distance, Duration duration,
			StartLocation startLocation, EndLocation endLocation) {
		super();
		this.distance = distance;
		this.duration = duration;
		this.startLocation = startLocation;
		this.endLocation = endLocation;
	}

	public Distance getDistance() {
		return distance;
	}

	public void setDistance(Distance distance) {
		this.distance = distance;
	}

	public Duration getDuration() {
		return duration;
	}

	public void setDuration(Duration duration) {
		this.duration = duration;
	}

	public StartLocation getStartLocation() {
		return startLocation;
	}

	public void setStartLocation(StartLocation startLocation) {
		this.startLocation = startLocation;
	}

	public EndLocation getEndLocation() {
		return endLocation;
	}

	public void setEndLocation(EndLocation endLocation) {
		this.endLocation = endLocation;
	}

}

class Distance {
	private String text;
	private Double value;

	public Distance() {
		// TODO Auto-generated constructor stub
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Distance(String text, Double value) {
		super();
		this.text = text;
		this.value = value;
	}
}

class Duration {
	private String text;
	private Double value;

	public Duration() {
		// TODO Auto-generated constructor stub
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Duration(String text, Double value) {
		super();
		this.text = text;
		this.value = value;
	}
}

class StartLocation {
	private Double lat;
	private Double lng;

	public StartLocation() {
		// TODO Auto-generated constructor stub
	}

	public StartLocation(Double lat, Double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
}

class EndLocation {
	private Double lat;
	private Double lng;

	public EndLocation() {
		// TODO Auto-generated constructor stub
	}

	public EndLocation(Double lat, Double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
}

/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Huy
 *
 */
public class MatchingProcess {

	// the radius of the Earth
	double earthRadius = 6371;

	// convert from degree to radian
	private Double deg2rad(Double deg) {
		return (deg * Math.PI / 180.0);
	}

	// convert from radian to degree
//	private Double rad2deg(Double rad) {
//		return (rad / Math.PI * 180.0);
//	}

	// change coordinate of gmaps to 3D
	public List<Double> ChangeCoordinateMapToCoordinate3D(Double longitude,
			Double latitude) {

		// change from degree to radian
		longitude = deg2rad(longitude);
		latitude = deg2rad(latitude);

		// change to 3D coordinate base on the formula
		double x = Math.cos(latitude) * Math.cos(longitude) * earthRadius;
		double y = Math.cos(latitude) * Math.sin(longitude) * earthRadius;
		double z = Math.sin(latitude) * earthRadius;
		List<Double> result = new ArrayList<Double>();
		result.add(x);
		result.add(y);
		result.add(z);
		return result;
	}

// change coordinate 3D to gmaps - not using now but reserve for future
//	public List<Double> ChangeCoordinate3DToCoordinateMap(Double x, Double y,
//			Double z) {
//
//		// change to longitude and latitude
//		Double longitude = Math.atan(y / x);
//		if (longitude < 0)
//			longitude += Math.PI;
//		Double latitude = Math.asin(z / earthRadius);
//
//		// change from radian to degree
//		longitude = rad2deg(longitude);
//		latitude = rad2deg(latitude);
//		List<Double> result = new ArrayList<Double>();
//		result.add(latitude);
//		result.add(longitude);
//		return result;
//	}

	private double maxAllowDistance = 1000.0; // meters

	// / <summary>
	// / Check whether the distance from the goods position to the stage connect
	// two point on path is not exceed the max allowed distance or not
	// / </summary>
	// / <param name="latGoods">latitude of goods</param>
	// / <param name="longGoods">longitude of goods</param>
	// / <param name="latStart">latitude of start point of the stage</param>
	// / <param name="longStart">longitude of start point of the stage</param>
	// / <param name="latEnd">latitude of end point of the stage</param>
	// / <param name="longEnd">longitude of end point of the stage</param>
	// / <returns></returns>
	@SuppressWarnings("unused")
	private boolean checkDistance(Double latGoods, Double longGoods,
			Double latStart, Double longStart, Double latEnd, Double longEnd) {

		// change from longitude and latitude to coordinate 3D
		List<Double> coordGoods = ChangeCoordinateMapToCoordinate3D(longGoods,
				latGoods);
		List<Double> coordStart = ChangeCoordinateMapToCoordinate3D(longStart,
				latStart);
		List<Double> coordEnd = ChangeCoordinateMapToCoordinate3D(longEnd,
				latEnd);

		// length of the path from start to end
		double lengthOfPath = Math.sqrt((coordStart.get(0) - coordEnd.get(0))
				* (coordStart.get(0) - coordEnd.get(0))
				+ (coordStart.get(1) - coordEnd.get(1))
				* (coordStart.get(1) - coordEnd.get(1))
				+ (coordStart.get(2) - coordEnd.get(2))
				* (coordStart.get(2) - coordEnd.get(2)));

		// find vector connect goods point and start point
		List<Double> vector1 = new ArrayList<Double>();
		vector1.add(coordGoods.get(0) - coordStart.get(0));
		vector1.add(coordGoods.get(1) - coordStart.get(1));
		vector1.add(coordGoods.get(2) - coordStart.get(2));

		// find vector connect goods point and end point
		List<Double> vector2 = new ArrayList<Double>();
		vector1.add(coordGoods.get(0) - coordEnd.get(0));
		vector1.add(coordGoods.get(1) - coordEnd.get(1));
		vector1.add(coordGoods.get(2) - coordEnd.get(2));

		// find the dot product of two above vectors
		List<Double> dotProduct = new ArrayList<Double>();
		dotProduct.add(vector1.get(1) * vector2.get(2) - vector1.get(2)
				* vector2.get(1));
		dotProduct.add(vector1.get(2) * vector2.get(0) - vector1.get(0)
				* vector2.get(2));
		dotProduct.add(vector1.get(0) * vector2.get(1) - vector1.get(1)
				* vector2.get(0));

		// if the start point and end point are the same
		if (lengthOfPath == 0) {
			if (Math.sqrt(vector1.get(0) * vector1.get(0) + vector1.get(1)
					* vector1.get(1) + vector1.get(2) * vector1.get(2)) > maxAllowDistance) {
				return false;
			}
			return true;
		}

		// find the distance by the area of the triangle
		double result = Math.sqrt(dotProduct.get(0) * dotProduct.get(0)
				+ dotProduct.get(1) * dotProduct.get(1) + dotProduct.get(2)
				* dotProduct.get(2))
				/ lengthOfPath;
		if (result > maxAllowDistance)
			return false;
		return true;
	}
}

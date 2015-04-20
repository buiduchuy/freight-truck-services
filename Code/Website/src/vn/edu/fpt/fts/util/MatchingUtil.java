/**
 * 
 */
package vn.edu.fpt.fts.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Huy
 *
 */
public class MatchingUtil {

	// the radius of the Earth
	private double earthRadius = 6371; // kilometers

	// maxAllowDistance
	// private double maxAllowDistance = 0.5; // kilometers

	// convert from degree to radian
	private Double deg2rad(Double deg) {
		return (deg * Math.PI / 180.0);
	}

	// convert from radian to degree
	// private Double rad2deg(Double rad) {
	// return (rad / Math.PI * 180.0);
	// }

	// change coordinate of gmaps to 3D
	public List<Double> ChangeCoordinateMapToCoordinate3D(Double latitude,
			Double longitude) {

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
	// public List<Double> ChangeCoordinate3DToCoordinateMap(Double x, Double y,
	// Double z) {
	//
	// // change to longitude and latitude
	// Double longitude = Math.atan(y / x);
	// if (longitude < 0)
	// longitude += Math.PI;
	// Double latitude = Math.asin(z / earthRadius);
	//
	// // change from radian to degree
	// longitude = rad2deg(longitude);
	// latitude = rad2deg(latitude);
	// List<Double> result = new ArrayList<Double>();
	// result.add(latitude);
	// result.add(longitude);
	// return result;
	// }

	// / Check whether the distance from the goods position to the stage connect
	// two point on path is not exceed the max allowed distance or not
	// / latGoods: latitude of goods
	// / longGoods: longitude of goods
	// / latStart: latitude of start point of the stage
	// / longStart: longitude of start point of the stage
	// / latEnd: latitude of end point of the stage
	// / longEnd: longitude of end point of the stage
	public Double calDistance(Double latGoods, Double longGoods,
			Double latStart, Double longStart, Double latEnd, Double longEnd,
			Double maxAllowDistance) {

		// change from longitude and latitude to coordinate 3D
		List<Double> coordGoods = ChangeCoordinateMapToCoordinate3D(latGoods,
				longGoods);
		List<Double> coordStart = ChangeCoordinateMapToCoordinate3D(latStart,
				longStart);
		List<Double> coordEnd = ChangeCoordinateMapToCoordinate3D(latEnd,
				longEnd);

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
		vector2.add(coordGoods.get(0) - coordEnd.get(0));
		vector2.add(coordGoods.get(1) - coordEnd.get(1));
		vector2.add(coordGoods.get(2) - coordEnd.get(2));

		// distance from goods position to start point
		Double length1 = Math.sqrt(vector1.get(0) * vector1.get(0)
				+ vector1.get(1) * vector1.get(1) + vector1.get(2)
				* vector1.get(2));

		// distance from goods position to end point
		Double length2 = Math.sqrt(vector2.get(0) * vector2.get(0)
				+ vector2.get(1) * vector2.get(1) + vector2.get(2)
				* vector2.get(2));

		// check acute angle
		Double signOfCos1 = (length1 * length1 + lengthOfPath * lengthOfPath - length2
				* length2);
		Double signOfCos2 = (length2 * length2 + lengthOfPath * lengthOfPath - length1
				* length1);

		if (signOfCos1 <= 0 || signOfCos2 <= 0) {
			// the projection lies outside the segment
			if (length1 > maxAllowDistance && length2 > maxAllowDistance)
				return -1.0;
			else {
				return Math.min(length1, length2);
			}
		}
		// find the dot product of two above vectors
		List<Double> dotProduct = new ArrayList<Double>();
		dotProduct.add(vector1.get(1) * vector2.get(2) - vector1.get(2)
				* vector2.get(1));
		dotProduct.add(vector1.get(2) * vector2.get(0) - vector1.get(0)
				* vector2.get(2));
		dotProduct.add(vector1.get(0) * vector2.get(1) - vector1.get(1)
				* vector2.get(0));

		// find the distance by the area of the triangle
		double result = Math.sqrt(dotProduct.get(0) * dotProduct.get(0)
				+ dotProduct.get(1) * dotProduct.get(1) + dotProduct.get(2)
				* dotProduct.get(2))
				/ lengthOfPath;
		if (result > maxAllowDistance)
			return -1.0;
		return result;
	}

	public Double angleBetween(Double latGoodsStart, Double longGoodsStart,
			Double latGoodsEnd, Double longGoodsEnd, Double latRouteStart,
			Double longRouteStart, Double latRouteEnd, Double longRouteEnd) {

		List<Double> coordGoodsStart = ChangeCoordinateMapToCoordinate3D(
				latGoodsStart, longGoodsStart);
		List<Double> coordGoodsEnd = ChangeCoordinateMapToCoordinate3D(
				latGoodsEnd, longGoodsEnd);
		List<Double> coordRouteStart = ChangeCoordinateMapToCoordinate3D(
				latRouteStart, longRouteStart);
		List<Double> coordRouteEnd = ChangeCoordinateMapToCoordinate3D(
				latRouteEnd, longRouteEnd);

		List<Double> vectorOfGoods = new ArrayList<Double>();
		vectorOfGoods.add(coordGoodsStart.get(0) - coordGoodsEnd.get(0));
		vectorOfGoods.add(coordGoodsStart.get(1) - coordGoodsEnd.get(1));
		vectorOfGoods.add(coordGoodsStart.get(2) - coordGoodsEnd.get(2));

		List<Double> vectorOfRoute = new ArrayList<Double>();
		vectorOfRoute.add(coordRouteStart.get(0) - coordRouteEnd.get(0));
		vectorOfRoute.add(coordRouteStart.get(1) - coordRouteEnd.get(1));
		vectorOfRoute.add(coordRouteStart.get(2) - coordRouteEnd.get(2));

		Double lengthOfGoods = Math.sqrt(vectorOfGoods.get(0)
				* vectorOfGoods.get(0) + vectorOfGoods.get(1)
				* vectorOfGoods.get(1) + vectorOfGoods.get(2)
				* vectorOfGoods.get(2));

		Double lengthOfRoute = Math.sqrt(vectorOfRoute.get(0)
				* vectorOfRoute.get(0) + vectorOfRoute.get(1)
				* vectorOfRoute.get(1) + vectorOfRoute.get(2)
				* vectorOfRoute.get(2));
		Double theta = (vectorOfGoods.get(0) * vectorOfRoute.get(0)
				+ vectorOfGoods.get(1) * vectorOfRoute.get(1) + 
				vectorOfGoods.get(2) * vectorOfRoute.get(2))
				/ (lengthOfGoods * lengthOfRoute);

		Double angleInDegrees = Math.acos(theta) * 180.0 / Math.PI;

		return angleInDegrees;
	}
}

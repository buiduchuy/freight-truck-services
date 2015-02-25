/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * @author Huy
 *
 */
public class MapsProcess {

	public String makeURL(LatLng src, List<LatLng> listMarker, LatLng des) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");
		urlString.append(Double.toString(src.getLatitude()));
		urlString.append(",");
		urlString.append(Double.toString(src.getLongitude()));
		urlString.append("&destination=");
		urlString.append(Double.toString(des.getLatitude()));
		urlString.append(",");
		urlString.append(Double.toString(des.getLongitude()));
		int s = listMarker.size();
		if (listMarker.size() != 0) {
			urlString.append("&waypoints=");
			String waypoints = "";
			for (int i = 0; i < s; i++) {
				waypoints += listMarker.get(i).getLatitude();
				waypoints += ",";
				waypoints += listMarker.get(i).getLongitude();
				if (s > 2) {
					waypoints += "|";
				}
			}
			try {
				waypoints = URLEncoder.encode(waypoints, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			urlString.append(waypoints);
		}
		urlString.append("&mode=driving");
		return urlString.toString();
	}

	public boolean checkDistance(LatLng goodsStartLocation,
			LatLng goodsFinishLocation, String result, double maxAllowDistance) {
		MatchingProcess matching = new MatchingProcess();

		try {
			final JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);
			List<Double> b_goodsStart = new ArrayList<Double>();
			List<Double> b_goodsFinish = new ArrayList<Double>();
			for (int z = 0; z < list.size() - 1; z++) {
				LatLng src = list.get(z);
				LatLng des = list.get(z + 1);
				b_goodsStart.add(matching.calDistance(
						goodsStartLocation.getLatitude(),
						goodsStartLocation.getLongitude(), src.getLatitude(),
						src.getLongitude(), des.getLatitude(),
						des.getLongitude(), maxAllowDistance));
				b_goodsFinish.add(matching.calDistance(
						goodsFinishLocation.getLatitude(),
						goodsFinishLocation.getLongitude(), src.getLatitude(),
						src.getLongitude(), des.getLatitude(),
						des.getLongitude(), maxAllowDistance));
			}

			boolean b_distanceGoodsStart = false;
			for (double distance : b_goodsStart) {
				if (distance > -1.0 && distance <= maxAllowDistance) {
					b_distanceGoodsStart = true;
					break;
				}
			}
			boolean b_distanceGoodsFinish = false;
			for (double distance : b_goodsFinish) {
				if (distance > -1.0 && distance <= maxAllowDistance) {
					b_distanceGoodsFinish = true;
					break;
				}
			}

			if (b_distanceGoodsStart && b_distanceGoodsFinish) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	private List<LatLng> decodePoly(String encoded) {

		List<LatLng> poly = new ArrayList<LatLng>();
		int index = 0, len = encoded.length();
		int lat = 0, lng = 0;

		while (index < len) {
			int b, shift = 0, result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lat += dlat;

			shift = 0;
			result = 0;
			do {
				b = encoded.charAt(index++) - 63;
				result |= (b & 0x1f) << shift;
				shift += 5;
			} while (b >= 0x20);
			int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
			lng += dlng;

			LatLng p = new LatLng((((double) lat / 1E5)),
					(((double) lng / 1E5)));
			poly.add(p);
		}
		return poly;
	}

}

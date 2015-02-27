/**
 * 
 */
package vn.edu.fpt.fts.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import vn.edu.fpt.fts.dao.RouteDAO;
import vn.edu.fpt.fts.dao.RouteMarkerDAO;
import vn.edu.fpt.fts.pojo.RouteMarker;

/**
 * @author Huy
 *
 */
public class MapsProcess {

	private final static String TAG = "MapsProcess";

	public String makeURL(String src, List<String> listMarker, String des) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");
		try {
			src = URLEncoder.encode(src, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		urlString.append(src);
		urlString.append("&destination=");
		try {
			des = URLEncoder.encode(des, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		urlString.append(des);
		int s = listMarker.size();
		String c_vertical = "|";
		if (listMarker.size() != 0) {
			// %7C is |
			String waypoints = "";
			waypoints += "&waypoints=optimize:true" + c_vertical;
			for (int i = 0; i < s; i++) {
				waypoints += listMarker.get(i);
				if (s > 1) {
					waypoints += c_vertical;
				}
			}
			try {
				waypoints = URLEncoder.encode(waypoints, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
			urlString.append(waypoints);
		}
		urlString.append("&mode=driving");
		return urlString.toString();
	}

	public List<LatLng> parseJsonList(String jsonResult) {
		try {
			final JSONObject json = new JSONObject(jsonResult);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);
			return list;
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return null;
	}

	public LatLng parseJsonRouteStart(String jsonResult) {
		LatLng latLng = new LatLng();
		try {
			final JSONObject jObject = new JSONObject(jsonResult);
			JSONArray routes = jObject.getJSONArray("routes");
			// Grab the first route
			JSONObject route = routes.getJSONObject(0);
			// Take all legs from the route
			JSONArray legs = route.getJSONArray("legs");
			// Grab first leg
			JSONObject leg = legs.getJSONObject(0);

			JSONObject start_location = leg.getJSONObject("start_location");
			latLng.setLatitude(start_location.getDouble("lat"));
			latLng.setLongitude(start_location.getDouble("lng"));
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return latLng;
	}

	public LatLng parseJsonRouteEnd(String jsonResult) {
		LatLng latLng = new LatLng();
		try {
			final JSONObject jObject = new JSONObject(jsonResult);
			JSONArray routes = jObject.getJSONArray("routes");
			// Grab the first route
			JSONObject route = routes.getJSONObject(0);
			// Take all legs from the route
			JSONArray legs = route.getJSONArray("legs");
			// Grab first leg
			JSONObject leg = legs.getJSONObject(0);

			JSONObject end_location = leg.getJSONObject("end_location");
			latLng.setLatitude(end_location.getDouble("lat"));
			latLng.setLongitude(end_location.getDouble("lng"));
		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return latLng;
	}

	public String getJSONFromUrl(String url) {
		InputStream is = null;
		String json = "";
		try {
			System.out.println("URL: " + url);
			long startTime = System.currentTimeMillis();
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(url);

			HttpResponse httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			long stopTime = System.currentTimeMillis();
			System.out.println("Request time: " + (stopTime - startTime));
			// Log.d("message", ("Request time: " + (stopTime - startTime)));
			is = httpEntity.getContent();

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		} catch (IOException e) {
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		try {
			String line = "";
			StringBuilder total = new StringBuilder();

			// Wrap a BufferedReader around the InputStream
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				// Read response until the end
				while ((line = rd.readLine()) != null) {
					total.append(line);
				}
			} catch (IOException e) {
				e.printStackTrace();
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
			// Return full string
			json = total.toString();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			// Log.e("Buffer Error", "Error converting result " + e.toString());
		}
		return json;
	}

	public boolean checkDistance(int routeID, LatLng goodsStartLocation,
			LatLng goodsFinishLocation, double maxAllowDistance) {
		MatchingUtils matching = new MatchingUtils();

		RouteDAO routeDao = new RouteDAO();
		RouteMarkerDAO routeMarkerDao = new RouteMarkerDAO();

		List<RouteMarker> listRouteMarker = routeMarkerDao
				.getAllRouteMarkerByRouteID(routeID);

		List<String> l_listRouteMarker = new ArrayList<String>();

		if (listRouteMarker.size() != 0) {
			for (int i = 0; i < listRouteMarker.size(); i++) {
				l_listRouteMarker.add(routeMarkerDao
						.getAllRouteMarkerByRouteID(routeID).get(i)
						.getRouteMarkerLocation());
			}
		} else {
			l_listRouteMarker.add("");
		}

		String json = getJSONFromUrl(makeURL(routeDao.getRouteByID(routeID)
				.getStartingAddress(), l_listRouteMarker, routeDao
				.getRouteByID(routeID).getDestinationAddress()));

		LatLng routeStart = parseJsonRouteStart(json);
		LatLng routeEnd = parseJsonRouteEnd(json);

		Double angleBetween2vector = matching.angleBetween(
				goodsStartLocation.getLatitude(),
				goodsStartLocation.getLongitude(),
				goodsFinishLocation.getLatitude(),
				goodsFinishLocation.getLongitude(), routeStart.getLatitude(),
				routeStart.getLongitude(), routeEnd.getLatitude(),
				routeEnd.getLongitude());

		if (angleBetween2vector > 0 && angleBetween2vector < 90) {
			List<LatLng> list = parseJsonList(json);
			try {

				List<Double> l_goodsStart = new ArrayList<Double>();
				List<Double> l_goodsFinish = new ArrayList<Double>();
				for (int z = 0; z < list.size() - 1; z++) {
					LatLng src = list.get(z);
					LatLng des = list.get(z + 1);
					l_goodsStart.add(matching.calDistance(
							goodsStartLocation.getLatitude(),
							goodsStartLocation.getLongitude(),
							src.getLatitude(), src.getLongitude(),
							des.getLatitude(), des.getLongitude(),
							maxAllowDistance));
					l_goodsFinish.add(matching.calDistance(
							goodsFinishLocation.getLatitude(),
							goodsFinishLocation.getLongitude(),
							src.getLatitude(), src.getLongitude(),
							des.getLatitude(), des.getLongitude(),
							maxAllowDistance));

				}

				System.out
						.println("Distance from start of goods to route "
								+ "with condition nearest " + maxAllowDistance
								+ " km:");
				boolean b_distanceGoodsStart = false;
				for (double distance : l_goodsStart) {
					if (distance > -1.0 && distance <= maxAllowDistance) {
						System.out.println(distance + " km");
						b_distanceGoodsStart = true;
					}
				}
				System.out
						.println("Distance from destination of goods to route "
								+ "with condition nearest " + maxAllowDistance
								+ " km:");
				boolean b_distanceGoodsFinish = false;
				for (double distance : l_goodsFinish) {
					if (distance > -1.0 && distance <= maxAllowDistance) {
						System.out.println(distance + " km");
						b_distanceGoodsFinish = true;
					}
				}

				if (b_distanceGoodsStart && b_distanceGoodsFinish) {
					return true;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				Logger.getLogger(TAG).log(Level.SEVERE, null, e);
			}
		}
		System.out.println("Goods Start: " + goodsStartLocation.getLatitude()
				+ " , " + goodsStartLocation.getLongitude());
		System.out.println("Goods Finish: " + goodsFinishLocation.getLatitude()
				+ " , " + goodsFinishLocation.getLongitude());
		System.out
				.println("Angle between goods and route is larger than 90degree: "
						+ angleBetween2vector);
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

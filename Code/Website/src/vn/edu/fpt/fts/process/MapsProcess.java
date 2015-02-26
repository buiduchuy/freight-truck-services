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
	public static final String API_KEY = "AIzaSyD_etqEdI3WY_xfwnnJNuzT8uLalBofaT0";
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

	public String getJSONFromUrl(String url) {
		InputStream is = null;
		String json = "";
		try {
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
		MatchingProcess matching = new MatchingProcess();

		RouteDAO routeDao = new RouteDAO();
		RouteMarkerDAO routeMarkerDao = new RouteMarkerDAO();

		List<RouteMarker> listRouteMarker = routeMarkerDao
				.getAllRouteMarkerByRouteID(routeID);

		List<String> l_listRouteMarker = new ArrayList<String>();

		for (int i = 0; i < listRouteMarker.size(); i++) {
			l_listRouteMarker.add(routeMarkerDao
					.getAllRouteMarkerByRouteID(routeID).get(i)
					.getRouteMarkerLocation());
		}

		String json = getJSONFromUrl(makeURL(routeDao.getRouteByID(routeID)
				.getStartingAddress(), l_listRouteMarker, routeDao
				.getRouteByID(routeID).getDestinationAddress()));

		List<LatLng> list = parseJsonList(json);
		try {

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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
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

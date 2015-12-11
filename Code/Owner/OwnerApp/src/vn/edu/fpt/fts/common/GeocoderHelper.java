package vn.edu.fpt.fts.common;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Color;

public class GeocoderHelper {
	List<Polyline> polylines = new ArrayList<Polyline>();

	public boolean checkPath(String result) {
		try {
			JSONObject jsonObject = new JSONObject(result);
			String status = jsonObject.getString("status");
			if (status.equals("ZERO_RESULTS")) {
				return false;
			} else {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

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

			LatLng p = new LatLng(((lat / 1E5)),
					((lng / 1E5)));
			poly.add(p);
		}

		return poly;
	}

	public void drawPath(String result, GoogleMap map) {
		try {
			final JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);

			for (int z = 0; z < list.size() - 1; z++) {
				LatLng src = list.get(z);
				LatLng dest = list.get(z + 1);
				polylines.add(map.addPolyline(new PolylineOptions()
						.add(new LatLng(src.latitude, src.longitude),
								new LatLng(dest.latitude, dest.longitude))
						.width(6).color(Color.BLUE).geodesic(true)));
			}

		} catch (JSONException e) {

		}
	}

	public LatLng getLatLong(JSONObject jsonObject) {
		LatLng result;
		try {

			double longitute = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").getDouble("lng");

			double latitude = ((JSONArray) jsonObject.get("results"))
					.getJSONObject(0).getJSONObject("geometry")
					.getJSONObject("location").getDouble("lat");
			result = new LatLng(latitude, longitute);
		} catch (JSONException e) {
			return null;

		}

		return result;
	}

	public JSONObject getLocationInfo(String address) {
		StringBuilder stringBuilder = new StringBuilder();
		try {

			address = address.replaceAll(" ", "%20");

			HttpPost httppost = new HttpPost(
					"http://maps.google.com/maps/api/geocode/json?address="
							+ address + "&sensor=false");
			HttpClient client = new DefaultHttpClient();
			HttpResponse response;
			stringBuilder = new StringBuilder();

			response = client.execute(httppost);
			HttpEntity entity = response.getEntity();
			InputStream stream = entity.getContent();
			int b;
			while ((b = stream.read()) != -1) {
				stringBuilder.append((char) b);
			}
		} catch (ClientProtocolException e) {
			return null;
		} catch (IOException e) {
			return null;
		}

		JSONObject jsonObject = new JSONObject();
		try {
			jsonObject = new JSONObject(stringBuilder.toString());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		return jsonObject;
	}

	public String makeURL(LatLng org, LatLng des) {
		StringBuilder urlString = new StringBuilder();
		urlString
				.append("https://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");
		urlString.append(Double.toString(org.latitude));
		urlString.append(",");
		urlString.append(Double.toString(org.longitude));
		urlString.append("&destination=");
		urlString.append(Double.toString(des.latitude));
		urlString.append(",");
		urlString.append(Double.toString(des.longitude));
		urlString
				.append("&mode=driving&region=vi");
		return urlString.toString();
	}

	public String makeURL2(LatLng org, LatLng p1, LatLng p2, LatLng des) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");
		urlString.append(Double.toString(org.latitude));
		urlString.append(",");
		urlString.append(Double.toString(org.longitude));
		urlString.append("&destination=");
		urlString.append(Double.toString(des.latitude));
		urlString.append(",");
		urlString.append(Double.toString(des.longitude));
		if (p1 != null || p2 != null) {
			urlString.append("&waypoints=");
			String waypoints = "";
			if (p1 != null) {
				waypoints += Double.toString(p1.latitude);
				waypoints += ",";
				waypoints += Double.toString(p1.longitude);
			}
			if (p2 != null) {
				waypoints += "|";
				waypoints += Double.toString(p2.latitude);
				waypoints += ",";
				waypoints += Double.toString(p2.longitude);
			}
			try {
				waypoints = URLEncoder.encode(waypoints, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			urlString.append(waypoints);
		}
		urlString.append("&mode=driving&region=vi");
		return urlString.toString();
	}

	public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
		List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
		JSONArray jRoutes = null;
		JSONArray jLegs = null;
		JSONArray jSteps = null;
		try {
			jRoutes = jObject.getJSONArray("routes");
			/* Traversing all routes */
			for (int i = 0; i < jRoutes.length(); i++) {
				jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
				List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

				/* Traversing all legs */
				for (int j = 0; j < jLegs.length(); j++) {
					jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

					/* Traversing all steps */
					for (int k = 0; k < jSteps.length(); k++) {
						String polyline = "";
						polyline = (String) ((JSONObject) ((JSONObject) jSteps
								.get(k)).get("polyline")).get("points");
						List<LatLng> list = decodePoly(polyline);

						/* Traversing all points */
						for (int l = 0; l < list.size(); l++) {
							HashMap<String, String> hm = new HashMap<String, String>();
							hm.put("lat",
									Double.toString(list.get(l).latitude));
							hm.put("lng",
									Double.toString(list.get(l).longitude));
							path.add(hm);
						}
					}
					routes.add(path);
				}
			}

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (Exception e) {
		}
		return routes;
	}

	public void RemovePolylines() {
		for (Polyline line : polylines) {
			line.remove();
		}
		polylines.clear();
	}
}
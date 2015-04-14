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

/**
 * @author Huy
 *
 */
public class MapsUtil {

	public static void main(String[] args) {
//		MapsUtils mapsUtils = new MapsUtils();
//		CityDAO cityDao = new CityDAO();
//		List<City> l_city = cityDao.getAllCity();
//
//		for (int i = 0; i < l_city.size(); i++) {
//			LatLng latLng = mapsUtils.parseJson(mapsUtils
//					.getJSONFromUrl(mapsUtils.makeGeoCodeURL(l_city.get(i)
//							.getCityName())));
//			cityDao.updateLocationCity(l_city.get(i).getCityID(), latLng);
//
//			System.out.println(latLng.getLatitude() + " , "
//					+ latLng.getLongitude());
//		}
		
	}

	private final static String TAG = "MapsUtil";

	public String makeGeoCodeURL(String address) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("https://maps.googleapis.com/maps/api/geocode/json");
		urlString.append("?address=");
		try {
			address = URLEncoder.encode(address, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		urlString.append(address);
		return urlString.toString();
	}

	public LatLng parseJson(String jsonResult) {
		LatLng latLng = new LatLng();
		try {
			final JSONObject json = new JSONObject(jsonResult);
			JSONArray resultArray = json.getJSONArray("results");
			JSONObject results = resultArray.getJSONObject(0);
			JSONObject geometry = results.getJSONObject("geometry");
			JSONObject location = geometry.getJSONObject("location");

			latLng.setLatitude(location.getDouble("lat"));
			latLng.setLongitude(location.getDouble("lng"));

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

	public String makeDirectionURL(String pickupAddress, String deliveryAddress) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?origin=");
		try {
			pickupAddress = URLEncoder.encode(pickupAddress, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		urlString.append(pickupAddress);
		urlString.append("&destination=");
		try {
			deliveryAddress = URLEncoder.encode(deliveryAddress, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		urlString.append(deliveryAddress);
		return urlString.toString();
	}

	public double parseJsonToGetDistance(String jsonResult) {
		double ret = 0.0;
		try {
			final JSONObject jObject = new JSONObject(jsonResult);
			JSONArray routes = jObject.getJSONArray("routes");
			// Grab the first route
			JSONObject route = routes.getJSONObject(0);
			// Take all legs from the route
			JSONArray legs = route.getJSONArray("legs");
			// Grab first leg
			JSONObject leg = legs.getJSONObject(0);

			JSONObject distance = leg.getJSONObject("distance");

			ret = distance.getDouble("value");

		} catch (JSONException e) {
			// TODO: handle exception
			e.printStackTrace();
			Logger.getLogger(TAG).log(Level.SEVERE, null, e);
		}
		return ret;
	}

}

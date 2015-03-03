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

import vn.edu.fpt.fts.dao.GoodsDAO;
import vn.edu.fpt.fts.pojo.Goods;

/**
 * @author Huy
 *
 */
public class MapsUtils {

	public static void main(String[] args) {
		MapsUtils mapsUtils = new MapsUtils();
		GoodsDAO goodsDao = new GoodsDAO();
		List<Goods> l_goods = goodsDao.getAllGoods();

		for (int i = 0; i < l_goods.size(); i++) {
			LatLng latLngSrc = mapsUtils.parseJson(mapsUtils
					.getJSONFromUrl(mapsUtils.makeURL(l_goods.get(i)
							.getPickupAddress())));
			LatLng latLngDes = mapsUtils.parseJson(mapsUtils
					.getJSONFromUrl(mapsUtils.makeURL(l_goods.get(i)
							.getDeliveryAddress())));
			goodsDao.updateLocationGoods(l_goods.get(i).getGoodsID(), latLngSrc, latLngDes);
			
			System.out.println(latLngSrc.getLatitude() + " , "
					+ latLngSrc.getLongitude());
			System.out.println(latLngDes.getLatitude() + " , "
					+ latLngDes.getLongitude());
		}
	}

	private final static String TAG = "MapsUtils";

	public String makeURL(String address) {
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

}

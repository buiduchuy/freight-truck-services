package vn.edu.fpt.fts.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.fpt.fts.classes.Constant;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;

public class GeocoderHelper {
	List<Polyline> polylines = new ArrayList<Polyline>();
	Activity activity;
	private static final String SERVICE_URL2 = Constant.SERVICE_URL
			+ "City/get";

	public void RemovePolylines() {
		for (Polyline line : polylines) {
			line.remove();
		}
		polylines.clear();
	}

	public GeocoderHelper(Activity activity) {
		this.activity = activity;
	}

	public GeocoderHelper() {

	}

	public String makeURL(LatLng org, LatLng p1, LatLng p2, LatLng des) {
		StringBuilder urlString = new StringBuilder();
		urlString.append("http://maps.googleapis.com/maps/api/directions/json");
		urlString.append("?mode=driving&origin=");
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

	public void drawPath(String result, GoogleMap map) {
		try {
			ArrayList<LatLng> points = null;
			PolylineOptions polyLineOptions = null;
			List<List<HashMap<String, String>>> routes = null;
			JSONObject obj = new JSONObject(result);
			routes = parse(obj);

			// traversing through routes
			for (int i = 0; i < routes.size(); i++) {
				points = new ArrayList<LatLng>();
				polyLineOptions = new PolylineOptions();
				List<HashMap<String, String>> path = routes.get(i);

				for (int j = 0; j < path.size(); j++) {
					HashMap<String, String> point = path.get(j);

					double lat = Double.parseDouble(point.get("lat"));
					double lng = Double.parseDouble(point.get("lng"));
					LatLng position = new LatLng(lat, lng);

					points.add(position);
				}
				polyLineOptions.addAll(points);
				polyLineOptions.width(2);
				polyLineOptions.color(Color.BLUE);
			}
			Polyline line = map.addPolyline(polyLineOptions);
			polylines.add(line);
		} catch (JSONException e) {

		}
	}

	public List<LatLng> getPoints(String result) {
		try {
			final JSONObject json = new JSONObject(result);
			JSONArray routeArray = json.getJSONArray("routes");
			JSONObject routes = routeArray.getJSONObject(0);
			JSONObject overviewPolylines = routes
					.getJSONObject("overview_polyline");
			String encodedString = overviewPolylines.getString("points");
			List<LatLng> list = decodePoly(encodedString);
			return list;
		} catch (JSONException e) {

		}
		return null;
	}
	
	public List<List<HashMap<String, String>>> parse(JSONObject jObject) {
		List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
		JSONArray jRoutes = null;
		JSONArray jLegs = null;
		JSONArray jSteps = null;
		try {
			jRoutes = jObject.getJSONArray("routes");
			/** Traversing all routes */
			for (int i = 0; i < jRoutes.length(); i++) {
				jLegs = ((JSONObject) jRoutes.get(i)).getJSONArray("legs");
				List<HashMap<String, String>> path = new ArrayList<HashMap<String, String>>();

				/** Traversing all legs */
				for (int j = 0; j < jLegs.length(); j++) {
					jSteps = ((JSONObject) jLegs.get(j)).getJSONArray("steps");

					/** Traversing all steps */
					for (int k = 0; k < jSteps.length(); k++) {
						String polyline = "";
						polyline = (String) ((JSONObject) ((JSONObject) jSteps
								.get(k)).get("polyline")).get("points");
						List<LatLng> list = decodePoly(polyline);

						/** Traversing all points */
						for (int l = 0; l < list.size(); l++) {
							HashMap<String, String> hm = new HashMap<String, String>();
							hm.put("lat",
									Double.toString(((LatLng) list.get(l)).latitude));
							hm.put("lng",
									Double.toString(((LatLng) list.get(l)).longitude));
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

	public double CalculationByDistance(LatLng StartP, LatLng EndP) {
		float[] result = new float[1];
		Location.distanceBetween(StartP.latitude, StartP.longitude,
				EndP.latitude, EndP.longitude, result);
		return result[0];
	}

	public ArrayList<String> getMiddlePoints(String startPoint, String endPoint) {
		LatLng start;
		LatLng end;
		ArrayList<String> list = new ArrayList<String>();
		try {
			start = new GetLatLng().execute(startPoint).get();
			end = new GetLatLng().execute(endPoint).get();
			GeocoderHelper helper = new GeocoderHelper();
			double distance = helper.CalculationByDistance(start, end);
			List<LatLng> points = new GetPoints().execute(
					helper.makeURL(start, null, null, end)).get();
			LatLng pnt1 = new LatLng(0, 0);
			LatLng pnt2 = new LatLng(0, 0);
			boolean flag1 = true;
			boolean flag2 = true;
			for (int i = 0; i < points.size(); i++) {
				if (flag1) {
					if (helper.CalculationByDistance(start, points.get(i)) >= (helper
							.CalculationByDistance(start, end) / 3)) {
						pnt1 = points.get(i);
						flag1 = false;
					}
				}
				if (flag2) {
					if (helper.CalculationByDistance(start, points.get(i)) >= (helper
							.CalculationByDistance(start, end) / 3 * 2)) {
						pnt2 = points.get(i);
						flag2 = false;
					}
				}
			}
			JSONObject middle1 = new JSONObject();
			JSONObject middle2 = new JSONObject();
			CalculateMiddlePoints service = new CalculateMiddlePoints(
					CalculateMiddlePoints.GET_TASK, activity,
					"Đang tìm điểm đi qua");
			String response = service.execute(SERVICE_URL2).get();
			JSONArray cities = new JSONObject(response).getJSONArray("city");
			middle1 = cities.getJSONObject(0);
			middle2 = cities.getJSONObject(0);
			LatLng min1 = new LatLng(Double.parseDouble(middle1
					.getString("latitude")), Double.parseDouble(middle1
					.getString("longitude")));

			LatLng min2 = new LatLng(Double.parseDouble(middle2
					.getString("latitude")), Double.parseDouble(middle2
					.getString("longitude")));
			for (int i = 0; i < cities.length(); i++) {
				JSONObject obj = cities.getJSONObject(i);
				LatLng point1 = new LatLng(Double.parseDouble(obj
						.getString("latitude")), Double.parseDouble(obj
						.getString("longitude")));
				if ((start.latitude > point1.latitude && point1.latitude > end.latitude)
						|| (start.latitude < point1.latitude && point1.latitude < end.latitude)) {
					if (helper.CalculationByDistance(point1, pnt1) < helper
							.CalculationByDistance(min1, pnt1)) {
						min1 = point1;
						middle1 = obj;
					}

					if (helper.CalculationByDistance(point1, pnt2) < helper
							.CalculationByDistance(min2, pnt2)) {
						min2 = point1;
						middle2 = obj;
					}
				}
			}
			list.add(middle1.getString("cityName"));
			list.add(middle2.getString("cityName"));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	private class GetLatLng extends AsyncTask<String, Void, LatLng> {
		private final AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient
				.newInstance(GetLatLng.class.getName());

		@Override
		protected LatLng doInBackground(String... locations) {
			String googleMapUrl = "http://maps.googleapis.com/maps/api/directions/json?origin="
					+ locations[0]
					+ "&destination="
					+ locations[0]
					+ "&sensor=false";

			try {
				JSONObject googleMapResponse = new JSONObject(
						ANDROID_HTTP_CLIENT.execute(
								new HttpGet(googleMapUrl.replace(" ", "%20")),
								new BasicResponseHandler()));
				JSONArray results = (JSONArray) googleMapResponse.get("routes");
				JSONObject result = results.getJSONObject(0);
				JSONObject location = result.getJSONObject("bounds")
						.getJSONObject("northeast");
				double latitude = location.getDouble("lat");
				double longitude = location.getDouble("lng");
				ANDROID_HTTP_CLIENT.close();
				return new LatLng(latitude, longitude);
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(LatLng result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ANDROID_HTTP_CLIENT.close();
		}
	}

	private class GetPoints extends AsyncTask<String, Void, List<LatLng>> {
		long startTime;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			startTime = System.nanoTime();
		}

		@Override
		protected List<LatLng> doInBackground(String... params) {
			JSONParser jParser = new JSONParser();
			String json = jParser.getJSONFromUrl(params[0]);
			GeocoderHelper helper = new GeocoderHelper();
			return helper.getPoints(json);
		}

		@Override
		protected void onPostExecute(List<LatLng> result) {
			super.onPostExecute(result);
		}
	}

	private class CalculateMiddlePoints extends
			AsyncTask<String, String, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 30000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public CalculateMiddlePoints(int taskType, Context mContext,
				String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

		@Override
		protected void onPreExecute() {
			showProgressDialog();

		}

		protected String doInBackground(String... urls) {
			String url = urls[0];
			String result = "";

			HttpResponse response = doResponse(url);

			if (response == null) {
				return result;
			} else {
				try {
					result = inputStreamToString(response.getEntity()
							.getContent());

				} catch (IllegalStateException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);

				} catch (IOException e) {
					Log.e(TAG, e.getLocalizedMessage(), e);
				}

			}

			return result;
		}

		@Override
		protected void onPostExecute(String response) {
			// Xu li du lieu tra ve sau khi insert thanh cong
			// handleResponse(response);
			pDlg.dismiss();
		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
		}

		private HttpResponse doResponse(String url) {

			// Use our connection and data timeouts as parameters for our
			// DefaultHttpClient
			HttpClient httpclient = new DefaultHttpClient(getHttpParams());

			HttpResponse response = null;

			try {
				switch (taskType) {

				case POST_TASK:
					HttpPost httppost = new HttpPost(url);
					// Add parameters
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));

					response = httpclient.execute(httppost);
					break;
				case GET_TASK:
					HttpGet httpget = new HttpGet(url);
					response = httpclient.execute(httpget);
					break;
				}
			} catch (ConnectTimeoutException e) {
				activity.runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(activity,
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {
				Log.e(TAG, e.getLocalizedMessage(), e);

			}

			return response;
		}

		private String inputStreamToString(InputStream is) {

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
				Log.e(TAG, e.getLocalizedMessage(), e);
			}

			// Return full string
			return total.toString();
		}

	}

}

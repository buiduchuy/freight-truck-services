package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import vn.edu.fpt.fts.helper.GeocoderHelper;
import vn.edu.fpt.fts.helper.JSONParser;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public class Map extends Fragment implements OnMapReadyCallback {
	GoogleMap map;
	GeocoderHelper helper = new GeocoderHelper();
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Route/getRouteByID";
	LatLngBounds.Builder b = new LatLngBounds.Builder();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		getActivity().getActionBar().setIcon(R.drawable.ic_action_place_white);
		getActivity().getActionBar().setTitle("Xem lộ trình");
		View v = inflater.inflate(R.layout.map, container, false);
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), 0);
		SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		map = mapFragment.getMap();
		return v;
	}

	private class WebService extends AsyncTask<String, Integer, String> {

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

		public WebService(int taskType, Context mContext, String processMessage) {

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
			JSONObject obj;
			try {
				obj = new JSONObject(response);
				Object intervent;
				String start = obj.getString("startingAddress");
				String p1 = "", p2 = "";
				if (obj.has("routeMarkers")) {
					intervent = obj.get("routeMarkers");
					if (intervent instanceof JSONArray) {
						JSONArray catArray = obj.getJSONArray("routeMarkers");
						for (int j = 0; j < catArray.length(); j++) {
							JSONObject cat = catArray.getJSONObject(j);
							if (j == 0) {
								if (!cat.getString("routeMarkerLocation")
										.equals("")) {
									p1 = cat.getString("routeMarkerLocation");
								}
							} else if (j == 1) {
								if (!cat.getString("routeMarkerLocation")
										.equals("")) {
									p2 = cat.getString("routeMarkerLocation");
								}
							}
						}
					} else if (intervent instanceof JSONObject) {
						JSONObject cat = obj.getJSONObject("routeMarkers");
						p1 = cat.getString("routeMarkerLocation");
					}
				}
				String end = obj.getString("destinationAddress");
				LatLng startP = new GetLatLng().execute(start).get();
				map.addMarker(new MarkerOptions()
						.position(startP)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.driver_marker_icon_small)));
				LatLng endP = new GetLatLng().execute(end).get();
				map.addMarker(new MarkerOptions()
						.position(endP)
						.icon(BitmapDescriptorFactory
								.fromResource(R.drawable.driver_marker_icon_small)));
				LatLng pp1 = null, pp2 = null;
				if (!p1.equals("")) {
					pp1 = new GetLatLng().execute(p1).get();
					map.addMarker(new MarkerOptions()
							.position(pp1)
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.driver_marker_icon_small)));
				}
				if (!p2.equals("")) {
					pp2 = new GetLatLng().execute(p2).get();
					map.addMarker(new MarkerOptions()
							.position(pp2)
							.icon(BitmapDescriptorFactory
									.fromResource(R.drawable.driver_marker_icon_small)));
				}
				b.include(startP);
				b.include(endP);
				b.include(pp1);
				b.include(pp2);
				String url = helper.makeURL(startP, pp1, pp2, endP);
				new connectAsyncTask().execute(url);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
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

	private class connectAsyncTask extends AsyncTask<String, Void, String> {
		long startTime;
		ProgressDialog pDlg;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDlg = new ProgressDialog(getActivity());
			pDlg.setMessage("Đang vẽ lộ trình ...");
			pDlg.setProgressDrawable(getActivity().getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();
			startTime = System.nanoTime();
		}

		@Override
		protected String doInBackground(String... params) {
			JSONParser jParser = new JSONParser();
			String json = jParser.getJSONFromUrl(params[0]);
			return json;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			JSONObject obj;
			try {
				obj = new JSONObject(result);
				if (obj.getString("status").equals("ZERO_RESULTS")) {
					Toast.makeText(getActivity(),
							"Không có lộ trình qua các điểm này",
							Toast.LENGTH_SHORT).show();
				} else {
					helper.drawPath(result, map);
					map.setMyLocationEnabled(true);
					map.getUiSettings().setMyLocationButtonEnabled(true);
					LatLngBounds bounds = b.build();
					CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(
							bounds, 20);
					map.animateCamera(cu);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pDlg.dismiss();
		}
	}

	public void onMapReady(GoogleMap map) {
		WebService ws = new WebService(WebService.POST_TASK, getActivity(),
				"Đang lấy dữ liệu ...");
		ws.addNameValuePair("routeID", getArguments().getString("routeID"));
		ws.execute(new String[] { SERVICE_URL });
	}
}

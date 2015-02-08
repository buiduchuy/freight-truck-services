package test.example.cp;

import helper.GeocoderHelper;
import helper.JSONParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.drive.internal.ar;
import com.google.android.gms.internal.ge;
import com.google.android.gms.internal.lt;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.maps.GeoPoint;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class CustomizeRoute extends Fragment implements OnMapReadyCallback {
	ArrayList<LatLng> locations = new ArrayList<LatLng>();
	LatLngBounds.Builder b = new LatLngBounds.Builder();
	GoogleMap map;
	GeocoderHelper helper = new GeocoderHelper();
	Button button;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.activity_customize_route, container,
				false);
		SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.map);
		mapFragment.getMapAsync(this);
		map = mapFragment.getMap();
		map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {

			@Override
			public void onMarkerDragStart(Marker arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onMarkerDragEnd(Marker mrk) {
				// TODO Auto-generated method stub
				int id = Integer.parseInt(mrk.getSnippet());
				locations.set(id - 1, mrk.getPosition());
				helper.RemovePolylines();
				for (int i = 0; i < locations.size() - 1; i++) {
					LatLng s = locations.get(i);
					LatLng e = locations.get(i + 1);
					String url = helper.makeURL(s.latitude, s.longitude,
							e.latitude, e.longitude);
					try {
						String result = new connectAsyncTask().execute(url)
								.get();
						helper.drawPath(result, map);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					map.setMyLocationEnabled(true);
					map.getUiSettings().setMyLocationButtonEnabled(true);
					for (LatLng p : locations) {
						MarkerOptions marker = new MarkerOptions();
						marker.position(p);
						b.include(marker.getPosition());
					}
					LatLngBounds bounds = b.build();
					CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(
							bounds, 20);
					map.animateCamera(cu);
				}
			}

			@Override
			public void onMarkerDrag(Marker arg0) {
				// TODO Auto-generated method stub

			}
		});
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng arg0) {
				if (locations.size() >= 4) {
					Toast.makeText(getActivity(),
							"Chỉ thêm được tối đa 2 địa điểm đi qua", Toast.LENGTH_SHORT).show();
				} else {
					for (int i = 0; i < locations.size() - 1; i++) {
						LatLng s = locations.get(i);
						LatLng e = locations.get(i + 1);
						if ((s.latitude > arg0.latitude && arg0.latitude > e.latitude)
								|| (s.latitude < arg0.latitude && arg0.latitude < e.latitude)) {
							locations.add(i + 1, arg0);
						}
					}
					map.clear();
					for (int i = 0; i < locations.size() - 1; i++) {
						map.addMarker(new MarkerOptions().position(locations.get(i)).draggable(true)
								.snippet(String.valueOf(i+1)));
						if (i == locations.size() - 2) {
							map.addMarker(new MarkerOptions().position(locations.get(i+1)).draggable(true)
									.snippet(String.valueOf(i + 2)));
						}
					}
					for (int i = 0; i < locations.size() - 1; i++) {
						LatLng s = locations.get(i);
						LatLng e = locations.get(i + 1);
						String url = helper.makeURL(s.latitude, s.longitude,
								e.latitude, e.longitude);
						try {
							String result = new connectAsyncTask().execute(url)
									.get();
							helper.drawPath(result, map);
						} catch (Exception ex) {
							ex.printStackTrace();
						}
					}
				}
			}
		});
		button = (Button) v.findViewById(R.id.save);
		button.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = getActivity().getIntent();

				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				Fragment fragment = new Fragment();
				if (intent.getStringExtra("sender").equalsIgnoreCase(
						"createRoute")) {
					fragment = getActivity().getSupportFragmentManager()
							.findFragmentByTag("createRoute");
					intent.putExtra("markerList", locations);
				} else {
					fragment = getActivity().getSupportFragmentManager()
							.findFragmentByTag("changeRoute");
					intent.putExtra("markerList2", locations);
				}
				trs.replace(R.id.content_frame, fragment);
				trs.commit();
			}
		});
		return v;
	}

	private class GetLatLng extends AsyncTask<String, Void, LatLng> {
		private final AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient
				.newInstance(GetLatLng.class.getName());

		@Override
		protected LatLng doInBackground(String... locations) {
			String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="
					+ locations[0] + "&sensor=false";

			try {
				JSONObject googleMapResponse = new JSONObject(
						ANDROID_HTTP_CLIENT.execute(
								new HttpGet(googleMapUrl.replace(" ", "%20")),
								new BasicResponseHandler()));
				JSONArray results = (JSONArray) googleMapResponse
						.get("results");
				JSONObject result = results.getJSONObject(0);
				JSONObject location = result.getJSONObject("geometry")
						.getJSONObject("location");
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
		private ProgressDialog progressDialog;
		String url;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
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
		}
	}

	@Override
	public void onMapReady(GoogleMap map) {
		try {
			Intent intent = getActivity().getIntent();
			String sender = intent.getStringExtra("sender");

			locations = new ArrayList<LatLng>();
			String startPoint = intent.getStringExtra("start");
			String point1 = intent.getStringExtra("p1");
			String point2 = intent.getStringExtra("p2");
			String endPoint = intent.getStringExtra("end");

			LatLng start = new GetLatLng().execute(startPoint).get();
			MarkerOptions startMarker = new MarkerOptions().draggable(true)
					.snippet("1");
			startMarker.position(start);
			locations.add(start);
			map.addMarker(startMarker);

			if (!point1.equals("")) {
				LatLng p1 = new GetLatLng().execute(point1).get();
				MarkerOptions p1Marker = new MarkerOptions().draggable(true)
						.snippet("2");
				p1Marker.position(p1);
				locations.add(p1);
				map.addMarker(p1Marker);
			}
			if (!point2.equals("")) {
				LatLng p2 = new GetLatLng().execute(point2).get();
				MarkerOptions p2Marker = new MarkerOptions().draggable(true)
						.snippet("3");
				p2Marker.position(p2);
				locations.add(p2);
				map.addMarker(p2Marker);
			}

			LatLng end = new GetLatLng().execute(endPoint).get();
			MarkerOptions endMarker = new MarkerOptions().draggable(true)
					.snippet("4");
			endMarker.position(end);
			locations.add(end);
			map.addMarker(endMarker);

			for (int i = 0; i < locations.size() - 1; i++) {
				LatLng s = locations.get(i);
				LatLng e = locations.get(i + 1);
				String url = helper.makeURL(s.latitude, s.longitude,
						e.latitude, e.longitude);
				try {
					String result = new connectAsyncTask().execute(url).get();
					helper.drawPath(result, map);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				map.setMyLocationEnabled(true);
				map.getUiSettings().setMyLocationButtonEnabled(true);
				for (LatLng p : locations) {
					MarkerOptions marker = new MarkerOptions();
					marker.position(p);
					b.include(marker.getPosition());
				}
				LatLngBounds bounds = b.build();
				CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds,
						20);
				map.animateCamera(cu);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}

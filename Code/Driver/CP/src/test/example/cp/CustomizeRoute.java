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
		map = mapFragment.getMap();
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng point) {
				MarkerOptions markerOptions = new MarkerOptions();
				markerOptions.position(point);
				locations.add(point);
				map.addMarker(markerOptions);
				Collections.swap(locations, locations.size() - 1,
						locations.size() - 2);
				helper.RemovePolylines();
				for (int i = 0; i < locations.size() - 1; i++) {
					LatLng start = locations.get(i);
					LatLng end = locations.get(i + 1);
					String url = helper.makeURL(start.latitude,
							start.longitude, end.latitude, end.longitude);
					try {
						String result = new connectAsyncTask().execute(url)
								.get();
						helper.drawPath(result, map);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		mapFragment.getMapAsync(this);

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
			if (sender.equals("createRoute")) {
				locations = (ArrayList<LatLng>) intent
						.getSerializableExtra("markerList");
			} else {
				locations = (ArrayList<LatLng>) intent
						.getSerializableExtra("markerList2");
			}
			if (locations == null || locations.size() == 0) {
				locations = new ArrayList<LatLng>();
				String startPoint = intent.getStringExtra("start");
				String endPoint = intent.getStringExtra("end");
				LatLng start = new GetLatLng().execute(startPoint).get();
				LatLng end = new GetLatLng().execute(endPoint).get();
				MarkerOptions startMarker = new MarkerOptions();
				startMarker.position(start);
				locations.add(start);
				map.addMarker(startMarker);
				MarkerOptions endMarker = new MarkerOptions();
				endMarker.position(end);
				locations.add(end);
				map.addMarker(endMarker);
				String url = helper.makeURL(start.latitude, start.longitude,
						end.latitude, end.longitude);
				String result = new connectAsyncTask().execute(url).get();
				helper.drawPath(result, map);
				map.setMyLocationEnabled(true);
				map.getUiSettings().setMyLocationButtonEnabled(true);
				for (LatLng p : locations) {
					MarkerOptions marker = new MarkerOptions();
					marker.position(p);
					b.include(marker.getPosition());
				}
			} else {
				for (int i = 0; i < locations.size() - 1; i++) {
					MarkerOptions markerOptions = new MarkerOptions();
					markerOptions.position(locations.get(i));
					map.addMarker(markerOptions);
					b.include(markerOptions.getPosition());
					if (i == locations.size() - 2) {
						MarkerOptions markerOptions2 = new MarkerOptions();
						markerOptions2.position(locations.get(i + 1));
						map.addMarker(markerOptions2);
						b.include(markerOptions2.getPosition());
					}
					LatLng start = locations.get(i);
					LatLng end = locations.get(i + 1);
					String url = helper.makeURL(start.latitude,
							start.longitude, end.latitude, end.longitude);
					try {
						String result = new connectAsyncTask().execute(url)
								.get();
						helper.drawPath(result, map);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			LatLngBounds bounds = b.build();
			CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 20);
			map.animateCamera(cu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
} 

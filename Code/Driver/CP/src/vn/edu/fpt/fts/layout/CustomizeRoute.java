package vn.edu.fpt.fts.layout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.ExecutionException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONObject;

import vn.edu.fpt.fts.helper.Common;
import vn.edu.fpt.fts.helper.GeocoderHelper;
import vn.edu.fpt.fts.helper.JSONParser;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.internal.pd;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CustomizeRoute extends Fragment implements OnMapReadyCallback {
	ArrayList<LatLng> locations = new ArrayList<LatLng>();
	LatLngBounds.Builder b = new LatLngBounds.Builder();
	GoogleMap map;
	GeocoderHelper helper = new GeocoderHelper();
	Button button;
	ProgressDialog pDlg;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().getActionBar().setIcon(R.drawable.ic_action_place_white);
		getActivity().getActionBar().setTitle("Tùy chỉnh lộ trình");
		View v = inflater.inflate(R.layout.activity_customize_route, container,
				false);
		InputMethodManager imm = (InputMethodManager) getActivity()
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(getActivity().getCurrentFocus()
				.getWindowToken(), 0);
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
				mrk.remove();
				String param = mrk.getPosition().latitude + ","
						+ mrk.getPosition().longitude;
				LatLng real = mrk.getPosition();
				try {
					real = new GetLatLng().execute(param).get();
					MarkerOptions realMarker = new MarkerOptions()
							.position(real).draggable(true)
							.snippet(mrk.getSnippet());
					map.addMarker(realMarker);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// TODO Auto-generated method stub
				int id = Integer.parseInt(mrk.getSnippet());
				locations.set(id - 1, real);
				helper.RemovePolylines();
				String url = "";
				if (locations.size() == 2) {
					url = helper.makeURL(locations.get(0), null, null,
							locations.get(1));
				} else if (locations.size() == 3) {
					url = helper.makeURL(locations.get(0), locations.get(1),
							null, locations.get(2));
				} else if (locations.size() == 4) {
					url = helper.makeURL(locations.get(0), locations.get(1),
							locations.get(2), locations.get(3));
				}
				try {
					new connectAsyncTask().execute(url);
				} catch (Exception ex) {
					ex.printStackTrace();
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
				String param = arg0.latitude + "," + arg0.longitude;
				try {
					arg0 = new GetLatLng().execute(param).get();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ExecutionException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (locations.size() >= 4) {
					Toast.makeText(getActivity(),
							"Chỉ thêm được tối đa 2 địa điểm đi qua",
							Toast.LENGTH_SHORT).show();
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
						map.addMarker(new MarkerOptions()
								.position(locations.get(i)).draggable(true)
								.snippet(String.valueOf(i + 1)));
						if (i == locations.size() - 2) {
							map.addMarker(new MarkerOptions()
									.position(locations.get(i + 1))
									.draggable(true)
									.snippet(String.valueOf(i + 2)));
						}
					}
					String url = "";
					if (locations.size() == 2) {
						url = helper.makeURL(locations.get(0), null, null,
								locations.get(1));
					} else if (locations.size() == 3) {
						url = helper.makeURL(locations.get(0),
								locations.get(1), null, locations.get(2));
					} else if (locations.size() == 4) {
						url = helper.makeURL(locations.get(0),
								locations.get(1), locations.get(2),
								locations.get(3));
					}
					try {
						new connectAsyncTask().execute(url);
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				}
			}
		});

		map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker arg0) {
				int id = Integer.parseInt(arg0.getSnippet());
				if (id != 1 && id != locations.size()) {
					locations.remove(id - 1);
					map.clear();
					for (int i = 0; i < locations.size() - 1; i++) {
						map.addMarker(new MarkerOptions()
								.position(locations.get(i)).draggable(true)
								.snippet(String.valueOf(i + 1)));
						if (i == locations.size() - 2) {
							map.addMarker(new MarkerOptions()
									.position(locations.get(i + 1))
									.draggable(true)
									.snippet(String.valueOf(i + 2)));
						}
					}
					String url = "";
					if (locations.size() == 2) {
						url = helper.makeURL(locations.get(0), null, null,
								locations.get(1));
					} else if (locations.size() == 3) {
						url = helper.makeURL(locations.get(0),
								locations.get(1), null, locations.get(2));
					}
					new connectAsyncTask().execute(url);
				}
				return true;
			}
		});
		return v;
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
			long stopTime = System.nanoTime();
			Log.d("message", ("Parse time: " + (stopTime - startTime)));
			startTime = System.nanoTime();
			helper.drawPath(result, map);
			stopTime = System.nanoTime();
			Log.d("message", ("Draw map time: " + (stopTime - startTime)));
			pDlg.dismiss();
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
			LatLng p1 = null;
			LatLng p2 = null;

			LatLng start = new GetLatLng().execute(startPoint).get();
			MarkerOptions startMarker = new MarkerOptions().draggable(true)
					.snippet("1");
			startMarker.position(start);
			locations.add(start);
			map.addMarker(startMarker);

			if (!point1.equals("")) {
				p1 = new GetLatLng().execute(point1).get();
				MarkerOptions p1Marker = new MarkerOptions().draggable(true)
						.snippet("2");
				p1Marker.position(p1);
				locations.add(p1);
				map.addMarker(p1Marker);
			}
			if (!point2.equals("")) {
				p2 = new GetLatLng().execute(point2).get();
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

			String url = helper.makeURL(start, p1, p2, end);
			try {
				new connectAsyncTask().execute(url);
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
			CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, 20);
			map.animateCamera(cu);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.findItem(R.id.action_create).setVisible(false);
		MenuItem item = menu.add(Menu.NONE, R.id.action_updateRoute, 99,
				R.string.change);
		item.setActionView(R.layout.actionbar_update_route);
		item.getActionView().setOnClickListener(new View.OnClickListener() {
			
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
		item.setIcon(R.drawable.ic_action_accept);
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT|MenuItem.SHOW_AS_ACTION_ALWAYS);
		super.onCreateOptionsMenu(menu, inflater);
	}
}

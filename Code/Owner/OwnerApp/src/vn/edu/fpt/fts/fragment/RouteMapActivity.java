package vn.edu.fpt.fts.fragment;

import java.util.concurrent.ExecutionException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import vn.edu.fpt.fts.activity.MainActivity;
import vn.edu.fpt.fts.common.GeocoderHelper;
import vn.edu.fpt.fts.common.JSONParser;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class RouteMapActivity extends Activity implements OnMapReadyCallback{
	private LatLng start, end, marker1, marker2;
	private GoogleMap map;
	private ProgressDialog pDlg;
	GeocoderHelper helper = new GeocoderHelper();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_route_map);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		
		Bundle bundle = getIntent().getBundleExtra("bundle");
		start = bundle.getParcelable("start");
		end = bundle.getParcelable("end");
		if (bundle.containsKey("m1")) {
			marker1 = bundle.getParcelable("m1");			
		}
		if (bundle.containsKey("m2")) {
			marker2 = bundle.getParcelable("m2");			
		}
		
		
		MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.routeMap);
		mapFragment.getMapAsync(this);
		map = mapFragment.getMap();
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.route_map, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == android.R.id.home) {
			Intent intent = new Intent(RouteMapActivity.this, MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onMapReady(GoogleMap arg0) {
		// TODO Auto-generated method stub
		String params = helper.makeURL2(start, marker1, marker2, end);
		try {
			new connectAsyncTask().execute(params);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		LatLng point = new LatLng(14.058324, 108.277199);
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 6));
		
	}
	
	
	private class connectAsyncTask extends AsyncTask<String, Void, String> {
		long startTime;

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDlg = new ProgressDialog(RouteMapActivity.this);
			pDlg.setMessage("Đang vẽ lộ trình ...");
			pDlg.setProgressDrawable(RouteMapActivity.this.getWallpaper());
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
				if(obj.getString("status").equals("ZERO_RESULTS")) {
					Toast.makeText(RouteMapActivity.this, "Không có lộ trình qua các điểm này", Toast.LENGTH_SHORT).show();
				}
				else {
					helper.drawPath(result, map);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			pDlg.dismiss();
		}
	}
}

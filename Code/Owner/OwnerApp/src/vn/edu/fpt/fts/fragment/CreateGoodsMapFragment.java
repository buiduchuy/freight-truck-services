package vn.edu.fpt.fts.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.fpt.fts.activity.CreateGoodsActivity;
import vn.edu.fpt.fts.activity.MainActivity;
import vn.edu.fpt.fts.common.GeocoderHelper;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateGoodsMapFragment extends Activity {	
	private double mlong, mlat;
	private LatLng point;
	private GoogleMap map;	
	private Button btn_accept;
	private String flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_create_goods_fragment);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		String address = getIntent().getStringExtra("address");
		flag = getIntent().getStringExtra("flag");
		mlat = getIntent().getDoubleExtra("lat", 0.0);
		mlong = getIntent().getDoubleExtra("long", 0.0);
		
		// khoi tao map
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		map = mapFragment.getMap();
		
		// tao marker tren map
		if (mlat == 0 && mlong == 0) {
//			Geocoder geocoder = new Geocoder(getBaseContext());
//			try {			
//				List<Address> list = geocoder.getFromLocationName(
//						address, 1);
//				if (list.size() > 0) {
//					mlong = list.get(0).getLongitude();
//					mlat = list.get(0).getLatitude();	
//				} else {
//					getFragmentManager().beginTransaction().remove(mapFragment).commit();
//				}
//				
//			} catch (IOException ex) {
//				ex.printStackTrace();
//			}
			WebServiceTask3 wst3 = new WebServiceTask3(WebServiceTask3.POST_TASK,
					CreateGoodsMapFragment.this, "Đang xử lý...");
			String url = "http://maps.google.com/maps/api/geocode/json?address="
					+ address.replaceAll(" ", "%20") + "&sensor=false";
			try {
				String test = wst3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url).get();
				test = "abc";
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			MarkerOptions mMarker = new MarkerOptions();
			mMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.owner_marker_icon_small));
			point = new LatLng(mlat, mlong);
			mMarker.position(point);
			mMarker.draggable(true);
			map.addMarker(mMarker);
			//zoom to marker	
			map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 14));
		}
		
		
		map.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
			
			@Override
			public void onMarkerDragStart(Marker arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onMarkerDragEnd(Marker arg0) {
				// TODO Auto-generated method stub
				LatLng pos = arg0.getPosition();
				mlat = pos.latitude;
				mlong = pos.longitude;
			}
			
			@Override
			public void onMarkerDrag(Marker arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		btn_accept = (Button)findViewById(R.id.button_accept);
		btn_accept.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("flag", flag);
				bundle.putDouble("lat", mlat);
				bundle.putDouble("lng", mlong);				
				Intent intent = new Intent(CreateGoodsMapFragment.this, CreateGoodsActivity.class);
				intent.putExtra("info", bundle);
				intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_goods_map, menu);
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
			Intent intent = new Intent(CreateGoodsMapFragment.this, MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	private class WebServiceTask3 extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask2";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask3(int taskType, Context mContext,
				String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		private void showProgressDialog() {

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
			try {
				JSONObject jsonObject = new JSONObject(response);
				GeocoderHelper geocoderHelper = new GeocoderHelper();
				LatLng latLng = geocoderHelper.getLatLong(jsonObject);
				mlat = latLng.latitude;
				mlong = latLng.longitude;
				if (mlat == 0 || mlong == 0) {
					Toast.makeText(CreateGoodsMapFragment.this, "Địa chỉ không có thật", Toast.LENGTH_LONG).show();
				} else {
					MarkerOptions mMarker = new MarkerOptions();
					mMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.owner_marker_icon_small));
					point = new LatLng(mlat, mlong);
					mMarker.position(point);
					mMarker.draggable(true);
					map.addMarker(mMarker);
					//zoom to marker	
					map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 14));
				}
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
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
					httppost.setEntity(new UrlEncodedFormEntity(params));

					response = httpclient.execute(httppost);
					break;
				case GET_TASK:
					HttpGet httpget = new HttpGet(url);
					response = httpclient.execute(httpget);
					break;
				}
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

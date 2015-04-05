package vn.edu.fpt.fts.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import vn.edu.fpt.fts.adapter.SuggestModelAdapter;
import vn.edu.fpt.fts.classes.Route;
import vn.edu.fpt.fts.classes.SuggestModel;
import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.fragment.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class SuggestActivity extends Activity {
	private List<Route> list = new ArrayList<Route>();
	private ListView listView;
	private SuggestModelAdapter adapter;
	private String goodsID, cate, categoryName;
	private TextView tvGone, tvInfo, tvInfo2;
	private Bundle extra = new Bundle();
	private double pickupLat, pickupLng, deliverLat, deliverLng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		tvInfo = (TextView) findViewById(R.id.tvInfo);
		tvInfo2 = (TextView) findViewById(R.id.tvInfo2);
		goodsID = getIntent().getStringExtra("goodsID");
		cate = getIntent().getStringExtra("cate");
		extra = getIntent().getBundleExtra("extra");

		switch (Integer.parseInt(cate)) {
		case 1:
			categoryName = "Hàng thực phẩm";
			break;
		case 2:
			categoryName = "Hàng đông lạnh";
			break;
		case 4:
			categoryName = "Hàng dễ vỡ";
			break;
		case 5:
			categoryName = "Hàng dễ cháy nổ";
			break;
		}

		if (extra != null) {
			LatLng pickup = extra.getParcelable("pickup");
			LatLng deliver = extra.getParcelable("deliver");
			pickupLat = pickup.latitude;
			pickupLng = pickup.longitude;
			deliverLat = deliver.latitude;
			deliverLng = deliver.longitude;
		}
		Bundle bundle = getIntent().getBundleExtra("bundle");
		if (bundle != null) {
			String[] tmp = bundle.getString("pickup").split(",");
			String[] tmp2 = bundle.getString("deliver").split(",");
			String text = categoryName + ": " + bundle.getString("weight")
					+ " - " + bundle.getString("price");
			String text2 = "Nhận: " + tmp[tmp.length - 1] + " - " + "Giao: "
					+ tmp2[tmp2.length - 1];

			tvInfo.setText(text);
			tvInfo2.setText(text2);
		} else if (bundle == null || extra == null) {
			WebServiceTask3 wst3 = new WebServiceTask3(
					WebServiceTask3.POST_TASK, SuggestActivity.this,
					"Đang xử lý...");
			String url = Common.IP_URL + Common.Service_Goods_getGoodsByID;
			wst3.addNameValuePair("goodsID", goodsID);
			wst3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					new String[] { url });
		}

		// price = getIntent().getStringExtra("price");
		// notes = getIntent().getStringExtra("notes");

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				SuggestActivity.this, "Đang xử lý...");
		String url = Common.IP_URL + Common.Service_Suggest_Route;
		wst.addNameValuePair("goodsID", goodsID);
		// wst.execute(new String[] { url });
		wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });

		listView = (ListView) findViewById(R.id.listview_suggest);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				int pos = listView.getPositionForView(view);
				Route route = list.get(pos);
				int routeId = route.getRouteID();
				Intent intent = new Intent(SuggestActivity.this,
						SuggestDetailActivity.class);
				intent.putExtra("route", routeId);
				intent.putExtra("goodsID", goodsID);
				Bundle bundle = new Bundle();
				bundle.putParcelable("pickup", new LatLng(pickupLat, pickupLng));
				bundle.putParcelable("deliver", new LatLng(deliverLat,
						deliverLng));
				intent.putExtra("extra", bundle);
				startActivity(intent);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suggest, menu);
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
		if (id == R.id.action_history) {
			Intent intent = new Intent(SuggestActivity.this,
					HistoryActivity.class);
			startActivity(intent);
		}
		if (id == android.R.id.home) {
			Intent intent = new Intent(SuggestActivity.this, MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(SuggestActivity.this,
					GoodsDetailActivity.class);
			intent.putExtra("goodsID", goodsID);
			intent.putExtra("goodsCategoryID", cate);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 20000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask(int taskType, Context mContext,
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
			if (response.equals("null")) {
				pDlg.dismiss();
				// Toast.makeText(SuggestActivity.this,
				// "Không tìm thấy lộ trình phù hợp", Toast.LENGTH_LONG)
				// .show();
				tvGone = (TextView) findViewById(R.id.textview_gone);
				tvGone.setVisibility(View.VISIBLE);
			} else {
				try {
					// android.os.Debug.waitForDebugger();
					JSONObject jsonObject = new JSONObject(response);
					Object object = jsonObject.get("route");
					if (object instanceof JSONArray) {
						JSONArray array = jsonObject.getJSONArray("route");

						for (int i = 0; i < array.length(); i++) {
							Route route = new Route();
							JSONObject jsonObject2 = array.getJSONObject(i);
							route.setRouteID(Integer.parseInt(jsonObject2
									.getString("routeID")));
							route.setStartingAddress(jsonObject2
									.getString("startingAddress"));
							route.setDestinationAddress(jsonObject2
									.getString("destinationAddress"));
							route.setStartTime(jsonObject2
									.getString("startTime"));
							route.setFinishTime(jsonObject2
									.getString("finishTime"));
							try {
								route.setNotes(jsonObject2.getString("notes"));
							} catch (JSONException e) {
								route.setNotes("");
							}
							route.setWeight(Integer.parseInt(jsonObject2
									.getString("weight")));
							route.setCreateTime(jsonObject2
									.getString("createTime"));
							route.setActive(Integer.parseInt(jsonObject2
									.getString("active")));
							route.setDriverID(Integer.parseInt(jsonObject2
									.getString("driverID")));
							list.add(route);
						}
					} else if (object instanceof JSONObject) {
						Route route = new Route();
						JSONObject jsonObject2 = jsonObject
								.getJSONObject("route");
						route.setRouteID(Integer.parseInt(jsonObject2
								.getString("routeID")));
						route.setStartingAddress(jsonObject2
								.getString("startingAddress"));
						route.setDestinationAddress(jsonObject2
								.getString("destinationAddress"));
						route.setStartTime(jsonObject2.getString("startTime"));
						route.setFinishTime(jsonObject2.getString("finishTime"));
						try {
							route.setNotes(jsonObject2.getString("notes"));
						} catch (JSONException e) {
							route.setNotes("");
						}
						route.setWeight(Integer.parseInt(jsonObject2
								.getString("weight")));
						route.setCreateTime(jsonObject2.getString("createTime"));
						route.setActive(Integer.parseInt(jsonObject2
								.getString("active")));
						route.setDriverID(Integer.parseInt(jsonObject2
								.getString("driverID")));
						list.add(route);
					}

				} catch (JSONException e) {
					Log.e(TAG, e.getLocalizedMessage());
					tvGone = (TextView) findViewById(R.id.textview_gone);
					tvGone.setVisibility(View.VISIBLE);
				}
				ArrayList<SuggestModel> models = new ArrayList<SuggestModel>();

				for (Route route : list) {
					String start = route.getStartingAddress();
					start = Common.formatLocation(start);
					String[] strings = start.split(",");
					String end = route.getDestinationAddress();
					end = Common.formatLocation(end);
					String[] strings2 = end.split(",");
					String tmp[] = route.getStartTime().split(" ");
					String tmp1[] = route.getFinishTime().split(" ");
					models.add(new SuggestModel("Lộ trình: "
							+ strings[strings.length - 1] + " - "
							+ strings2[strings2.length - 1], Common
							.formatDateFromString(tmp[0])
							+ " - "
							+ Common.formatDateFromString(tmp1[0])));
				}
				if (models.size() == 0) {
					tvGone = (TextView) findViewById(R.id.textview_gone);
					tvGone.setVisibility(View.VISIBLE);
				}

				adapter = new SuggestModelAdapter(SuggestActivity.this, models);
				listView.setAdapter(adapter);
				pDlg.dismiss();
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
					httppost.setEntity(new UrlEncodedFormEntity(params,
							HTTP.UTF_8));

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
			try {
				JSONObject jsonObject = new JSONObject(response);
				String price = jsonObject.getString("price").replace(".0", "")
						+ " nghìn đồng";
				String weight = jsonObject.getString("weight") + " kg";
				String pickup = Common.formatLocation(jsonObject
						.getString("pickupAddress"));
				String deliver = Common.formatLocation(jsonObject
						.getString("deliveryAddress"));
				String tmp[] = pickup.split(",");
				String tmp2[] = deliver.split(",");
				String text = categoryName + ": " + weight + " - " + price;
				String text2 = "Nhận: " + tmp[tmp.length - 1] + " - "
						+ "Giao: " + tmp2[tmp2.length - 1];
				String test = jsonObject.getString("pickupMarkerLatidute");
				String test2 = jsonObject.getString("pickupMarkerLongtitude");
				pickupLat = Double.parseDouble(jsonObject
						.getString("pickupMarkerLatidute"));
				pickupLng = Double.parseDouble(jsonObject
						.getString("pickupMarkerLongtitude"));
				deliverLat = Double.parseDouble(jsonObject
						.getString("deliveryMarkerLatidute"));
				deliverLng = Double.parseDouble(jsonObject
						.getString("deliveryMarkerLongtitude"));
				LatLng c = new LatLng(pickupLat, pickupLng);

				tvInfo.setText(text);
				tvInfo2.setText(text2);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.getLocalizedMessage());
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

package vn.edu.fpt.fts.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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

import com.google.android.gms.maps.model.LatLng;

import vn.edu.fpt.fts.classes.Route;
import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.common.GeocoderHelper;
import vn.edu.fpt.fts.fragment.R;
import vn.edu.fpt.fts.fragment.RouteMapActivity;
import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SuggestDetailActivity extends Activity {
	private int routeid;
	private TextView startAddr, destAddr, startTime, finishTime, category,
			weight, tvMap;
	private EditText etPrice, etNote;
	private Button btnSend;
	private String goodsID, ownerID, categoryID;
	private LatLng startAdd, mark1, mark2, endAdd, pickup, deliver;
	private Bundle extra = new Bundle();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_suggest_detail);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		routeid = getIntent().getIntExtra("route", 0);
		goodsID = getIntent().getStringExtra("goodsID");		
		extra = getIntent().getBundleExtra("extra");
		
		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				SuggestDetailActivity.this, "Đang xử lý...");
		String url = Common.IP_URL + Common.Service_Route_GetByID;
		wst.addNameValuePair("routeID", routeid + "");
		// wst.execute(new String[] { url });
		wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });

		SharedPreferences preferences = getSharedPreferences("MyPrefs",
				Context.MODE_PRIVATE);
		ownerID = preferences.getString("ownerID", "");

		startAddr = (TextView) this.findViewById(R.id.textview_startAddr);
		destAddr = (TextView) this.findViewById(R.id.textview_destAddr);
		startTime = (TextView) this.findViewById(R.id.textview_startTime);
		finishTime = (TextView) this.findViewById(R.id.textview_finishTime);
		category = (TextView) this.findViewById(R.id.textview_category);
		weight = (TextView) this.findViewById(R.id.textview_weight);
		etPrice = (EditText) findViewById(R.id.edittext_price);
		etNote = (EditText) findViewById(R.id.edittext_note);
		btnSend = (Button) findViewById(R.id.button_send);

		// etPrice.setText(price);
		// etNote.setText(notes);
		WebServiceTask3 wst3 = new WebServiceTask3(WebServiceTask3.POST_TASK,
				this, "Đang xử lý");
		String urlString = Common.IP_URL + Common.Service_Goods_getGoodsByID;
		wst3.addNameValuePair("goodsID", goodsID);
		wst3.execute(new String[] { urlString });

		btnSend.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance();
				WebServiceTask2 wst2 = new WebServiceTask2(
						WebServiceTask2.POST_TASK, SuggestDetailActivity.this,
						"Đang xử lý...");
				wst2.addNameValuePair("dealID", "0");
				wst2.addNameValuePair("price", etPrice.getText().toString());
				wst2.addNameValuePair("notes", etNote.getText().toString());
				wst2.addNameValuePair("createTime", Common.formatDate(calendar));
				wst2.addNameValuePair("createBy", "owner");
				wst2.addNameValuePair("routeID", routeid + "");
				wst2.addNameValuePair("goodsID", goodsID + "");
				wst2.addNameValuePair("refDealID", "0");
				wst2.addNameValuePair("active", "1");
				String url = Common.IP_URL + Common.Service_Deal_Create;
				// wst2.execute(new String[] { url });
				wst2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
						new String[] { url });
			}
		});

		tvMap = (TextView) findViewById(R.id.tvMap);
		tvMap.setPaintFlags(tvMap.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
		tvMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(SuggestDetailActivity.this,
						RouteMapActivity.class);
				Bundle bundle = new Bundle();
				bundle.putParcelable("start", startAdd);
				if (mark1 != null) {
					bundle.putParcelable("m1", mark1);
				}
				if (mark2 != null) {
					bundle.putParcelable("m2", mark2);
				}
				bundle.putParcelable("end", endAdd);
				intent.putExtra("bundle", bundle);
				intent.putExtra("extra", extra);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.suggest_detail, menu);
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
			Intent intent = new Intent(SuggestDetailActivity.this,
					HistoryActivity.class);
			startActivity(intent);
		}
		if (id == android.R.id.home) {
			Intent intent = new Intent(SuggestDetailActivity.this,
					MainActivity.class);
			startActivity(intent);
		}
		if (id == R.id.send_deal) {
			Calendar calendar = Calendar.getInstance();
			WebServiceTask2 wst2 = new WebServiceTask2(
					WebServiceTask2.POST_TASK, SuggestDetailActivity.this,
					"Đang xử lý...");
			wst2.addNameValuePair("dealID", "0");
			wst2.addNameValuePair("price", etPrice.getText().toString());
			wst2.addNameValuePair("notes", etNote.getText().toString());
			wst2.addNameValuePair("createTime", Common.formatDate(calendar));
			wst2.addNameValuePair("createBy", "owner");
			wst2.addNameValuePair("routeID", routeid + "");
			wst2.addNameValuePair("goodsID", goodsID + "");
			wst2.addNameValuePair("refDealID", "0");
			wst2.addNameValuePair("active", "1");
			String url = Common.IP_URL + Common.Service_Deal_Create;
			// wst2.execute(new String[] { url });
			wst2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					new String[] { url });
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(SuggestDetailActivity.this,
					SuggestActivity.class);
			intent.putExtra("goodsID", goodsID);
			intent.putExtra("cate", categoryID);
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
		private static final int SOCKET_TIMEOUT = 5000;

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

			if (response.getEntity() == null) {
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
			if (response != null) {
				Route route = new Route();
				try {
					// android.os.Debug.waitForDebugger();
					JSONObject jsonObject = new JSONObject(response);
					String startTime = jsonObject.getString("startTime");
					String start[] = startTime.split(" ");
					String finishTime = jsonObject.getString("finishTime");
					String finish[] = finishTime.split(" ");
					Date sTime = new Date();
					Date fTime = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					try {
						sTime = sdf.parse(start[0]);
						fTime = sdf.parse(finish[0]);
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					sdf = new SimpleDateFormat("dd/MM/yyyy");
					startTime = sdf.format(sTime);
					finishTime = sdf.format(fTime);
					// JSONObject jsonObject2 =
					// jsonObject.getJSONObject("goodsCategory");
					String category = "";
					if (response.contains("goodsCategory")) {
						Object object = jsonObject.get("goodsCategory");

						if (object instanceof JSONArray) {
							JSONArray array = jsonObject
									.getJSONArray("goodsCategory");

							for (int i = 0; i < array.length(); i++) {
								JSONObject jsonObject2 = array.getJSONObject(i);
								category = category
										+ jsonObject2.getString("name") + ", ";
							}
						} else if (object instanceof JSONObject) {
							JSONObject jsonObject2 = jsonObject
									.getJSONObject("goodsCategory");
							category = category + jsonObject2.getString("name")
									+ ", ";
						}
					} else {
						category = "Không có";
					}
					if (response.contains("routeMarkers")) {
						Object object = jsonObject.get("routeMarkers");
						if (object instanceof JSONArray) {
							JSONArray array = jsonObject
									.getJSONArray("routeMarkers");
							for (int i = 0; i < array.length(); i++) {
								JSONObject jsonObject2 = array.getJSONObject(i);
								if (jsonObject2.getString("numbering").equals(
										"1")) {
									String marker1 = jsonObject2
											.getString("routeMarkerLocation");
									try {
										mark1 = new GetLatLng()
												.execute(marker1).get();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ExecutionException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								} else if (jsonObject2.getString("numbering")
										.equals("2")) {
									String marker2 = jsonObject2
											.getString("routeMarkerLocation");
									try {
										mark2 = new GetLatLng()
												.execute(marker2).get();
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (ExecutionException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
						} else if (object instanceof JSONObject) {
							JSONObject jsonObject2 = jsonObject
									.getJSONObject("routeMarkers");
							if (jsonObject2.getString("numbering").equals("1")) {
								String marker1 = jsonObject2
										.getString("routeMarkerLocation");
								try {
									mark1 = new GetLatLng().execute(marker1)
											.get();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} else if (jsonObject2.getString("numbering")
									.equals("2")) {
								String marker2 = jsonObject2
										.getString("routeMarkerLocation");
								try {
									mark2 = new GetLatLng().execute(marker2)
											.get();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (ExecutionException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}

					}
					String rWeight = jsonObject.getString("weight");
					String startAddress = jsonObject
							.getString("startingAddress");
					try {
						startAdd = new GetLatLng().execute(startAddress).get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					route.setStartingAddress(startAddress);
					String endAddress = jsonObject
							.getString("destinationAddress");
					try {
						endAdd = new GetLatLng().execute(endAddress).get();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					route.setDestinationAddress(endAddress);
					route.setStartTime(startTime);
					route.setFinishTime(finishTime);
					route.setCategory(category);
					route.setWeight(Integer.parseInt(rWeight));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// String a = route.getStartingAddress();
				startAddr.setText(route.getStartingAddress());
				destAddr.setText(route.getDestinationAddress());
				startTime.setText(route.getStartTime());
				finishTime.setText(route.getFinishTime());
				category.setText(route.getCategory());
				weight.setText(route.getWeight() + " kg");
			} else {
				Toast.makeText(SuggestDetailActivity.this,
						"Lộ trình không còn tồn tại", Toast.LENGTH_LONG).show();
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

	private class WebServiceTask2 extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask2(int taskType, Context mContext,
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
			if (Integer.parseInt(response) > 0) {
				Toast.makeText(SuggestDetailActivity.this,
						"Gửi đề nghị thành công", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(SuggestDetailActivity.this,
						SuggestActivity.class);
				intent.putExtra("goodsID", goodsID);
				intent.putExtra("cate", categoryID);
				startActivity(intent);
			} else {
				Toast.makeText(SuggestDetailActivity.this,
						"Gửi đề nghị thất bại", Toast.LENGTH_LONG).show();
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
				categoryID = jsonObject.getString("goodsCategoryID");
				String price = jsonObject.getString("price");
				String notes = jsonObject.getString("notes");
				etPrice.setText(price.replace(".0", ""));
				etNote.setText(notes);
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

	private class GetLatLng extends AsyncTask<String, Void, LatLng> {
		private final AndroidHttpClient androidHttpClient = AndroidHttpClient
				.newInstance(GetLatLng.class.getName());

		@Override
		protected LatLng doInBackground(String... address) {
			// TODO Auto-generated method stub
			String url = "http://maps.google.com/maps/api/geocode/json?address="
					+ address[0].replace(" ", "%20") + "&sensor=false";
			try {
				JSONObject googleMapResponse = new JSONObject(
						androidHttpClient.execute(
								new HttpGet(url.replace(" ", "%20")),
								new BasicResponseHandler()));
				GeocoderHelper geocoderHelper = new GeocoderHelper();
				LatLng result = geocoderHelper.getLatLong(googleMapResponse);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(LatLng result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			androidHttpClient.close();
		};
	}
}

package vn.edu.fpt.fts.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

import vn.edu.fpt.fts.classes.Route;
import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.fragment.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.NotificationManager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DealDetailActivity extends Activity {
	private TextView startAddr, destAddr, startTime, finishTime, category,
			goodscate, goodsweight, goodspickup, goodsdeliver, tvPrice, tvNote,
			tvStatus, tvNote1, tvStatus1, tvWeight, tvText;
	private int dealID;
	private String refDealID, dealStatus, routeID, goodsID, goodsCategory;
	private double price;
	private String note, createBy;
	private Button btn_counter;
	private EditText etPrice, etNote;
	private View viewLine;
	private MenuItem accept, decline, cancel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deal_detail);

		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		dealID = getIntent().getIntExtra("dealID", 0);

		startAddr = (TextView) this.findViewById(R.id.textview_startAddr);
		destAddr = (TextView) this.findViewById(R.id.textview_destAddr);
		startTime = (TextView) this.findViewById(R.id.textview_startTime);
		finishTime = (TextView) this.findViewById(R.id.textview_finishTime);
		category = (TextView) this.findViewById(R.id.textview_category);
		tvPrice = (TextView) findViewById(R.id.textview_price);
		tvNote = (TextView) findViewById(R.id.textview_note);
		tvStatus = (TextView) findViewById(R.id.textview_status);
		tvNote1 = (TextView) findViewById(R.id.textview_note1);
		tvStatus1 = (TextView) findViewById(R.id.textview_status1);
		tvWeight = (TextView) findViewById(R.id.textview_weight);
		etPrice = (EditText) findViewById(R.id.edittext_price);
		etNote = (EditText) findViewById(R.id.edittext_note);
		btn_counter = (Button) findViewById(R.id.button_counter);

		viewLine = (View) findViewById(R.id.view_line);
		tvText = (TextView) findViewById(R.id.tvText);
		// goodscate = (TextView) findViewById(R.id.textview_categorygoods);
		// goodsweight = (TextView) findViewById(R.id.textview_weightgoods);
		// goodspickup = (TextView) findViewById(R.id.textview_pickupgoods);
		// goodsdeliver = (TextView) findViewById(R.id.textview_delivergoods);

		// refDealID = getIntent().getIntExtra("refDealID", 0);
		// dealStatus = getIntent().getIntExtra("dealStatus", 0);
		// routeID = getIntent().getIntExtra("routeID", 0);
		// goodsID = getIntent().getIntExtra("goodsID", 0);
		// price = getIntent().getDoubleExtra("price", 0.0);
		// note = getIntent().getStringExtra("note");
		// createBy = getIntent().getStringExtra("createBy");
		WebServiceTask6 wst6 = new WebServiceTask6(WebServiceTask6.POST_TASK,
				this, "Đang xử lý...");
		wst6.addNameValuePair("dealID", dealID + "");
		String url = Common.IP_URL + Common.Service_Deal_getDealByID;
		// wst6.execute(new String[] { url });
		wst6.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });

		btn_counter.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance();
				WebServiceTask2 wst2 = new WebServiceTask2(
						WebServiceTask2.POST_TASK, DealDetailActivity.this,
						"Đang xử lý...");
				wst2.addNameValuePair("dealID", dealID + "");
				wst2.addNameValuePair("price", etPrice.getText().toString());
				wst2.addNameValuePair("notes", etNote.getText().toString());
				wst2.addNameValuePair("createTime", formatDate(calendar));
				wst2.addNameValuePair("createBy", "owner");
				wst2.addNameValuePair("routeID", routeID + "");
				wst2.addNameValuePair("goodsID", goodsID + "");
				if (refDealID.equals("0")) {
					wst2.addNameValuePair("refDealID", dealID + "");
				} else {
					wst2.addNameValuePair("refDealID", refDealID + "");
				}
				wst2.addNameValuePair("active", "1");
				String url = Common.IP_URL + Common.Service_Deal_Create;
				// wst2.execute(new String[] { url });
				wst2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
						new String[] { url });
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.deal_detail, menu);
		return true;
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);
		accept = menu.findItem(R.id.accept_deal);
		decline = menu.findItem(R.id.decline_deal);
		cancel = menu.findItem(R.id.cancel_deal);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		// if (id == R.id.action_settings) {
		// return true;
		// }
		// if (id == R.id.action_history) {
		// Intent intent = new Intent(DealDetailActivity.this,
		// HistoryActivity.class);
		// startActivity(intent);
		// }
		if (id == android.R.id.home) {
			Intent intent = new Intent(DealDetailActivity.this,
					MainActivity.class);
			startActivity(intent);
			return true;
		}
		if (id == R.id.accept_deal) {
			Calendar calendar = Calendar.getInstance();
			WebServiceTask3 wst3 = new WebServiceTask3(
					WebServiceTask3.POST_TASK, DealDetailActivity.this,
					"Đang xử lý...");
			wst3.addNameValuePair("dealID", dealID + "");
			wst3.addNameValuePair("price", price + "");
			wst3.addNameValuePair("notes", note + "");
			wst3.addNameValuePair("createTime", formatDate(calendar));
			wst3.addNameValuePair("createBy", "owner");
			wst3.addNameValuePair("routeID", routeID + "");
			wst3.addNameValuePair("goodsID", goodsID + "");
			if (refDealID.equals("0")) {
				wst3.addNameValuePair("refDealID", dealID + "");
			} else {
				wst3.addNameValuePair("refDealID", refDealID + "");
			}
			wst3.addNameValuePair("active", "1");
			String url = Common.IP_URL + Common.Service_Deal_Accept;
			// wst3.execute(new String[] { url });
			wst3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					new String[] { url });
		}
		if (id == R.id.decline_deal) {
			Calendar calendar = Calendar.getInstance();
			WebServiceTask4 wst4 = new WebServiceTask4(
					WebServiceTask4.POST_TASK, DealDetailActivity.this,
					"Đang xử lý...");
			wst4.addNameValuePair("dealID", dealID + "");
			wst4.addNameValuePair("price", price + "");
			wst4.addNameValuePair("notes", note + "");
			wst4.addNameValuePair("createTime", formatDate(calendar));
			wst4.addNameValuePair("createBy", "owner");
			wst4.addNameValuePair("routeID", routeID + "");
			wst4.addNameValuePair("goodsID", goodsID + "");
			if (refDealID.equals("0")) {
				wst4.addNameValuePair("refDealID", dealID + "");
			} else {
				wst4.addNameValuePair("refDealID", refDealID + "");
			}
			wst4.addNameValuePair("active", "1");
			String url = Common.IP_URL + Common.Service_Deal_Decline;
			// wst4.execute(new String[] { url });
			wst4.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					new String[] { url });
		}
		if (id == R.id.cancel_deal) {
			Calendar calendar = Calendar.getInstance();
			WebServiceTask5 wst5 = new WebServiceTask5(
					WebServiceTask5.POST_TASK, DealDetailActivity.this,
					"Đang xử lý...");
			wst5.addNameValuePair("dealID", dealID + "");
			wst5.addNameValuePair("price", price + "");
			wst5.addNameValuePair("notes", note + "");
			wst5.addNameValuePair("createTime", formatDate(calendar));
			wst5.addNameValuePair("createBy", "owner");
			wst5.addNameValuePair("routeID", routeID + "");
			wst5.addNameValuePair("goodsID", goodsID + "");
			if (refDealID.equals("0")) {
				wst5.addNameValuePair("refDealID", dealID + "");
			} else {
				wst5.addNameValuePair("refDealID", refDealID + "");
			}
			wst5.addNameValuePair("active", "1");
			String url = Common.IP_URL + Common.Service_Deal_Cancel;
			// wst5.execute(new String[] { url });
			wst5.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
					new String[] { url });
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (dealStatus.equals("1")) {
				Intent intent = new Intent(DealDetailActivity.this,
						GoodsDetailActivity.class);
				intent.putExtra("goodsID", goodsID);
				intent.putExtra("goodsCategoryID", goodsCategory);
				startActivity(intent);
			} else {
				Intent intent = new Intent(DealDetailActivity.this,
						HistoryActivity.class);
				startActivity(intent);
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	private String formatDate(Calendar calendar) {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		return sdf.format(calendar.getTime());
	}

	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 10000;

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
					route.setStartingAddress(jsonObject
							.getString("startingAddress"));
					route.setDestinationAddress(jsonObject
							.getString("destinationAddress"));
					route.setStartTime(startTime);
					route.setFinishTime(finishTime);
					route.setCategory(category);
					route.setWeight(Integer.parseInt(jsonObject
							.getString("weight")));
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
				tvPrice.setText((int) price + " nghìn đồng");
				tvWeight.setText(route.getWeight() + " kg");
				tvNote.setText(note);
			} else {
				Toast.makeText(DealDetailActivity.this,
						"Lộ trình không tồn tại", Toast.LENGTH_LONG).show();
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
		private static final int SOCKET_TIMEOUT = 10000;

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
			if (Integer.parseInt(response) > 0) {
				Toast.makeText(DealDetailActivity.this,
						"Gửi đề nghị thành công", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(DealDetailActivity.this,
						MainActivity.class);
				startActivity(intent);

			} else {
				Toast.makeText(DealDetailActivity.this, "Gửi đề nghị thất bại",
						Toast.LENGTH_LONG).show();
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

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 10000;

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
			if (response.equals("1")) {
				Toast.makeText(DealDetailActivity.this,
						"Đề nghị đã được chấp nhận", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(DealDetailActivity.this,
						MainActivity.class);
				startActivity(intent);

			} else {
				Toast.makeText(DealDetailActivity.this,
						"Đề nghị chưa được chấp nhận", Toast.LENGTH_LONG)
						.show();
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

	private class WebServiceTask4 extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 10000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask4(int taskType, Context mContext,
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
			if (response.equals("1")) {
				Toast.makeText(DealDetailActivity.this,
						"Đề nghị đã được từ chối", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(DealDetailActivity.this,
						MainActivity.class);
				startActivity(intent);

			} else {
				Toast.makeText(DealDetailActivity.this,
						"Đề nghị chưa được từ chối", Toast.LENGTH_LONG).show();
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

	private class WebServiceTask5 extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 10000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask5(int taskType, Context mContext,
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
			if (response.equals("1")) {
				Toast.makeText(DealDetailActivity.this, "Đề nghị đã được hủy",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(DealDetailActivity.this,
						MainActivity.class);
				startActivity(intent);

			} else {
				Toast.makeText(DealDetailActivity.this,
						"Đề nghị chưa được hủy", Toast.LENGTH_LONG).show();
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

	private class WebServiceTask6 extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask2";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 10000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebServiceTask6(int taskType, Context mContext,
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
			if (response != null) {
				try {
					JSONObject jsonObject = new JSONObject(response);
					refDealID = jsonObject.getString("refDealID");
					dealStatus = jsonObject.getString("dealStatusID");
					routeID = jsonObject.getString("routeID");
					goodsID = jsonObject.getString("goodsID");
					price = Double.parseDouble(jsonObject.getString("price"));
					note = jsonObject.getString("notes");
					createBy = jsonObject.getString("createBy");
					JSONObject jsonObject2 = (jsonObject.getJSONObject("goods"))
							.getJSONObject("goodsCategory");
					goodsCategory = jsonObject2.getString("goodsCategoryId");
					if (dealStatus.equals("1") && createBy.equals("owner")) {
						btn_counter.setVisibility(View.GONE);
						etPrice.setVisibility(View.GONE);
						etNote.setVisibility(View.GONE);
						viewLine.setVisibility(View.GONE);
						tvText.setVisibility(View.GONE);
						accept.setVisible(false);
						decline.setVisible(false);
						cancel.setVisible(true);

					} else if(dealStatus.equals("1") && createBy.equals("driver"))
					{
						btn_counter.setVisibility(View.VISIBLE);
						etPrice.setVisibility(View.VISIBLE);
						etNote.setVisibility(View.VISIBLE);
						viewLine.setVisibility(View.VISIBLE);
						tvText.setVisibility(View.VISIBLE);
					}
					else if (dealStatus.equals("2")) {
						btn_counter.setVisibility(View.GONE);
						etPrice.setVisibility(View.GONE);
						etNote.setVisibility(View.GONE);
						viewLine.setVisibility(View.GONE);
						tvText.setVisibility(View.GONE);
						tvNote.setVisibility(View.GONE);
						tvStatus.setVisibility(View.VISIBLE);
						tvNote1.setVisibility(View.GONE);
						tvStatus1.setVisibility(View.VISIBLE);
						tvStatus.setText("Đã chấp nhận");
						accept.setVisible(false);
						decline.setVisible(false);
						cancel.setVisible(false);
					} else if (dealStatus.equals("4")) {
						btn_counter.setVisibility(View.GONE);
						etPrice.setVisibility(View.GONE);
						etNote.setVisibility(View.GONE);
						viewLine.setVisibility(View.GONE);
						tvText.setVisibility(View.GONE);
						tvNote.setVisibility(View.GONE);
						tvStatus.setVisibility(View.VISIBLE);
						tvNote1.setVisibility(View.GONE);
						tvStatus1.setVisibility(View.VISIBLE);
						tvStatus.setText("Đã bị hủy");
						accept.setVisible(false);
						decline.setVisible(false);
						cancel.setVisible(false);
					} else if (dealStatus.equals("3")
							&& createBy.equals("owner")) {
						btn_counter.setVisibility(View.GONE);
						etPrice.setVisibility(View.GONE);
						etNote.setVisibility(View.GONE);
						viewLine.setVisibility(View.GONE);
						tvText.setVisibility(View.GONE);
						tvNote.setVisibility(View.GONE);
						tvStatus.setVisibility(View.VISIBLE);
						tvNote1.setVisibility(View.GONE);
						tvStatus1.setVisibility(View.VISIBLE);
						tvStatus.setText("Đã từ chối");
						accept.setVisible(false);
						decline.setVisible(false);
						cancel.setVisible(false);
					} else if (dealStatus.equals("3")
							&& createBy.equals("driver")) {
						btn_counter.setVisibility(View.GONE);
						etPrice.setVisibility(View.GONE);
						etNote.setVisibility(View.GONE);
						viewLine.setVisibility(View.GONE);
						tvText.setVisibility(View.GONE);
						tvNote.setVisibility(View.GONE);
						tvStatus.setVisibility(View.VISIBLE);
						tvNote1.setVisibility(View.GONE);
						tvStatus1.setVisibility(View.VISIBLE);
						tvStatus.setText("Đã bị từ chối");
						accept.setVisible(false);
						decline.setVisible(false);
						cancel.setVisible(false);
					}
					WebServiceTask wst = new WebServiceTask(
							WebServiceTask.POST_TASK, DealDetailActivity.this,
							"Đang xử lý...");
					// String url = Common.IP_URL +
					// Common.Service_Deal_getDealByID;
					// wst.addNameValuePair("dealID", dealID + "");
					String urlGetRoute = Common.IP_URL
							+ Common.Service_Route_GetByID;
					wst.addNameValuePair("routeID", routeID + "");
					// wst.execute(new String[] { urlGetRoute });
					wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
							new String[] { urlGetRoute });
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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

package vn.edu.fpt.fts.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;

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
import org.json.JSONException;
import org.json.JSONObject;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetailActivity extends Activity {
	private TextView tvStartAdd, tvDestAdd, tvStartTime, tvFinishTime, tvCate,
			tvPrice, tvNote, tvPhone, tvStatus;
	private Button btnConfirm;
	private String orderID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		orderID = getIntent().getStringExtra("orderID");
		tvStartAdd = (TextView) findViewById(R.id.textview_startAddr);
		tvDestAdd = (TextView) findViewById(R.id.textview_destAddr);
		tvStartTime = (TextView) findViewById(R.id.textview_startTime);
		tvFinishTime = (TextView) findViewById(R.id.textview_finishTime);
		tvCate = (TextView) findViewById(R.id.textview_category);
		tvPrice = (TextView) findViewById(R.id.textview_price);
		tvNote = (TextView) findViewById(R.id.textview_note);
		tvPhone = (TextView) findViewById(R.id.textview_phone);
		tvStatus = (TextView) findViewById(R.id.textview_status);
		btnConfirm = (Button) findViewById(R.id.button_confirm);

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				OrderDetailActivity.this, "Đang xử lý...");
		wst.addNameValuePair("orderID", orderID);
		String url = Common.IP_URL + Common.Service_Order_getOrderByID;
//		wst.execute(new String[] { url });
		wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[] {url});

		btnConfirm.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WebServiceTask2 wst2 = new WebServiceTask2(
						WebServiceTask2.POST_TASK, OrderDetailActivity.this,
						"Đang xử lý...");
				wst2.addNameValuePair("orderID", orderID);
				//wst2.addNameValuePair("ownerConfirmDelivery", "true");
				String url = Common.IP_URL
						+ Common.Service_Order_ConfirmDelivery;
//				wst2.execute(new String[] { url });
				wst2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[] {url});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.order_detail, menu);
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
		if  (id == R.id.action_history) {
			Intent intent = new Intent(OrderDetailActivity.this, HistoryActivity.class);
			startActivity(intent);
		}
		if (id == android.R.id.home) {
			Intent intent = new Intent(OrderDetailActivity.this, MainActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);
	}

	public String formatNumber(int number) {
		DecimalFormat formatter = new DecimalFormat();
		DecimalFormatSymbols symbol = new DecimalFormatSymbols();
		symbol.setGroupingSeparator('.');
		formatter.setDecimalFormatSymbols(symbol);
		return formatter.format(number);
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
			try {
				JSONObject jsonObject = new JSONObject(response);
				JSONObject jsonObject2 = jsonObject.getJSONObject("deal");
				JSONObject jsonObject3 = jsonObject2.getJSONObject("goods");
				JSONObject jsonObject4 = jsonObject3
						.getJSONObject("goodsCategory");
				JSONObject jsonObject5 = jsonObject2.getJSONObject("route");
				JSONObject jsonObject6 = jsonObject5.getJSONObject("driver");
				tvStartAdd.setText("Địa chỉ nhận hàng: "
						+ jsonObject3.getString("pickupAddress"));
				tvDestAdd.setText("Địa chỉ giao hàng: "
						+ jsonObject3.getString("deliveryAddress"));
				String startTime = jsonObject3.getString("pickupTime");
				String endTime = jsonObject3.getString("deliveryTime");
				String[] tmp = startTime.split(" ");
				String[] tmp1 = endTime.split(" ");
				tvStartTime.setText("Ngày nhận hàng: " + tmp[0]);
				tvFinishTime.setText("Ngày giao hàng: " + tmp1[0]);
				String test = jsonObject4.getString("name");
				tvCate.setText("Loại hàng: " + jsonObject4.getString("name"));
				int price = (int) Double.parseDouble(jsonObject2
						.getString("price"));
				tvPrice.setText("Giá hàng: " + formatNumber(price)
						+ ".000 đồng");
				tvNote.setText("Ghi chú: " + jsonObject2.getString("notes"));
				tvPhone.setText("Số điện thoại tài xế: "
						+ jsonObject6.getString("phone"));
				String count = jsonObject.getString("orderStatusID");
				if (count.equals("3") || count.equals("4")) {
					tvStatus.setText("Trạng thái hàng: " + "Đã nhận hàng");
					btnConfirm.setVisibility(View.GONE);
				} else {
					tvStatus.setText("Trạng thái hàng: " + "Hàng chưa giao");
				}
			} catch (JSONException e) {
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
			if (response.equals("1")) {				
				finish();
				startActivity(getIntent());				
			} else {
				Toast.makeText(OrderDetailActivity.this,
						"Không thể xác nhận giao hàng", Toast.LENGTH_LONG)
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
}

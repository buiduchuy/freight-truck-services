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
import vn.edu.fpt.fts.fragment.PaypalActivity;
import vn.edu.fpt.fts.fragment.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OrderDetailActivity extends Activity {
	private TextView tvStartAdd, tvDestAdd, tvStartTime, tvFinishTime, tvCate,
			tvPrice, tvNote, tvPhone, tvStatus, tvWeight, tvOrderid;
	private String orderID;
	private MenuItem lost, pay, cancel;
	private int price;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_detail);
		SharedPreferences preferences = getSharedPreferences("MyPrefs",
				Context.MODE_PRIVATE);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		actionBar.setTitle(this.getString(R.string.title_activity_order_detail)
				+ " - " + preferences.getString("ownerName", ""));

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
		tvWeight = (TextView) findViewById(R.id.textview_weight);
		tvOrderid = (TextView) findViewById(R.id.tvOrderid);

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				OrderDetailActivity.this, "Đang xử lý...");
		wst.addNameValuePair("orderID", orderID);
		String url = Common.IP_URL + Common.Service_Order_getOrderByID;
		// wst.execute(new String[] { url });
		wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });

	}

	public void makePayment() {
		Intent intent = new Intent(this, PaypalActivity.class);
		intent.putExtra("orderID", orderID);
		intent.putExtra("price", price);
		startActivity(intent);
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
		// if (id == R.id.action_settings) {
		// return true;
		// }
		// if (id == R.id.action_history) {
		// Intent intent = new Intent(OrderDetailActivity.this,
		// HistoryActivity.class);
		// startActivity(intent);
		// }
		if (id == android.R.id.home) {
			Intent intent = new Intent(OrderDetailActivity.this,
					MainActivity.class);
			startActivity(intent);
		}
		if (id == R.id.action_call) {
			Uri uri = Uri.parse("tel:" + tvPhone.getText().toString());
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
		if (id == R.id.action_pay) {
			makePayment();
		}
		if (id == R.id.cancel_order) {
			DialogInterface.OnClickListener cancelListener = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						WebServiceTask2 wst2 = new WebServiceTask2(
								WebServiceTask2.POST_TASK,
								OrderDetailActivity.this, "Đang xử lý...");
						wst2.addNameValuePair("orderID", orderID);
						String url = Common.IP_URL
								+ Common.Service_Order_ownerCancelOrder;
						wst2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
								new String[] { url });
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						break;
					}
				}
			};
			AlertDialog.Builder builder = new AlertDialog.Builder(
					OrderDetailActivity.this);
			builder.setMessage("Bạn có muốn hủy đơn hàng không?")
					.setPositiveButton("Đồng ý", cancelListener)
					.setNegativeButton("Hủy", cancelListener).show();
		}
		if (id == R.id.lost_order) {
			DialogInterface.OnClickListener lostListener = new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					switch (which) {
					case DialogInterface.BUTTON_POSITIVE:
						WebServiceTask3 wst3 = new WebServiceTask3(
								WebServiceTask3.POST_TASK,
								OrderDetailActivity.this, "Đang xử lý...");
						wst3.addNameValuePair("orderID", orderID);
						// wst2.addNameValuePair("ownerConfirmDelivery",
						// "true");
						String url = Common.IP_URL
								+ Common.Service_Order_ownerNoticeLostGoods;
						// wst2.execute(new String[] { url });
						wst3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
								new String[] { url });
						break;
					case DialogInterface.BUTTON_NEGATIVE:
						break;
					}
				}
			};
			AlertDialog.Builder builder = new AlertDialog.Builder(
					OrderDetailActivity.this);
			builder.setMessage("Bạn có muốn phản hồi đơn hàng không?")
					.setPositiveButton("Đồng ý", lostListener)
					.setNegativeButton("Hủy", lostListener).show();

		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onPrepareOptionsMenu(menu);
		lost = menu.findItem(R.id.lost_order);
		pay = menu.findItem(R.id.action_pay);
		cancel = menu.findItem(R.id.cancel_order);
		return true;
	};

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		super.onKeyDown(keyCode, event);
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(OrderDetailActivity.this,
					MainActivity.class);
			startActivity(intent);
		}
		return true;
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
				tvStartAdd.setText(jsonObject3.getString("pickupAddress"));
				tvDestAdd.setText(jsonObject3.getString("deliveryAddress"));
				String startTime = jsonObject3.getString("pickupTime");
				String endTime = jsonObject3.getString("deliveryTime");
				String[] tmp = startTime.split(" ");
				String[] tmp1 = endTime.split(" ");
				tvStartTime.setText(Common.formatDateFromString(tmp[0]));
				tvFinishTime.setText(Common.formatDateFromString(tmp1[0]));
				String test = jsonObject4.getString("name");
				tvCate.setText(jsonObject4.getString("name"));
				price = (int) Double
						.parseDouble(jsonObject2.getString("price"));
				tvPrice.setText(price + " nghìn đồng");
				tvWeight.setText(jsonObject3.getString("weight") + " kg");
				String sNote = "";
				try {
					sNote = jsonObject2.getString("notes");
				} catch (JSONException e) {
					sNote = "Không có";
				}
				tvNote.setText(sNote);
				tvPhone.setText(jsonObject6.getString("phone"));
				tvOrderid.setText("#OD" + orderID);
				int count = Integer.parseInt(jsonObject
						.getString("orderStatusID"));
				switch (count) {
				case 1:
					tvStatus.setText("Chưa trả tiền");
					pay.setVisible(true);
					break;
				case 2:
					tvStatus.setText("Đã trả tiền");
					break;
				case 3:
					tvStatus.setText("Đang chở hàng");
					break;
				case 4:
					tvStatus.setText("Đã giao hàng");
					lost.setVisible(true);
					break;
				case 5:
					tvStatus.setText("Đã bị hủy");
					break;
				case 6:
					tvStatus.setText("Đã hoàn tiền");
					break;
				case 7:
					tvStatus.setText("Đã giao hàng");
					break;
				}

				if (!Common.expireDate(tmp[0]) && (count == 1 || count == 2)) {
					cancel.setVisible(true);
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
				Toast.makeText(OrderDetailActivity.this,
						"Hủy đơn hàng thành công", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(OrderDetailActivity.this,
						MainActivity.class);
				startActivity(intent);
				finish();
				startActivity(getIntent());
			} else {
				Toast.makeText(OrderDetailActivity.this,
						"Hủy đơn hàng xảy ra lỗi. Xin vui lòng thử lại",
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
				Toast.makeText(OrderDetailActivity.this,
						"Phản hồi đơn hàng thành công", Toast.LENGTH_LONG)
						.show();
				lost.setVisible(false);
			} else {
				Toast.makeText(
						OrderDetailActivity.this,
						"Phản hồi đơn hàng không thành công. Xin vui lòng thử lại",
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
}

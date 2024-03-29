package vn.edu.fpt.fts.layout;

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

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import vn.edu.fpt.fts.classes.Constant;

public class SendOffer extends Fragment {

	private class WebService extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 30000;

		private static final String TAG = "WebServiceTask";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebService(int taskType, Context mContext, String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		@Override
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
			} catch (ConnectTimeoutException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);

			}

			return response;
		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
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

		@Override
		protected void onPostExecute(String response) {
			// Xu li du lieu tra ve sau khi insert thanh cong
			// handleResponse(response);
			pDlg.dismiss();
			if (Integer.parseInt(response) == 0) {
				Toast.makeText(getActivity(),
						"Có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT)
						.show();
			} else if (Integer.parseInt(response) == 2) {
				Toast.makeText(getActivity(),
						"Đề nghị đã bị hủy. Gửi đề nghị thất bại.",
						Toast.LENGTH_SHORT).show();
			} else if (Integer.parseInt(response) > 0) {
				Toast.makeText(getActivity(), "Gửi đề nghị thành công",
						Toast.LENGTH_SHORT).show();
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				Fragment fragment = new TabDeals();
				trs.replace(R.id.content_frame, fragment);
				trs.addToBackStack(null);
				trs.commit();
			}
		}

		@Override
		protected void onPreExecute() {
			showProgressDialog();

		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}

	}
	private class WebService2 extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 30000;

		private static final String TAG = "WebServiceTask";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebService2(int taskType, Context mContext, String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		@Override
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
			} catch (ConnectTimeoutException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);

			}

			return response;
		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
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

		@Override
		protected void onPostExecute(String response) {
			pDlg.dismiss();
			try {
				JSONObject good = new JSONObject(response);
				DecimalFormat formatter = new DecimalFormat();
				DecimalFormatSymbols symbol = new DecimalFormatSymbols();
				symbol.setGroupingSeparator('.');
				formatter.setDecimalFormatSymbols(symbol);
				startPlace.setText(good.getString("pickupAddress"));
				endPlace.setText(good.getString("deliveryAddress"));
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				Date start = format.parse(good.getString("pickupTime"));
				Date end = format.parse(good.getString("deliveryTime"));
				format.applyPattern("dd/MM/yyyy");
				startTime.setText(format.format(start));
				endTime.setText(format.format(end));
				pr.setText(formatter.format(Double.parseDouble(good.getString(
						"price").replace(".0", "")))
						+ " nghìn đồng");
				prc = good.getString("price");
				weight.setText(good.getString("weight") + " kg");
				if (good.has("notes")) {
					if (good.getString("notes").equals("")
							|| good.getString("notes").equals("null")) {
						nte.setText("Không có");
					} else {
						nte.setText(good.getString("notes"));
					}
				} else {
					nte.setText("Không có");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			showProgressDialog();

		}

		private void showProgressDialog() {
			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();
		}
	}
	private class WebService3 extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 30000;

		private static final String TAG = "WebServiceTask";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebService3(int taskType, Context mContext, String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		@Override
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
			} catch (ConnectTimeoutException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);

			}

			return response;
		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
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

		@Override
		protected void onPostExecute(String response) {
			pDlg.dismiss();
			try {
				DecimalFormat formatter = new DecimalFormat();
				DecimalFormatSymbols symbol = new DecimalFormatSymbols();
				symbol.setGroupingSeparator('.');
				formatter.setDecimalFormatSymbols(symbol);
				JSONObject obj = new JSONObject(response);
				JSONObject good = obj.getJSONObject("goods");
				startPlace.setText(good.getString("pickupAddress"));
				endPlace.setText(good.getString("deliveryAddress"));
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd hh:mm:ss");
				Date start = format.parse(good.getString("pickupTime"));
				Date end = format.parse(good.getString("deliveryTime"));
				format.applyPattern("dd/MM/yyyy");
				startTime.setText(format.format(start));
				endTime.setText(format.format(end));
				pr.setText(formatter.format(Double.parseDouble(obj.getString(
						"price"))) + " nghìn đồng");
				weight.setText(good.getString("weight") + " kg");
				if (obj.has("notes")) {
					if (obj.getString("notes").equals("")
							|| obj.getString("notes").equalsIgnoreCase("null")) {
						nte.setText("Không có");
					} else {
						nte.setText(obj.getString("notes"));
					}
				} else {
					nte.setText("Không có");
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {
			showProgressDialog();

		}

		private void showProgressDialog() {
			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();
		}
	}
	private class WebService4 extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 30000;

		private static final String TAG = "WebServiceTask";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebService4(int taskType, Context mContext, String processMessage) {

			this.taskType = taskType;
			this.mContext = mContext;
			this.processMessage = processMessage;
		}

		public void addNameValuePair(String name, String value) {

			params.add(new BasicNameValuePair(name, value));
		}

		@Override
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
			} catch (ConnectTimeoutException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
			} catch (Exception e) {

				Log.e(TAG, e.getLocalizedMessage(), e);

			}

			return response;
		}

		// Establish connection and socket (data retrieval) timeouts
		private HttpParams getHttpParams() {

			HttpParams htpp = new BasicHttpParams();

			HttpConnectionParams.setConnectionTimeout(htpp, CONN_TIMEOUT);
			HttpConnectionParams.setSoTimeout(htpp, SOCKET_TIMEOUT);

			return htpp;
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

		@Override
		protected void onPostExecute(String response) {
			// Xu li du lieu tra ve sau khi insert thanh cong
			// handleResponse(response);
			pDlg.dismiss();
			if (Integer.parseInt(response) == -1) {
				Toast.makeText(
						getActivity(),
						"Hàng đã có hóa đơn liên quan. Bạn không thể chấp nhận hàng này.",
						Toast.LENGTH_SHORT).show();
			} else if (Integer.parseInt(response) > 0) {
				Toast.makeText(getActivity(), "Hàng hóa đã được chấp nhận.",
						Toast.LENGTH_SHORT).show();
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				HistoryDetail fragment = new HistoryDetail();
				Bundle bundle = new Bundle();
				bundle.putString("orderID", response);
				fragment.setArguments(bundle);
				trs.replace(R.id.content_frame, fragment);
				trs.addToBackStack(null);
				trs.commit();
			} else {
				Toast.makeText(getActivity(),
						"Có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT)
						.show();
			}
		}

		@Override
		protected void onPreExecute() {
			showProgressDialog();

		}

		private void showProgressDialog() {

			pDlg = new ProgressDialog(mContext);
			pDlg.setMessage(processMessage);
			pDlg.setProgressDrawable(mContext.getWallpaper());
			pDlg.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			pDlg.setCancelable(false);
			pDlg.show();

		}
	}
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Deal/Create";
	private static final String SERVICE_URL2 = Constant.SERVICE_URL
			+ "Goods/getGoodsByID";
	private static final String SERVICE_URL3 = Constant.SERVICE_URL
			+ "Deal/getDealByID";
	private static final String SERVICE_URL4 = Constant.SERVICE_URL
			+ "Deal/acceptFirst";

	EditText note;

	String prc = "";

	EditText price;

	TextView startPlace, endPlace, startTime, endTime, pr, nte, weight;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.findItem(R.id.action_create).setVisible(false);
		if (getArguments().getString("dealID") == null) {
			inflater.inflate(R.menu.send_offer, menu);
		} else {
			MenuItem item = menu.add(Menu.NONE, R.id.action_send, 99,
					R.string.send);
			item.setActionView(R.layout.actionbar_send);
			item.getActionView().setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					Calendar calendar = Calendar.getInstance();
					String current = String.valueOf(calendar.get(Calendar.YEAR))
							+ "-"
							+ String.valueOf(calendar.get(Calendar.MONTH) + 1)
							+ "-"
							+ String.valueOf(calendar
									.get(Calendar.DAY_OF_MONTH))
							+ " "
							+ String.valueOf(calendar.get(Calendar.HOUR))
							+ ":"
							+ String.valueOf(calendar.get(Calendar.MINUTE))
							+ ":"
							+ String.valueOf(calendar.get(Calendar.SECOND));
					String pr = price.getText().toString();
					if (pr.equals("")) {
						Toast.makeText(getActivity(),
								"Giá đề nghị không được để trống.",
								Toast.LENGTH_SHORT).show();
					} else {
						WebService ws = new WebService(WebService.POST_TASK,
								getActivity(), "Đang xử lý ...");
						String refID = getArguments().getString("refID");
						if (getArguments().getString("refID") != null) {
							ws.addNameValuePair("dealID", getArguments()
									.getString("refID"));
							ws.addNameValuePair("refDealID", getArguments()
									.getString("refID"));
						} else {
							ws.addNameValuePair("dealID", "");
							ws.addNameValuePair("refDealID", "");
						}
						ws.addNameValuePair("price", pr);
						ws.addNameValuePair("notes", note.getText().toString());
						ws.addNameValuePair("createTime", current);
						ws.addNameValuePair("createBy", "driver");
						ws.addNameValuePair("routeID", getArguments()
								.getString("routeID"));
						ws.addNameValuePair("goodsID", getArguments()
								.getString("goodID"));
						ws.addNameValuePair("active", "1");
						ws.execute(new String[] { SERVICE_URL });
					}
				}
			});
			item.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT
					| MenuItem.SHOW_AS_ACTION_ALWAYS);
			item.setIcon(R.drawable.ic_action_email);
		}
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		getActivity().getActionBar().setTitle("Gửi đề nghị");
		getActivity().getActionBar().setIcon(
				R.drawable.ic_action_sort_by_size_white);
		View v = inflater.inflate(R.layout.activity_send_offer, container,
				false);

		price = (EditText) v.findViewById(R.id.editText1);
		note = (EditText) v.findViewById(R.id.editText2);
		startPlace = (TextView) v.findViewById(R.id.textView2);
		endPlace = (TextView) v.findViewById(R.id.textView4);
		startTime = (TextView) v.findViewById(R.id.textView6);
		endTime = (TextView) v.findViewById(R.id.textView8);
		pr = (TextView) v.findViewById(R.id.textView10);
		nte = (TextView) v.findViewById(R.id.textView12);
		weight = (TextView) v.findViewById(R.id.textView14);

		if (getArguments().getString("dealID") == null) {
			WebService2 ws2 = new WebService2(WebService2.POST_TASK,
					getActivity(), "");
			ws2.addNameValuePair("goodsID", getArguments().getString("goodID"));
			ws2.execute(SERVICE_URL2);
		} else {
			WebService3 ws3 = new WebService3(WebService3.POST_TASK,
					getActivity(), "");
			ws3.addNameValuePair("dealID", getArguments().getString("dealID"));
			ws3.execute(SERVICE_URL3);
		}

		return v;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_send:
			Calendar calendar = Calendar.getInstance();
			String current = String.valueOf(calendar.get(Calendar.YEAR)) + "-"
					+ String.valueOf(calendar.get(Calendar.MONTH) + 1) + "-"
					+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + " "
					+ String.valueOf(calendar.get(Calendar.HOUR)) + ":"
					+ String.valueOf(calendar.get(Calendar.MINUTE)) + ":"
					+ String.valueOf(calendar.get(Calendar.SECOND));
			String pr = price.getText().toString();
			if (pr.equals("")) {
				Toast.makeText(getActivity(),
						"Giá đề nghị không được để trống", Toast.LENGTH_SHORT)
						.show();
			} else {
				WebService ws = new WebService(WebService.POST_TASK,
						getActivity(), "Đang xử lý ...");
				String refID = getArguments().getString("refID");
				if (getArguments().getString("refID") != null) {
					ws.addNameValuePair("dealID",
							getArguments().getString("refID"));
					ws.addNameValuePair("refDealID",
							getArguments().getString("refID"));
				} else {
					ws.addNameValuePair("dealID", "");
					ws.addNameValuePair("refDealID", "");
				}
				ws.addNameValuePair("price", pr);
				ws.addNameValuePair("notes", note.getText().toString());
				ws.addNameValuePair("createTime", current);
				ws.addNameValuePair("createBy", "driver");
				ws.addNameValuePair("routeID",
						getArguments().getString("routeID"));
				ws.addNameValuePair("goodsID",
						getArguments().getString("goodID"));
				ws.addNameValuePair("active", "1");
				ws.execute(new String[] { SERVICE_URL });
			}
			return true;
		case R.id.action_accept:
			WebService4 ws = new WebService4(WebService.POST_TASK,
					getActivity(), "Đang xử lý ...");
			ws.addNameValuePair("createBy", "driver");
			ws.addNameValuePair("routeID", getArguments().getString("routeID"));
			ws.addNameValuePair("goodsID", getArguments().getString("goodID"));
			ws.execute(new String[] { SERVICE_URL4 });
			return true;
		default:
			break;
		}
		return false;
	}
}

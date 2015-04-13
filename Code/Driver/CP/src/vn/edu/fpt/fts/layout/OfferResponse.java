package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
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
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import vn.edu.fpt.fts.classes.Constant;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OfferResponse extends Fragment {
	Button sendNewOffer;
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Deal/getDealByID";
	private static final String SERVICE_URL2 = Constant.SERVICE_URL
			+ "Deal/accept";
	private static final String SERVICE_URL3 = Constant.SERVICE_URL
			+ "Deal/decline";
	TextView startPlace, endPlace, startTime, endTime, price, note, weight;
	TextView startPoint, point1, point2, endPoint, startDate, endDate, payload,
			cantLoad;
	String id;
	String routeID;
	String goodID;
	String refID;
	String oldPrice;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		getActivity().getActionBar().setIcon(
				R.drawable.ic_action_sort_by_size_white);
		getActivity().getActionBar().setTitle("Trả lời đề nghị");
		id = getArguments().getString("dealID");
		WebService ws = new WebService(WebService.POST_TASK, getActivity(),
				"Đang xử lý ...");
		ws.addNameValuePair("dealID", id);
		ws.execute(new String[] { SERVICE_URL });
		View v = inflater.inflate(R.layout.activity_offer_response, container,
				false);

		startPlace = (TextView) v.findViewById(R.id.textView2);
		endPlace = (TextView) v.findViewById(R.id.textView4);
		startTime = (TextView) v.findViewById(R.id.textView6);
		endTime = (TextView) v.findViewById(R.id.textView8);
		price = (TextView) v.findViewById(R.id.textView10);
		note = (TextView) v.findViewById(R.id.textView12);
		weight = (TextView) v.findViewById(R.id.textView14);
		startPoint = (TextView) v.findViewById(R.id.textView18);
		point1 = (TextView) v.findViewById(R.id.textView20);
		point2 = (TextView) v.findViewById(R.id.textView22);
		endPoint = (TextView) v.findViewById(R.id.textView24);
		startDate = (TextView) v.findViewById(R.id.textView26);
		endDate = (TextView) v.findViewById(R.id.textView28);
		payload = (TextView) v.findViewById(R.id.textView30);
		cantLoad = (TextView) v.findViewById(R.id.textView32);
		
		return v;
	}

	private class WebService extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 30000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebService(int taskType, Context mContext, String processMessage) {

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
			JSONObject obj;
			try {
				obj = new JSONObject(response);
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
				price.setText(obj.getString("price").replace(".0", "")
						+ " nghìn đồng");
				weight.setText(good.getString("weight") + " kg");
				if (obj.has("notes")) {
					if (obj.getString("notes").equals("")
							|| obj.getString("notes").equalsIgnoreCase("null")) {
						note.setText("Không có");
					} else {
						note.setText(obj.getString("notes"));
					}
				} else {
					note.setText("Không có");
				}
				routeID = obj.getString("routeID");
				goodID = obj.getString("goodsID");
				oldPrice = obj.getString("price");
				refID = obj.getString("refDealID");

				obj = obj.getJSONObject("route");
				Object intervent;
				startPoint.setText(obj.getString("startingAddress"));

				if (obj.has("routeMarkers")) {
					intervent = obj.get("routeMarkers");
					if (intervent instanceof JSONArray) {
						JSONArray catArray = obj.getJSONArray("routeMarkers");
						for (int j = 0; j < catArray.length(); j++) {
							JSONObject cat = catArray.getJSONObject(j);
							if (j == 0) {
								if (!cat.getString("routeMarkerLocation")
										.equals("")) {
									point1.setText(cat
											.getString("routeMarkerLocation"));
								} else {
									point1.setText("Không có");
								}
								point2.setText("Không có");
							} else if (j == 1) {
								if (!cat.getString("routeMarkerLocation")
										.equals("")) {
									point2.setText(cat
											.getString("routeMarkerLocation"));
								}
							}
						}
					} else if (intervent instanceof JSONObject) {
						JSONObject cat = obj.getJSONObject("routeMarkers");
						point1.setText(cat.getString("routeMarkerLocation"));
						point2.setText("Không có");
					}
				} else {
					point1.setText("Không có");
					point2.setText("Không có");
				}

				endPoint.setText(obj.getString("destinationAddress"));
				format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date startD = null;
				try {
					startD = format.parse(obj.getString("startTime"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				format.applyPattern("dd/MM/yyyy hh:mm");
				startDate.setText(format.format(startD));
				Date endD = null;
				try {
					format.applyPattern("yyyy-MM-dd hh:mm:ss");
					endD = format.parse(obj.getString("finishTime"));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				format.applyPattern("dd/MM/yyyy");
				endDate.setText(format.format(endD));
				payload.setText(obj.getString("weight") + " kg");

				String cLoad = "";
				if (obj.has("goodsCategory")) {
					intervent = obj.get("goodsCategory");
					if (intervent instanceof JSONArray) {
						JSONArray goodArray = obj.getJSONArray("goodsCategory");
						for (int j = 0; j < goodArray.length(); j++) {
							good = goodArray.getJSONObject(j);
							cLoad += good.getString("name") + ", ";
						}
						cLoad = cLoad.substring(0, cLoad.length() - 2);
					} else if (intervent instanceof JSONObject) {
						good = obj.getJSONObject("goodsCategory");
						cLoad += good.getString("name");
					}
				} else {
					cLoad += "không có loại hàng nào";
				}

				cantLoad.setText(cLoad);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ParseException e) {
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
			} catch (SocketTimeoutException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
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

	private class WebService2 extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 30000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebService2(int taskType, Context mContext, String processMessage) {

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
			pDlg.dismiss();
			if (Integer.parseInt(response) == 1) {
				Toast.makeText(getActivity(), "Đề nghị đã được chấp nhận.",
						Toast.LENGTH_SHORT).show();
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				TabDeals fragment = new TabDeals();
				trs.replace(R.id.content_frame, fragment);
				trs.addToBackStack(null);
				trs.commit();
			}  else if (Integer.parseInt(response) == 0){
				Toast.makeText(getActivity(),
						"Có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getActivity(),
						"Đề nghị đã bị hủy. Không thể chấp nhận đề nghị.", Toast.LENGTH_SHORT)
						.show();
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
			} catch (SocketTimeoutException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
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

	private class WebService3 extends AsyncTask<String, Integer, String> {

		public static final int POST_TASK = 1;
		public static final int GET_TASK = 2;

		private static final String TAG = "WebServiceTask";

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 30000;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 30000;

		private int taskType = GET_TASK;
		private Context mContext = null;
		private String processMessage = "Processing...";

		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();

		private ProgressDialog pDlg = null;

		public WebService3(int taskType, Context mContext, String processMessage) {

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
			pDlg.dismiss();
			if (Integer.parseInt(response) == 1) {
				Toast.makeText(getActivity(), "Đề nghị đã được từ chối.",
						Toast.LENGTH_SHORT).show();
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				Fragment fragment = new TabDeals();
				trs.replace(R.id.content_frame, fragment);
				trs.addToBackStack(null);
				trs.commit();
			} else if (Integer.parseInt(response) == 0){
				Toast.makeText(getActivity(),
						"Có lỗi xảy ra. Vui lòng thử lại.", Toast.LENGTH_SHORT)
						.show();
			} else {
				Toast.makeText(getActivity(),
						"Đề nghị đã bị hủy. Không thể từ chối đề nghị.", Toast.LENGTH_SHORT)
						.show();
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
			} catch (SocketTimeoutException e) {
				getActivity().runOnUiThread(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getActivity(),
								"Không thể kết nối tới máy chủ",
								Toast.LENGTH_SHORT).show();
					}
				});
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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		menu.findItem(R.id.action_create).setVisible(false);
		inflater.inflate(R.menu.offer_response, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.action_counter:
			Bundle bundle = new Bundle();
			bundle.putString("dealID", id);
			bundle.putString("refID", id);
			bundle.putString("routeID", routeID);
			bundle.putString("goodID", goodID);
			FragmentManager mng = getActivity().getSupportFragmentManager();
			FragmentTransaction trs = mng.beginTransaction();
			SendOffer frag = new SendOffer();
			frag.setArguments(bundle);
			trs.replace(R.id.content_frame, frag);
			trs.addToBackStack(null);
			trs.commit();
			return true;
		case R.id.action_accept:
			WebService2 ws = new WebService2(WebService.POST_TASK,
					getActivity(), "Đang xử lý ...");
			ws.addNameValuePair("dealID", id);
			ws.addNameValuePair("active", "1");
			ws.addNameValuePair("price", oldPrice);
			ws.addNameValuePair("notes", note.getText().toString());
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String createTime = format.format(Calendar.getInstance().getTime());
			ws.addNameValuePair("createTime", createTime);
			ws.addNameValuePair("createBy", "driver");
			ws.addNameValuePair("routeID", routeID);
			ws.addNameValuePair("goodsID", goodID);
			if (refID.equals("")) {
				ws.addNameValuePair("refDealID", id);
			} else {
				ws.addNameValuePair("refDealID", refID);
			}
			ws.execute(new String[] { SERVICE_URL2 });
			return true;
		case R.id.action_decline:
			WebService3 ws2 = new WebService3(WebService.POST_TASK,
					getActivity(), "Đang xử lý ...");
			ws2.addNameValuePair("dealID", id);
			ws2.addNameValuePair("active", "1");
			ws2.addNameValuePair("price", oldPrice);
			ws2.addNameValuePair("notes", note.getText().toString());
			SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
			String createTime2 = format2.format(Calendar.getInstance()
					.getTime());
			ws2.addNameValuePair("createTime", createTime2);
			ws2.addNameValuePair("createBy", "driver");
			ws2.addNameValuePair("routeID", routeID);
			ws2.addNameValuePair("goodsID", goodID);
			if (refID.equals("")) {
				ws2.addNameValuePair("refDealID", id);
			} else {
				ws2.addNameValuePair("refDealID", refID);
			}
			ws2.execute(new String[] { SERVICE_URL3 });
			return true;
		default:
			break;
		}
		return false;
	}
}

package vn.edu.fpt.fts.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
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
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;
import vn.edu.fpt.fts.activity.MainActivity;
import vn.edu.fpt.fts.activity.SuggestActivity;
import vn.edu.fpt.fts.adapter.PlacesAutoCompleteAdapter;
import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.common.GeocoderHelper;
import vn.edu.fpt.fts.common.JSONParser;

public class InformationFragment extends Fragment {
	private class CalculateMoney extends AsyncTask<String, Void, String> {

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
				if (obj.getString("status").equals("ZERO_RESULTS")) {
					// Toast.makeText(CreateGoodsActivity.this,
					// "Không tìm thấy lộ trình", Toast.LENGTH_SHORT)
					// .show();
				} else {
					JSONArray array = obj.getJSONArray("routes");
					JSONArray array2 = array.getJSONObject(0)
							.getJSONArray("legs");

					String[] tmp = array2.getJSONObject(0)
							.getJSONObject("distance").getString("text")
							.replace(",", "").split(" ");
					double distance = Double.parseDouble(tmp[0]);
					int weight = Integer
							.parseInt(etWeight.getText().toString());
					int price = Common.calculateGoodsPrice(weight, distance);
					etPrice.setText(price + "");
				}

			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
	public class MyDatePickerDialog extends DatePickerDialog {

		private CharSequence title;

		public MyDatePickerDialog(Context context, OnDateSetListener callBack,
				int year, int monthOfYear, int dayOfMonth) {
			super(context, callBack, year, monthOfYear, dayOfMonth);
		}

		@Override
		public void onDateChanged(DatePicker view, int year, int month, int day) {
			super.onDateChanged(view, year, month, day);
			setTitle(title);
		}

		public void setPermanentTitle(CharSequence title) {
			this.title = title;
			setTitle(title);
		}
	}
	private class WebServiceTask extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 10000;

		private static final String TAG = "WebServiceTask";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebServiceTask(int taskType, Context mContext,
				String processMessage) {

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
			if (response.equals("0")) {
				Toast.makeText(getActivity(),
						"Đã có lỗi xảy ra. Hàng chưa được cập nhật",
						Toast.LENGTH_LONG).show();

			} else if (response.equals("1")) {
				Toast.makeText(getActivity(), "Hàng cập nhật thành công",
						Toast.LENGTH_LONG).show();
				// getActivity().finish();
				// getActivity().startActivity(getActivity().getIntent());
				Intent intent = new Intent(getActivity(), SuggestActivity.class);
				intent.putExtra("goodsID", goodsID);
				intent.putExtra("cate", cateId + "");
				Bundle extra = new Bundle();
				extra.putParcelable("pickup", new LatLng(pickupLat, pickupLng));
				extra.putParcelable("deliver", new LatLng(deliverLat,
						deliverLng));
				intent.putExtra("extra", extra);
				Bundle bundle = new Bundle();
				bundle.putString("price", etPrice.getText().toString()
						+ " nghìn đồng");
				bundle.putString("weight", etWeight.getText().toString()
						+ " kg");
				bundle.putString("pickup", Common.formatLocation(actPickupAddr
						.getText().toString()));
				bundle.putString("deliver", Common
						.formatLocation(actDeliverAddr.getText().toString()));
				intent.putExtra("bundle", bundle);
				startActivity(intent);
			} else if (response.equals("2")) {
				Toast.makeText(getActivity(), "Hàng hiện không thể cập nhật",
						Toast.LENGTH_LONG).show();
			}
			pDlg.dismiss();

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
	private class WebServiceTask2 extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private static final String TAG = "WebServiceTask2";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebServiceTask2(int taskType, Context mContext,
				String processMessage) {

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
			try {
				JSONObject jsonObject = new JSONObject(response);
				JSONArray array = jsonObject.getJSONArray("goodsCategory");
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject2 = array.getJSONObject(i);
					cateList.add(jsonObject2.getString("name"));
				}
				dataAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.getLocalizedMessage());
			}
			pDlg.dismiss();

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
	private class WebServiceTask3 extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private static final String TAG = "WebServiceTask2";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebServiceTask3(int taskType, Context mContext,
				String processMessage) {

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
			try {
				JSONObject jsonObject = new JSONObject(response);
				String deliverDate = jsonObject.getString("deliveryTime");
				String pickupDate = jsonObject.getString("pickupTime");
				String[] tmp = deliverDate.split(" ");
				String[] tmp1 = pickupDate.split(" ");

				deliverDate = Common.formatDateFromString(tmp[0]);
				pickupDate = Common.formatDateFromString(tmp1[0]);
				String[] pickup = pickupDate.split("/");
				calendar1 = Calendar.getInstance();
				calendar1.set(Calendar.DAY_OF_MONTH,
						Integer.parseInt(pickup[0]));
				calendar1.set(Calendar.MONTH, Integer.parseInt(pickup[1]) - 1);
				calendar1.set(Calendar.YEAR, Integer.parseInt(pickup[2]));
				String a = Common.formatDate(calendar1);
				String[] deliver = deliverDate.split("/");
				calendar2 = Calendar.getInstance();
				calendar2.set(Calendar.DAY_OF_MONTH,
						Integer.parseInt(deliver[0]));
				calendar2.set(Calendar.MONTH, Integer.parseInt(deliver[1]) - 1);
				calendar2.set(Calendar.YEAR, Integer.parseInt(deliver[2]));

				etDeliverDate.setText(deliverDate);
				etNotes.setText(jsonObject.getString("notes"));
				etPickupDate.setText(pickupDate);
				etPrice.setText(jsonObject.getString("price").replace(".0", ""));
				etWeight.setText(jsonObject.getString("weight"));
				actPickupAddr.setText(jsonObject.getString("pickupAddress"));
				actDeliverAddr.setText(jsonObject.getString("deliveryAddress"));

				pickupLat = Double.parseDouble(jsonObject
						.getString("pickupMarkerLatidute"));
				pickupLng = Double.parseDouble(jsonObject
						.getString("pickupMarkerLongtitude"));
				deliverLat = Double.parseDouble(jsonObject
						.getString("deliveryMarkerLatidute"));
				deliverLng = Double.parseDouble(jsonObject
						.getString("deliveryMarkerLongtitude"));

				int cate = Integer.parseInt(jsonObject
						.getString("goodsCategoryID"));
				cateId = cate;
				int pos = 0;
				switch (cate) {
				case 1:
					pos = 0;
					break;
				case 2:
					pos = 1;
					break;
				case 4:
					pos = 2;
					break;
				case 5:
					pos = 3;
					break;
				}
				spinner.setSelection(pos);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				Log.e(TAG, e.getLocalizedMessage());
			}
			pDlg.dismiss();

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
	private class WebServiceTask4 extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private static final String TAG = "WebServiceTask2";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebServiceTask4(int taskType, Context mContext,
				String processMessage) {

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
			try {
				JSONObject jsonObject = new JSONObject(response);
				GeocoderHelper geocoderHelper = new GeocoderHelper();
				LatLng latLng = geocoderHelper.getLatLong(jsonObject);
				if (latLng != null) {
					pickupLat = latLng.latitude;
					pickupLng = latLng.longitude;
				}			
				calculate();
			} catch (JSONException ex) {
				ex.printStackTrace();
			}
		}

		@Override
		protected void onPreExecute() {

			showProgressDialog();

		}

		private void showProgressDialog() {

		}

	}
	private class WebServiceTask5 extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 5000;

		private static final String TAG = "WebServiceTask2";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebServiceTask5(int taskType, Context mContext,
				String processMessage) {

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
			try {
				JSONObject jsonObject = new JSONObject(response);
				GeocoderHelper geocoderHelper = new GeocoderHelper();
				LatLng latLng = geocoderHelper.getLatLong(jsonObject);
				if (latLng != null) {
					deliverLat = latLng.latitude;
					deliverLng = latLng.longitude;	
				}			
				calculate();
			} catch (JSONException ex) {
				ex.printStackTrace();
			}

		}

		@Override
		protected void onPreExecute() {

			showProgressDialog();

		}

		private void showProgressDialog() {

		}

	}
	private class WebServiceTask6 extends AsyncTask<String, Integer, String> {

		// connection timeout, in milliseconds (waiting to connect)
		private static final int CONN_TIMEOUT = 3000;
		public static final int GET_TASK = 2;

		public static final int POST_TASK = 1;

		// socket timeout, in milliseconds (waiting for data)
		private static final int SOCKET_TIMEOUT = 10000;

		private static final String TAG = "WebServiceTask";

		private Context mContext = null;
		private ArrayList<NameValuePair> params = new ArrayList<NameValuePair>();
		private ProgressDialog pDlg = null;

		private String processMessage = "Processing...";

		private int taskType = GET_TASK;

		public WebServiceTask6(int taskType, Context mContext,
				String processMessage) {

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
			if (response.equals("0")) {
				Toast.makeText(getActivity(),
						"Đã có lỗi xảy ra. Không thể xóa hàng",
						Toast.LENGTH_LONG).show();
			} else if (response.equals("1")) {
				Toast.makeText(getActivity(), "Xóa hàng thành công",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(getActivity(), MainActivity.class);
				startActivity(intent);
			} else if (response.equals("2")) {
				Toast.makeText(getActivity(),
						"Hàng hiện đang deal với lộ trình. Không thể xóa",
						Toast.LENGTH_LONG).show();
			}
			pDlg.dismiss();

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
	private AutoCompleteTextView actPickupAddr, actDeliverAddr;
	private Button btnPost, btnDelete;
	private Calendar calendar1, calendar2, currentTime;
	private int cateId, spinnerPos;

	private List<String> cateList = new ArrayList<String>();

	private ArrayAdapter<String> dataAdapter;

	private DatePickerDialog.OnDateSetListener date1, date2;

	// ------------------------------------------------------------------------------

	private EditText etPickupDate, etDeliverDate, etNotes, etPrice, etWeight;

	private ImageButton ibPickupMap, ibDeliverMap;

	private String ownerid, goodsID, selected, errorMsg = "";

	private Double pickupLat = 0.0, deliverLat = 0.0, pickupLng = 0.0,
			deliverLng = 0.0;

	private Spinner spinner;

	private void calculate() {
		if (!(etWeight.getText().toString().trim().equals(""))) {
			if (pickupLat != 0 && pickupLng != 0 && deliverLat != 0
					&& deliverLng != 0) {
				String url = new GeocoderHelper().makeURL(new LatLng(pickupLat,
						pickupLng), new LatLng(deliverLat, deliverLng));
				try {
					new CalculateMoney().execute(url);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View rootView = inflater.inflate(R.layout.fragment_information,
				container, false);
		goodsID = getActivity().getIntent().getStringExtra("goodsID");

		etNotes = (EditText) rootView.findViewById(R.id.edittext_note);
		etPrice = (EditText) rootView.findViewById(R.id.edittext_price);
		etWeight = (EditText) rootView.findViewById(R.id.edittext_weight);

		// drop down list
		WebServiceTask2 task2 = new WebServiceTask2(WebServiceTask2.GET_TASK,
				getActivity(), "Đang xử lý...");
		String url1 = Common.IP_URL + Common.Service_GoodsCategory_Get;
		// task2.execute(new String[] { url1 });
		task2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url1 });

		spinner = (Spinner) rootView.findViewById(R.id.spinner_goods_type);
		dataAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_spinner_item, cateList);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(dataAdapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String selected = parent.getItemAtPosition(position).toString();
				spinnerPos = position;
				if (selected.equals("Hàng thực phẩm")) {
					cateId = 1;
				} else if (selected.equals("Hàng đông lạnh")) {
					cateId = 2;
				} else if (selected.equals("Hàng dễ vỡ")) {
					cateId = 4;
				} else if (selected.equals("Hàng dễ cháy nổ")) {
					cateId = 5;
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});

		// date picker listener
		calendar1 = Calendar.getInstance();

		date1 = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub

				calendar1.set(Calendar.YEAR, year);
				calendar1.set(Calendar.MONTH, monthOfYear);
				calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				Common.updateLabel(etPickupDate, calendar1);
			}
		};

		calendar2 = Calendar.getInstance();
		date2 = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				calendar2.set(Calendar.YEAR, year);
				calendar2.set(Calendar.MONTH, monthOfYear);
				calendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				Common.updateLabel(etDeliverDate, calendar2);
			}
		};

		// date picker: pickup date
		etPickupDate = (EditText) rootView
				.findViewById(R.id.edittext_pickup_date);
		etPickupDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					MyDatePickerDialog dialog = new MyDatePickerDialog(
							getActivity(), date1, calendar1.get(Calendar.YEAR),
							calendar1.get(Calendar.MONTH), calendar1
									.get(Calendar.DAY_OF_MONTH));
					dialog.setPermanentTitle("Ngày có thể nhận hàng");
					DatePicker picker = dialog.getDatePicker();
					Calendar cal = Calendar.getInstance();
					picker.setMinDate(cal.getTimeInMillis() - 1000);
					cal.add(Calendar.MONTH, 1);
					picker.setMaxDate(cal.getTimeInMillis());
					dialog.show();
				}
			}
		});

		// date picker: deliver date
		etDeliverDate = (EditText) rootView
				.findViewById(R.id.edittext_deliver_date);
		etDeliverDate
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (hasFocus) {
							MyDatePickerDialog dialog = new MyDatePickerDialog(
									getActivity(), date2, calendar2
											.get(Calendar.YEAR), calendar2
											.get(Calendar.MONTH), calendar2
											.get(Calendar.DAY_OF_MONTH));
							dialog.setPermanentTitle("Ngày có thể giao hàng");
							DatePicker picker = dialog.getDatePicker();
							Calendar cal = Calendar.getInstance();
							cal.set(Calendar.MONTH,
									calendar1.get(Calendar.MONTH));
							cal.set(Calendar.DAY_OF_MONTH,
									calendar1.get(Calendar.DAY_OF_MONTH));
							picker.setMinDate(cal.getTimeInMillis() - 1000);
							cal.add(Calendar.MONTH, 1);
							picker.setMaxDate(cal.getTimeInMillis());
							dialog.show();
						}
					}
				});

		// pickup address and map button
		actPickupAddr = (AutoCompleteTextView) rootView
				.findViewById(R.id.edittext_pickup_address);
		actPickupAddr.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				WebServiceTask4 wst4 = new WebServiceTask4(
						WebServiceTask3.POST_TASK, getActivity(),
						"Đang xử lý...");
				String url = "http://maps.google.com/maps/api/geocode/json?address="
						+ actPickupAddr.getText().toString()
								.replaceAll(" ", "%20") + "&sensor=false";
				try {
					String test = wst4.executeOnExecutor(
							AsyncTask.THREAD_POOL_EXECUTOR, url).get();
					test = "abc";
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}
		});
		
		ibPickupMap = (ImageButton) rootView
				.findViewById(R.id.imagebtn_pickup_address);
		ibPickupMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						EditGoodsMapActivity.class);
				intent.putExtra("marker", new LatLng(pickupLat, pickupLng));				
				startActivity(intent);
			}
		});

		// deliver address and map button
		actDeliverAddr = (AutoCompleteTextView) rootView
				.findViewById(R.id.edittext_deliver_address);
		actDeliverAddr.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				WebServiceTask5 wst5 = new WebServiceTask5(
						WebServiceTask3.POST_TASK, getActivity(),
						"Đang xử lý...");
				String url2 = "http://maps.google.com/maps/api/geocode/json?address="
						+ actDeliverAddr.getText().toString()
								.replaceAll(" ", "%20") + "&sensor=false";
				try {
					wst5.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, url2)
							.get();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}
		});
		
		ibDeliverMap = (ImageButton) rootView
				.findViewById(R.id.imagebtn_deliver_address);
		ibDeliverMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						EditGoodsMapActivity.class);
				intent.putExtra("marker", new LatLng(deliverLat, deliverLng));
				startActivity(intent);
			}
		});

		// auto complete textview
		actPickupAddr.setAdapter(new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.list_item_pickup));
		actDeliverAddr.setAdapter(new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.list_item_deliver));

		// button
		btnPost = (Button) rootView.findViewById(R.id.btn_post);
		btnPost.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Chi viec goi ham postData
				if (validate()) {
					postData(v);
				} else {
					Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_LONG)
							.show();
				}

			}
		});

		btnDelete = (Button) rootView.findViewById(R.id.btn_delete);
		btnDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				WebServiceTask6 wst6 = new WebServiceTask6(
						WebServiceTask6.POST_TASK, getActivity(),
						"Đang xử lý...");
				wst6.addNameValuePair("goodsID", goodsID);
				String url = Common.IP_URL + Common.Service_Goods_Delete;
				wst6.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
						new String[] { url });

			}
		});

		etWeight.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				calculate();
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
		});

		// set owner id

		SharedPreferences preferences = getActivity().getSharedPreferences(
				"MyPrefs", Context.MODE_PRIVATE);
		ownerid = preferences.getString("ownerID", "");

		WebServiceTask3 wst3 = new WebServiceTask3(WebServiceTask3.POST_TASK,
				getActivity(), "Đang xử lý...");
		String url = Common.IP_URL + Common.Service_Goods_getGoodsByID;
		wst3.addNameValuePair("goodsID", goodsID);
		// wst3.execute(new String[] { url });
		wst3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });

		return rootView;
	}

	public void postData(View vw) {

		// Code sample lay cac gia tri tu edittext tren man hinh, lam tuong tu

		// EditText edFirstName = (EditText) findViewById(R.id.first_name);
		// EditText edLastName = (EditText) findViewById(R.id.last_name);
		// EditText edEmail = (EditText) findViewById(R.id.email);
		//
		// String firstName = edFirstName.getText().toString();
		// String lastName = edLastName.getText().toString();
		// String email = edEmail.getText().toString();
		//
		// if (firstName.equals("") || lastName.equals("") || email.equals(""))
		// {
		// Toast.makeText(this, "Please enter in all required fields.",
		// Toast.LENGTH_LONG).show();
		// return;
		// }

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK,
				getActivity(), "Đang xử lý...");
		// Cac cap gia tri gui ve server
		currentTime = Calendar.getInstance();
		wst.addNameValuePair("goodsID", goodsID);
		wst.addNameValuePair("active", "1");
		wst.addNameValuePair("createTime", Common.formatDate(currentTime));
		wst.addNameValuePair("deliveryAddress", actDeliverAddr.getText()
				.toString());
		wst.addNameValuePair("deliveryMarkerLatidute",
				Double.toString(deliverLat));
		wst.addNameValuePair("deliveryMarkerLongtitude",
				Double.toString(deliverLng));
		String a = Common.formatDate(calendar1);
		wst.addNameValuePair("deliveryTime", Common.formatDate(calendar2));
		wst.addNameValuePair("goodsCategoryID", Integer.toString(cateId));
		wst.addNameValuePair("notes", etNotes.getText().toString());
		wst.addNameValuePair("ownerID", ownerid);
		wst.addNameValuePair("pickupAddress", actPickupAddr.getText()
				.toString());
		wst.addNameValuePair("pickupMarkerLatidute", Double.toString(pickupLat));
		wst.addNameValuePair("pickupMarkerLongtitude",
				Double.toString(pickupLng));
		wst.addNameValuePair("pickupTime", Common.formatDate(calendar1));
		wst.addNameValuePair("price", etPrice.getText().toString());
		wst.addNameValuePair("weight", etWeight.getText().toString());

		// the passed String is the URL we will POST to
		String url = Common.IP_URL + Common.Service_Goods_Update;
		// wst.execute(new String[] { url });
		wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });

	}
	
	public boolean validate() {
		boolean check = true;
		String tmp = etWeight.getText().toString();
		if (etWeight.getText().toString().trim().length() == 0) {
			check = false;
			errorMsg = "Khối lượng không được để trống";
		}
		if (etDeliverDate.getText().toString().trim().length() == 0) {
			check = false;
			errorMsg = "Ngày giao hàng không được để trống";
		}
		if (etPickupDate.getText().toString().trim().length() == 0) {
			check = false;
			errorMsg = "Ngày nhận hàng không được để trống";
		}
		if (etPrice.getText().toString().trim().length() == 0) {
			check = false;
			errorMsg = "Giá tiền không được để trống";
		}
		if (actDeliverAddr.getText().toString().trim().length() == 0) {
			check = false;
			errorMsg = "Địa chỉ giao hàng không được để trống";
		}
		if (actPickupAddr.getText().toString().trim().length() == 0) {
			check = false;
			errorMsg = "Địa chỉ nhận hàng không được để trống";
		}
		if (pickupLat == 0 || pickupLng == 0) {
			check = false;
			errorMsg = "Địa chỉ nhận hàng không phù hợp";
		}
		if (deliverLat == 0 || deliverLng == 0) {
			check = false;
			errorMsg = "Địa chỉ giao hàng không phù hợp";
		}
		String a = Common.formatDate(calendar1);
		String b = Common.formatDate(calendar2);
		if (calendar1.compareTo(calendar2) >= 0) {
			check = false;
			errorMsg = "Ngày nhận hàng không được trễ hơn ngày giao hàng";
		}

		// Geocoder geocoder = new Geocoder(getActivity());
		// try {
		// List<Address> list = geocoder.getFromLocationName(actPickupAddr
		// .getText().toString(), 1);
		// List<Address> list2 = geocoder.getFromLocationName(actDeliverAddr
		// .getText().toString(), 1);
		// if (list.size() > 0 && list2.size() > 0) {
		// pickupLng = list.get(0).getLongitude();
		// pickupLat = list.get(0).getLatitude();
		//
		// deliverLng = list2.get(0).getLongitude();
		// deliverLat = list2.get(0).getLatitude();
		// } else {
		// errorMsg = "Địa chỉ không có thật";
		// check = false;
		// }
		// } catch (IOException ex) {
		// ex.printStackTrace();
		// check = false;
		// errorMsg = "Địa chỉ không có thật";
		// }

		GeocoderHelper helper = new GeocoderHelper();

		// new GetLocation().execute(actPickupAddr.getText()
		// .toString());
		// JSONObject addr1 = helper.getLocationInfo(actPickupAddr.getText()
		// .toString());
		// JSONObject addr2 = helper.getLocationInfo(actDeliverAddr.getText()
		// .toString());
		// if (addr1 != null && addr2 != null) {
		// LatLng latLng1 = helper.getLatLong(addr1);
		// LatLng latLng2 = helper.getLatLong(addr2);
		// if (latLng1 != null & latLng2 != null) {
		// pickupLng = helper.getLatLong(addr1).longitude;
		// pickupLat = helper.getLatLong(addr1).latitude;
		// deliverLng = helper.getLatLong(addr2).longitude;
		// deliverLat = helper.getLatLong(addr2).latitude;
		// } else {
		// errorMsg = "Địa chỉ không có thật";
		// check = false;
		// }
		//
		// }

		// if (!helper.checkPath(url)) {
		// check = false;
		// errorMsg = "Không thể tìm đường đi thích hợp cho địa chỉ đã nhập";
		// }

		return check;
	}
}

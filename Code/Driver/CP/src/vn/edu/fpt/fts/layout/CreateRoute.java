package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONObject;

import com.google.android.gms.maps.model.LatLng;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import vn.edu.fpt.fts.classes.Constant;
import vn.edu.fpt.fts.drawer.MyDatePickerDialog;
import vn.edu.fpt.fts.drawer.MyTimePickerDialog;
import vn.edu.fpt.fts.helper.Common;
import vn.edu.fpt.fts.helper.GeocoderHelper;
import vn.edu.fpt.fts.helper.JSONParser;
import vn.edu.fpt.fts.helper.PlacesAutoCompleteAdapter;

public class CreateRoute extends Fragment {

	private class CalculateMiddlePoints extends
			AsyncTask<String, String, String> {

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

		public CalculateMiddlePoints(int taskType, Context mContext,
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
	private class GetAddress extends AsyncTask<Double, Void, String> {
		private final AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient
				.newInstance(GetAddress.class.getName());

		@Override
		protected String doInBackground(Double... locations) {
			String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
					+ locations[0]
					+ ","
					+ locations[1]
					+ "&sensor=false&language=vi";

			try {
				JSONObject googleMapResponse = new JSONObject(
						ANDROID_HTTP_CLIENT.execute(new HttpGet(googleMapUrl),
								new BasicResponseHandler()));
				JSONArray results = (JSONArray) googleMapResponse
						.get("results");
				JSONObject result = results.getJSONObject(0);
				String address = result.getString("formatted_address");
				ANDROID_HTTP_CLIENT.close();
				return address;
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ANDROID_HTTP_CLIENT.close();
		}
	}
	private class GetFullAddress extends AsyncTask<String, Void, String> {
		private final AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient
				.newInstance(GetAddress.class.getName());

		@Override
		protected String doInBackground(String... locations) {
			String googleMapUrl = "http://maps.googleapis.com/maps/api/geocode/json?address="
					+ locations[0].replace(" ", "%20")
					+ "&sensor=false&language=vi";

			try {
				JSONObject googleMapResponse = new JSONObject(
						ANDROID_HTTP_CLIENT.execute(new HttpGet(googleMapUrl),
								new BasicResponseHandler()));
				JSONArray results = (JSONArray) googleMapResponse
						.get("results");
				JSONObject result = results.getJSONObject(0);
				String address = result.getString("formatted_address");
				ANDROID_HTTP_CLIENT.close();
				return address;
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ANDROID_HTTP_CLIENT.close();
		}
	}
	private class GetLatLng extends AsyncTask<String, Void, LatLng> {
		private final AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient
				.newInstance(GetLatLng.class.getName());

		@Override
		protected LatLng doInBackground(String... locations) {
			String googleMapUrl = "http://maps.googleapis.com/maps/api/directions/json?origin="
					+ locations[0]
					+ ", Vietnam"
					+ "&destination="
					+ locations[0] + ", Vietnam" + "&sensor=false";

			try {
				JSONObject googleMapResponse = new JSONObject(
						ANDROID_HTTP_CLIENT.execute(
								new HttpGet(googleMapUrl.replace(" ", "%20")),
								new BasicResponseHandler()));
				JSONArray results = (JSONArray) googleMapResponse.get("routes");
				JSONObject result = results.getJSONObject(0);
				JSONObject location = result.getJSONObject("bounds")
						.getJSONObject("northeast");
				double latitude = location.getDouble("lat");
				double longitude = location.getDouble("lng");
				ANDROID_HTTP_CLIENT.close();
				return new LatLng(latitude, longitude);
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(LatLng result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ANDROID_HTTP_CLIENT.close();
		}
	}
	private class GetPoints extends AsyncTask<String, Void, List<LatLng>> {
		long startTime;

		@Override
		protected List<LatLng> doInBackground(String... params) {
			JSONParser jParser = new JSONParser();
			String json = jParser.getJSONFromUrl(params[0]);
			GeocoderHelper helper = new GeocoderHelper();
			return helper.getPoints(json);
		}

		@Override
		protected void onPostExecute(List<LatLng> result) {
			super.onPostExecute(result);
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			startTime = System.nanoTime();
		}
	}
	private class GetTravelTime extends AsyncTask<String, Void, Integer> {
		private final AndroidHttpClient ANDROID_HTTP_CLIENT = AndroidHttpClient
				.newInstance(GetLatLng.class.getName());

		@Override
		protected Integer doInBackground(String... locations) {
			String googleMapUrl = "http://maps.googleapis.com/maps/api/directions/json?origin="
					+ locations[0]
					+ ", Vietnam"
					+ "&destination="
					+ locations[1] + ", Vietnam" + "&sensor=false";

			if (!locations[2].equals("") || !locations[3].equals("")) {
				googleMapUrl += "&waypoints=";
				String waypoints = "";
				if (p1 != null) {
					waypoints += locations[2];
				}
				if (p2 != null) {
					waypoints += "|";
					waypoints += "|" + locations[3];
				}
				try {
					waypoints = URLEncoder.encode(waypoints, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				googleMapUrl += waypoints;
			}

			try {
				JSONObject googleMapResponse = new JSONObject(
						ANDROID_HTTP_CLIENT.execute(
								new HttpGet(googleMapUrl.replace(" ", "%20")),
								new BasicResponseHandler()));
				JSONArray results = (JSONArray) googleMapResponse.get("routes");
				JSONObject result = results.getJSONObject(0);
				JSONObject distanceJSON = result.getJSONArray("legs")
						.getJSONObject(0).getJSONObject("distance");
				int distance = distanceJSON.getInt("value") / 1000;
				ANDROID_HTTP_CLIENT.close();
				return distance;
			} catch (Exception ignored) {
				ignored.printStackTrace();
			}
			return 0;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			ANDROID_HTTP_CLIENT.close();
		}
	}
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
			if (Integer.parseInt(response) > 0) {
				Toast.makeText(getActivity(), "Tạo mới thành công",
						Toast.LENGTH_SHORT).show();
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				SystemSuggest frag = new SystemSuggest();
				Bundle bundle = new Bundle();
				bundle.putString("routeID", response);
				String[] start = params.get(0).getValue()
						.replaceAll("(?i), Vietnam", "")
						.replaceAll("(?i), Viet Nam", "")
						.replaceAll("(?i), Việt Nam", "").split(",");
				String st = start[start.length - 1];
				String[] end = params.get(1).getValue()
						.replaceAll("(?i), Vietnam", "")
						.replaceAll("(?i), Viet Nam", "")
						.replaceAll("(?i), Việt Nam", "").split(",");
				String ed = end[end.length - 1];
				bundle.putString("route", st + " - " + ed);
				frag.setArguments(bundle);
				trs.replace(R.id.content_frame, frag);
				trs.addToBackStack(null);
				trs.commit();
			} else if (Integer.parseInt(response) == -1) {
				Toast.makeText(getActivity(), "Đã có một lộ trình trong khoảng thời gian này. Vui lòng thay đổi thời gian chạy.",
						Toast.LENGTH_SHORT).show();
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
			+ "Route/Create";
	CheckBox broken;
	Calendar cal = Calendar.getInstance();
	CheckBox check1;
	CheckBox check2;
	CheckBox check3;
	String create = "";
	MyDatePickerDialog dialog1 = null, dialog2 = null;
	MyTimePickerDialog dialog3 = null;
	AutoCompleteTextView end;
	PlacesAutoCompleteAdapter endAdapter;
	EditText endDate;
	private DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
		}
	};
	String endString = "";
	CheckBox flammable;
	CheckBox food;
	CheckBox frozen;
	TextView link;
	LocationManager locationManager;
	ArrayList<LatLng> locations;
	AutoCompleteTextView p1;
	PlacesAutoCompleteAdapter p1Adapter;
	String p1String = "";
	AutoCompleteTextView p2;
	PlacesAutoCompleteAdapter p2Adapter;
	String p2String = "";
	EditText payload;

	ArrayList<String> pos = new ArrayList<String>();

	TextView show;
	String sp1, sp2;

	Spinner spinner;

	AutoCompleteTextView start;

	PlacesAutoCompleteAdapter startAdapter;

	EditText startDate;

	EditText startHour;

	private TimePickerDialog.OnTimeSetListener startHourListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String hr = String.valueOf(hourOfDay);
			String min = String.valueOf(minute);
			if (hr.length() == 1) {
				hr = "0" + hr;
			}
			if (min.length() == 1) {
				min = "0" + min;
			}
			String sd = startDate.getText().toString() + " " + hr + ":" + min;
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm");
			Date sdd = new Date();
			try {
				sdd = format.parse(sd);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			startHour.setText(hr + ":" + min);
		}
	};

	private DatePickerDialog.OnDateSetListener startListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			startDate
					.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
			endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
			if (dialog2 != null) {
				dialog2.updateDate(dialog1.getDatePicker().getYear(), dialog1
						.getDatePicker().getMonth(), dialog1.getDatePicker()
						.getDayOfMonth());
			}
		}
	};

	String startPoint, endPoint, Point1, Point2, startD, endD, brk, flm, frz,
			fd, load, current;

	String startString = "";

	View v;

	TextWatcher watcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			startAdapter.getFilter().filter(s);
		}
	};

	TextWatcher watcher2 = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			endAdapter.getFilter().filter(s);
		}
	};

	TextWatcher watcher3 = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			p1Adapter.getFilter().filter(s);
		}
	};

	TextWatcher watcher4 = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			p2Adapter.getFilter().filter(s);
		}
	};

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
		MenuItem item = menu.add(Menu.NONE, R.id.action_new, 99,
				R.string.create2);
		item.setActionView(R.layout.actionbar_create);
		item.getActionView().setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Common common = new Common();
				startPoint = start.getText().toString();
				Point1 = p1.getText().toString();
				Point2 = p2.getText().toString();
				endPoint = end.getText().toString();
				// try {
				// if (!startPoint.equals("")) {
				// startPoint = new GetFullAddress().execute(startPoint)
				// .get();
				// }
				// if (!endPoint.equals("")) {
				// endPoint = new GetFullAddress().execute(endPoint).get();
				// }
				// if (!Point1.equals("")) {
				// Point1 = new GetFullAddress().execute(Point1).get();
				// }
				// if (!Point2.equals("")) {
				// Point2 = new GetFullAddress().execute(Point2).get();
				// }
				// } catch (InterruptedException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// } catch (ExecutionException e1) {
				// // TODO Auto-generated catch block
				// e1.printStackTrace();
				// }
				startD = startDate.getText().toString().replace("/", "-");
				startD = common.changeFormatDate(startD) + " "
						+ startHour.getText().toString();
				endD = endDate.getText().toString().replace("/", "-");
				endD = common.changeFormatDate(endD);
				frz = String.valueOf(frozen.isChecked());
				brk = String.valueOf(broken.isChecked());
				flm = String.valueOf(flammable.isChecked());
				fd = String.valueOf(food.isChecked());
				load = payload.getText().toString();
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				current = String.valueOf(calendar.get(Calendar.YEAR)) + "-"
						+ String.valueOf(calendar.get(Calendar.MONTH) + 1)
						+ "-"
						+ String.valueOf(calendar.get(Calendar.DAY_OF_MONTH))
						+ " " + String.valueOf(calendar.get(Calendar.HOUR))
						+ ":" + String.valueOf(calendar.get(Calendar.MINUTE))
						+ ":" + String.valueOf(calendar.get(Calendar.SECOND));
				if (startPoint.equals("")) {
					Toast.makeText(getActivity(),
							"Điểm bắt đầu không được để trống.",
							Toast.LENGTH_SHORT).show();
				} else if (startPoint.length() > 100) {
					Toast.makeText(
							getActivity(),
							"Điểm bắt đầu chỉ dài tối đa 100 ký tự. Vui lòng nhập lại.",
							Toast.LENGTH_SHORT).show();
				} else if (Point1.length() > 100) {
					Toast.makeText(
							getActivity(),
							"Điểm đi qua 1 chỉ dài tối đa 100 ký tự. Vui lòng nhập lại.",
							Toast.LENGTH_SHORT);
				} else if (Point2.length() > 100) {
					Toast.makeText(
							getActivity(),
							"Điểm đi qua 2 chỉ dài tối đa 100 ký tự. Vui lòng nhập lại.",
							Toast.LENGTH_SHORT).show();
				} else if (endPoint.equals("")) {
					Toast.makeText(getActivity(),
							"Điểm kết thúc không được để trống.",
							Toast.LENGTH_SHORT).show();
				} else if (endPoint.length() > 100) {
					Toast.makeText(
							getActivity(),
							"Điểm kết thúc chỉ dài tối đa 100 ký tự. Vui lòng nhập lại.",
							Toast.LENGTH_SHORT).show();
				} else
					try {
						if (new GetTravelTime().execute(startPoint, endPoint,
								Point1, Point2).get() <= 10) {
							Toast.makeText(getActivity(),
									"Độ dài lộ trình phải lớn hơn 10 km.",
									Toast.LENGTH_SHORT).show();
						} else if (startD.equals("")) {
							Toast.makeText(getActivity(),
									"Ngày bắt đầu không được để trống.",
									Toast.LENGTH_SHORT).show();
						} else if (startHour.getText().toString().equals("")) {
							Toast.makeText(getActivity(),
									"Giờ bắt đầu không được để trống.",
									Toast.LENGTH_SHORT).show();
						} else if (endD.equals("")) {
							Toast.makeText(getActivity(),
									"Ngày kết thúc không được để trống.",
									Toast.LENGTH_SHORT).show();
						} else
							try {
								if (formatter.parse(
										startDate.getText().toString())
										.compareTo(
												formatter.parse(endDate
														.getText().toString())) > 0) {
									Toast.makeText(
											getActivity(),
											"Ngày kết thúc không được sớm hơn ngày bắt đầu",
											Toast.LENGTH_SHORT).show();
								} else if (load.equals("")) {
									Toast.makeText(
											getActivity(),
											"Khối lượng chở không được để trống.",
											Toast.LENGTH_SHORT).show();
								} else if (Integer.parseInt(load) > 20000) {
									Toast.makeText(
											getActivity(),
											"Khối lượng chở không vượt quá 20 tấn",
											Toast.LENGTH_SHORT).show();
								} else {
									if (create.equals("")) {
										if (Point1.equals("")
												&& Point2.equals("")) {
											GeocoderHelper helper = new GeocoderHelper(
													getActivity());
											ArrayList<String> list = helper
													.getMiddlePoints(
															startPoint,
															endPoint);
											sp1 = list.get(0);
											sp2 = list.get(1);
											ConnectivityManager cm = (ConnectivityManager) getActivity()
													.getSystemService(
															Context.CONNECTIVITY_SERVICE);
											NetworkInfo ni = cm
													.getActiveNetworkInfo();
											if (ni == null) {
												Toast.makeText(
														getActivity()
																.getBaseContext(),
														"Để tùy chỉnh bằng bản đồ vui lòng bật internet",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												if (sp1.equals(sp2)) {
													if (!sp1.equals(startPoint)
															&& !sp1.equals(endPoint)) {
														p1.setText(sp1);
													}
												} else {
													if (!sp1.equals(startPoint)
															&& !sp1.equals(endPoint)) {
														p1.setText(sp1);
													}
													if (!sp2.equals(startPoint)
															&& !sp2.equals(endPoint)) {
														if (p1.getText()
																.equals("")) {
															p1.setText(sp2);
														} else {
															p2.setText(sp2);
														}
													}
												}
												show.setVisibility(View.GONE);
												p1.setVisibility(View.VISIBLE);
												p2.setVisibility(View.VISIBLE);
												Intent intent = getActivity()
														.getIntent();

												intent.putExtra("start", start
														.getText().toString());
												intent.putExtra("p1", p1
														.getText().toString());
												intent.putExtra("p2", p2
														.getText().toString());
												intent.putExtra("end", end
														.getText().toString());

												intent.putExtra("sender",
														"createRoute");
												FragmentManager mng = getActivity()
														.getSupportFragmentManager();
												FragmentTransaction trs = mng
														.beginTransaction();
												CustomizeRoute frag1 = new CustomizeRoute();
												trs.replace(R.id.content_frame,
														frag1, "customizeRoute");
												trs.addToBackStack("customizeRoute");
												trs.commit();
											}
										} else {
											if (Point1.equals("")) {
												Toast.makeText(
														getActivity(),
														"Địa điểm đi qua 1 không được để trống",
														Toast.LENGTH_SHORT)
														.show();
											} else if (Point2.equals("")) {
												Toast.makeText(
														getActivity(),
														"Địa điểm đi qua 2 không được để trống",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												WebService ws = new WebService(
														WebService.POST_TASK,
														getActivity(),
														"Đang xử lý ...");
												ws.addNameValuePair(
														"startingAddress",
														startPoint);
												ws.addNameValuePair(
														"destinationAddress",
														endPoint);
												ws.addNameValuePair(
														"routeMarkerLocation1",
														Point1);
												ws.addNameValuePair(
														"routeMarkerLocation2",
														Point2);
												ws.addNameValuePair(
														"startTime", startD);
												ws.addNameValuePair(
														"finishTime", endD);
												ws.addNameValuePair("notes",
														null);
												if (spinner.getSelectedItem()
														.toString()
														.equals("kg")) {
													ws.addNameValuePair(
															"weight", load);
												} else {
													load = String.valueOf((Integer
															.parseInt(load) * 1000));
													ws.addNameValuePair(
															"weight", load);
												}
												ws.addNameValuePair(
														"createTime", current);
												ws.addNameValuePair("active",
														"1");
												ws.addNameValuePair(
														"driverID",
														getActivity()
																.getIntent()
																.getStringExtra(
																		"driverID"));
												ws.addNameValuePair("Food", fd);
												ws.addNameValuePair("Freeze",
														frz);
												ws.addNameValuePair("Broken",
														brk);
												ws.addNameValuePair("Flame",
														flm);
												ws.execute(new String[] { SERVICE_URL });
												create = "";
											}
										}
									} else {
										if (Point1.equals("")) {
											Toast.makeText(
													getActivity(),
													"Địa điểm đi qua 1 không được để trống",
													Toast.LENGTH_SHORT).show();
										} else if (Point2.equals("")) {
											Toast.makeText(
													getActivity(),
													"Địa điểm đi qua 2 không được để trống",
													Toast.LENGTH_SHORT).show();
										} else {
											WebService ws = new WebService(
													WebService.POST_TASK,
													getActivity(),
													"Đang xử lý ...");
											ws.addNameValuePair(
													"startingAddress",
													startPoint);
											ws.addNameValuePair(
													"destinationAddress",
													endPoint);
											ws.addNameValuePair(
													"routeMarkerLocation1",
													Point1);
											ws.addNameValuePair(
													"routeMarkerLocation2",
													Point2);
											ws.addNameValuePair("startTime",
													startD);
											ws.addNameValuePair("finishTime",
													endD);
											ws.addNameValuePair("notes", null);
											if (spinner.getSelectedItem()
													.toString().equals("kg")) {
												ws.addNameValuePair("weight",
														load);
											} else {
												load = String.valueOf((Integer
														.parseInt(load) * 1000));
												ws.addNameValuePair("weight",
														load);
											}
											ws.addNameValuePair("createTime",
													current);
											ws.addNameValuePair("active", "1");
											ws.addNameValuePair(
													"driverID",
													getActivity().getIntent()
															.getStringExtra(
																	"driverID"));
											ws.addNameValuePair("Food", fd);
											ws.addNameValuePair("Freeze", frz);
											ws.addNameValuePair("Broken", brk);
											ws.addNameValuePair("Flame", flm);
											ws.execute(new String[] { SERVICE_URL });
											create = "";
										}
									}
								}
							} catch (NumberFormatException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		item.setShowAsAction(MenuItem.SHOW_AS_ACTION_WITH_TEXT
				| MenuItem.SHOW_AS_ACTION_ALWAYS);
		item.setIcon(R.drawable.ic_action_accept);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().getActionBar().setNavigationMode(
				ActionBar.NAVIGATION_MODE_STANDARD);
		getActivity().getActionBar().setDisplayOptions(
				ActionBar.DISPLAY_SHOW_TITLE | ActionBar.DISPLAY_SHOW_HOME
						| ActionBar.DISPLAY_HOME_AS_UP);
		getActivity().getActionBar().setIcon(R.drawable.ic_action_place_white);
		getActivity().getActionBar().setTitle("Lộ trình mới");

		v = inflater.inflate(R.layout.activity_create_route, container, false);
		show = (TextView) v.findViewById(R.id.textView2);
		startDate = (EditText) v.findViewById(R.id.editText2);
		startHour = (EditText) v.findViewById(R.id.editText3);
		payload = (EditText) v.findViewById(R.id.editText8);
		cal = Calendar.getInstance();
		String date = String.valueOf(cal.get(Calendar.DAY_OF_MONTH) + "/"
				+ String.valueOf(cal.get(Calendar.MONTH) + 1) + "/"
				+ String.valueOf(cal.get(Calendar.YEAR)));
		startDate.setText(date);
		endDate = (EditText) v.findViewById(R.id.editText4);
		link = (TextView) v.findViewById(R.id.textView7);
		spinner = (Spinner) v.findViewById(R.id.spinner);

		List<String> list = new ArrayList<String>();
		list.add("kg");
		list.add("tấn");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list);
		spinner.setAdapter(dataAdapter);

		frozen = (CheckBox) v.findViewById(R.id.checkBox1);
		broken = (CheckBox) v.findViewById(R.id.checkBox2);
		flammable = (CheckBox) v.findViewById(R.id.checkBox3);
		food = (CheckBox) v.findViewById(R.id.checkBox4);

		startAdapter = new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.autocomplete_item);
		p1Adapter = new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.autocomplete_item);
		p2Adapter = new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.autocomplete_item);
		endAdapter = new PlacesAutoCompleteAdapter(getActivity(),
				R.layout.autocomplete_item);
		start = (AutoCompleteTextView) v.findViewById(R.id.start);
		p1 = (AutoCompleteTextView) v.findViewById(R.id.point1);
		p2 = (AutoCompleteTextView) v.findViewById(R.id.point2);
		end = (AutoCompleteTextView) v.findViewById(R.id.end);

		start.setAdapter(startAdapter);
		p1.setAdapter(p1Adapter);
		p2.setAdapter(p2Adapter);
		end.setAdapter(endAdapter);

		show.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				show.setVisibility(View.GONE);
				p1.setVisibility(View.VISIBLE);
				p2.setVisibility(View.VISIBLE);
			}
		});

		startDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dialog1 == null) {
					dialog1 = new MyDatePickerDialog(getActivity(),
							startListener, cal.get(Calendar.YEAR), cal
									.get(Calendar.MONTH), cal
									.get(Calendar.DATE), "Ngày bắt đầu");
					DatePicker picker = dialog1.getDatePicker();
					Calendar calendar = Calendar.getInstance();
					picker.setMinDate(calendar.getTimeInMillis() - 1000);
					calendar.add(Calendar.MONTH, 1);
					picker.setMaxDate(calendar.getTimeInMillis());
				}
				dialog1.show();
			}
		});

		startHour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Calendar cal = Calendar.getInstance();
				cal.add(Calendar.HOUR_OF_DAY, 6);
				if (dialog3 == null) {
					dialog3 = new MyTimePickerDialog(getActivity(),
							startHourListener, cal.get(Calendar.HOUR_OF_DAY),
							cal.get(Calendar.MINUTE), true, "Giờ bắt đầu");
					dialog3.setTitle("Giờ bắt đầu");
				}
				dialog3.show();
			}
		});

		endDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (dialog2 == null) {
					dialog2 = new MyDatePickerDialog(getActivity(),
							endListener, cal.get(Calendar.YEAR), cal
									.get(Calendar.MONTH), cal
									.get(Calendar.DATE), "Ngày kết thúc");
					DatePicker picker = dialog2.getDatePicker();
					Calendar calendar = Calendar.getInstance();
					picker.setMinDate(calendar.getTimeInMillis() - 1000);
					calendar.add(Calendar.MONTH, 1);
					picker.setMaxDate(calendar.getTimeInMillis());
				}
				dialog2.show();
			}
		});
		link.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ConnectivityManager cm = (ConnectivityManager) getActivity()
						.getSystemService(Context.CONNECTIVITY_SERVICE);
				NetworkInfo ni = cm.getActiveNetworkInfo();
				if (ni == null) {
					Toast.makeText(getActivity().getBaseContext(),
							"Để tùy chỉnh bằng bản đồ vui lòng bật internet",
							Toast.LENGTH_SHORT).show();
				} else if (start.getText().toString().equals("")
						|| end.getText().toString().equals("")) {
					Toast.makeText(
							getActivity().getBaseContext(),
							"Vui lòng nhập địa điểm bắt đầu và kết thúc trước khi tùy chỉnh",
							Toast.LENGTH_SHORT).show();
				} else if (p1.getText().toString().equals("")
						&& !p2.getText().toString().equals("")) {
					Toast.makeText(
							getActivity().getBaseContext(),
							"Vui lòng nhập đia điểm đi qua 1 trước khi nhập địa điểm đi qua 2",
							Toast.LENGTH_SHORT).show();
				} else
					try {
						if (new GetTravelTime().execute(
								start.getText().toString(),
								end.getText().toString(),
								p1.getText().toString(),
								p2.getText().toString()).get() < 10) {
							Toast.makeText(getActivity().getBaseContext(),
									"Độ dài lộ trình phải lớn hơn 10 km.",
									Toast.LENGTH_SHORT).show();
						} else {
							Intent intent = getActivity().getIntent();

							intent.putExtra("start", start.getText().toString());
							intent.putExtra("p1", p1.getText().toString());
							intent.putExtra("p2", p2.getText().toString());
							intent.putExtra("end", end.getText().toString());

							intent.putExtra("sender", "createRoute");
							FragmentManager mng = getActivity()
									.getSupportFragmentManager();
							FragmentTransaction trs = mng.beginTransaction();
							CustomizeRoute frag1 = new CustomizeRoute();

							trs.replace(R.id.content_frame, frag1);
							trs.addToBackStack(null);
							trs.commit();
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		return v;
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Fragment fragment = getActivity().getSupportFragmentManager()
				.findFragmentByTag("createRoute");
		if (fragment != null) {
			getActivity().getSupportFragmentManager()
					.findFragmentByTag("createRoute").setRetainInstance(true);
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Intent intent = getActivity().getIntent();
		locations = (ArrayList<LatLng>) intent
				.getSerializableExtra("markerList");
		if (locations == null || locations.size() == 0) {
			getActivity().getSupportFragmentManager()
					.findFragmentByTag("createRoute").getRetainInstance();
		} else {
			for (LatLng p : locations) {
				try {
					String address = new GetAddress().execute(p.latitude,
							p.longitude).get();
					pos.add(address);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (pos.size() == 3) {
				if (intent.getBooleanExtra("change", false) == true) {
					p1.setText(pos.get(1));
				}
				show.setVisibility(View.GONE);
				p1.setVisibility(View.VISIBLE);
				p2.setVisibility(View.VISIBLE);
			} else if (pos.size() == 4) {
				boolean change = getActivity().getIntent().getBooleanExtra(
						"change", false);
				if (change) {
					p1.setText(pos.get(1));
					p2.setText(pos.get(2));
				}
				show.setVisibility(View.GONE);
				p1.setVisibility(View.VISIBLE);
				p2.setVisibility(View.VISIBLE);
			}
		}
		intent.removeExtra("markerList");
		intent.removeExtra("change");
		pos.clear();
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		locationManager = (LocationManager) getActivity().getSystemService(
				Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				String current;
				try {
					current = new GetAddress().execute(location.getLatitude(),
							location.getLongitude()).get();
					if (start.getText().toString().equals("")) {
						start.setText(current);
					}
					locationManager.removeUpdates(this);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			@Override
			public void onProviderDisabled(String provider) {
			}

			@Override
			public void onProviderEnabled(String provider) {
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {
			}
		};
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, locationListener);
	}

	public void setString(String create) {
		this.create = create;
	}
}

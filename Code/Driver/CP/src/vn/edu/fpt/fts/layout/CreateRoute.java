package vn.edu.fpt.fts.layout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.json.JSONObject;

import vn.edu.fpt.fts.classes.Constant;
import vn.edu.fpt.fts.helper.Common;
import vn.edu.fpt.fts.helper.PlacesAutoCompleteAdapter;

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
import android.opengl.Visibility;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class CreateRoute extends Fragment {

	EditText startDate;
	EditText startHour;
	EditText endDate;
	AutoCompleteTextView start;
	AutoCompleteTextView p1;
	AutoCompleteTextView p2;
	AutoCompleteTextView end;
	PlacesAutoCompleteAdapter startAdapter;
	PlacesAutoCompleteAdapter p1Adapter;
	PlacesAutoCompleteAdapter p2Adapter;
	PlacesAutoCompleteAdapter endAdapter;
	TextView show;
	CheckBox check1;
	CheckBox check2;
	CheckBox check3;
	TextView link;
	CheckBox frozen;
	CheckBox broken;
	CheckBox flammable;
	CheckBox food;
	EditText payload;
	View v;
	ArrayList<LatLng> locations;
	String startString = "";
	String p1String = "";
	String p2String = "";
	String endString = "";
	ArrayList<String> pos = new ArrayList<String>();
	Calendar cal = Calendar.getInstance();
	LocationManager locationManager;
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "Route/Create";

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

			locationManager = (LocationManager) getActivity().getSystemService(
					Context.LOCATION_SERVICE);
			LocationListener locationListener = new LocationListener() {
				public void onLocationChanged(Location location) {
					String current;
					try {
						current = new GetAddress()
								.execute(location.getLatitude(),
										location.getLongitude()).get();
						start.setText(current);
						locationManager.removeUpdates(this);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				public void onStatusChanged(String provider, int status,
						Bundle extras) {
				}

				public void onProviderEnabled(String provider) {
				}

				public void onProviderDisabled(String provider) {
				}
			};
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, locationListener);

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
			start.setText(pos.get(0));
			end.setText(pos.get(pos.size() - 1));
			if (pos.size() == 3) {
				p1.setText(pos.get(1));
			} else if (pos.size() == 4) {
				p1.setText(pos.get(1));
				p2.setText(pos.get(2));
			}
		}
		intent.removeExtra("markerList");
		pos.clear();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getActivity().getActionBar().setIcon(R.drawable.ic_action_place_white);
		getActivity().getActionBar().setTitle("Lộ trình mới");

		Fragment custom = getFragmentManager().findFragmentByTag(
				"customizeRoute");
		if (custom != null) {
			FragmentTransaction ft = getFragmentManager().beginTransaction();
			ft.remove(custom).commit();
		}

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
				DatePickerDialog dialog = new DatePickerDialog(getActivity(),
						startListener, cal.get(Calendar.YEAR), cal
								.get(Calendar.MONTH), cal.get(Calendar.DATE));
				DatePicker picker = dialog.getDatePicker();
				Calendar calendar = Calendar.getInstance();
				picker.setMinDate(calendar.getTimeInMillis() - 1000);
				calendar.add(Calendar.MONTH, 1);
				picker.setMaxDate(calendar.getTimeInMillis());
				dialog.show();
			}
		});

		startHour.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				cal.add(Calendar.HOUR_OF_DAY, 6);
				TimePickerDialog dialog = new TimePickerDialog(getActivity(),
						startHourListener, cal.get(Calendar.HOUR_OF_DAY), cal
								.get(Calendar.MINUTE), true);
				dialog.show();
			}
		});

		endDate.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				DatePickerDialog dialog = new DatePickerDialog(getActivity(),
						endListener, cal.get(Calendar.YEAR), cal
								.get(Calendar.MONTH), cal.get(Calendar.DATE));
				DatePicker picker = dialog.getDatePicker();
				Calendar calendar = Calendar.getInstance();
				picker.setMinDate(calendar.getTimeInMillis() - 1000);
				calendar.add(Calendar.MONTH, 1);
				picker.setMaxDate(calendar.getTimeInMillis());
				dialog.show();
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
					trs.replace(R.id.content_frame, frag1, "customizeRoute");
					trs.addToBackStack("customizeRoute");
					trs.commit();
				}
			}
		});
		return v;
	}

	private DatePickerDialog.OnDateSetListener startListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			startDate
					.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
		}
	};

	private TimePickerDialog.OnTimeSetListener startHourListener = new TimePickerDialog.OnTimeSetListener() {

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			String hr = String.valueOf(hourOfDay);
			String min = String.valueOf(minute);
			if(hr.length() == 1) {
				hr = "0" + hr;
			}
			if(min.length() == 1) {
				min = "0" + min;
			}
			startHour.setText(hr + ":" + min);
		}
	};

	private DatePickerDialog.OnDateSetListener endListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			endDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
		}
	};

	TextWatcher watcher = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			startAdapter.getFilter().filter(s);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

	TextWatcher watcher2 = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			endAdapter.getFilter().filter(s);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

	TextWatcher watcher3 = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			p1Adapter.getFilter().filter(s);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

	TextWatcher watcher4 = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
			p2Adapter.getFilter().filter(s);
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
		}
	};

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

	private class WebService extends AsyncTask<String, Integer, String> {

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
			pDlg.dismiss();
			if (Integer.parseInt(response) > 0) {
				Toast.makeText(getActivity(), "Tạo mới thành công",
						Toast.LENGTH_SHORT).show();
				FragmentManager mng = getActivity().getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				SystemSuggest frag = new SystemSuggest();
				Bundle bundle = new Bundle();
				bundle.putString("routeID", response);
				frag.setArguments(bundle);
				trs.replace(R.id.content_frame, frag);
				trs.addToBackStack(null);
				trs.commit();
			} else {
				Toast.makeText(getActivity(), "Tạo mới thất bại",
						Toast.LENGTH_SHORT).show();
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
				String startPoint = start.getText().toString();
				String Point1 = p1.getText().toString();
				String Point2 = p2.getText().toString();
				String endPoint = end.getText().toString();
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
				String startD = startDate.getText().toString()
						.replace("/", "-");
				startD = common.changeFormatDate(startD) + " "
						+ startHour.getText().toString();
				String endD = endDate.getText().toString().replace("/", "-");
				endD = common.changeFormatDate(endD);
				String frz = String.valueOf(frozen.isChecked());
				String brk = String.valueOf(broken.isChecked());
				String flm = String.valueOf(flammable.isChecked());
				String fd = String.valueOf(food.isChecked());
				String load = payload.getText().toString();
				Calendar calendar = Calendar.getInstance();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String current = String.valueOf(calendar.get(Calendar.YEAR))
						+ "-"
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
				} else if (startD.equals("")) {
					Toast.makeText(getActivity(),
							"Ngày bắt đầu không được để trống.",
							Toast.LENGTH_SHORT).show();
				} else if (endD.equals("")) {
					Toast.makeText(getActivity(),
							"Ngày kết thúc không được để trống.",
							Toast.LENGTH_SHORT).show();
				} else
					try {
						if (formatter.parse(startDate.getText().toString())
								.compareTo(
										formatter.parse(endDate.getText()
												.toString())) > 0) {
							Toast.makeText(
									getActivity(),
									"Ngày kết thúc không được sớm hơn ngày bắt đầu",
									Toast.LENGTH_SHORT).show();
						} else if (payload.equals("")) {
							Toast.makeText(getActivity(),
									"Khối lượng chở không được để trống.",
									Toast.LENGTH_SHORT).show();
						} else if (Integer.parseInt(load) > 20000) {
							Toast.makeText(getActivity(),
									"Khối lượng chở không vượt quá 20 tấn",
									Toast.LENGTH_SHORT).show();
						} else {
							WebService ws = new WebService(
									WebService.POST_TASK, getActivity(),
									"Đang xử lý ...");
							ws.addNameValuePair("startingAddress", startPoint);
							ws.addNameValuePair("destinationAddress", endPoint);
							ws.addNameValuePair("routeMarkerLocation1", Point1);
							ws.addNameValuePair("routeMarkerLocation2", Point2);
							ws.addNameValuePair("startTime", startD);
							ws.addNameValuePair("finishTime", endD);
							ws.addNameValuePair("notes", null);
							ws.addNameValuePair("weight", load);
							ws.addNameValuePair("createTime", current);
							ws.addNameValuePair("active", "1");
							ws.addNameValuePair("driverID", getActivity()
									.getIntent().getStringExtra("driverID"));
							ws.addNameValuePair("Food", fd);
							ws.addNameValuePair("Freeze", frz);
							ws.addNameValuePair("Broken", brk);
							ws.addNameValuePair("Flame", flm);
							ws.execute(new String[] { SERVICE_URL });
						}
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ParseException e) {
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
}

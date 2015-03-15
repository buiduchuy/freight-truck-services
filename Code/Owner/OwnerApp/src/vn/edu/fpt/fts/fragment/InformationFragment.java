package vn.edu.fpt.fts.fragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
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

import vn.edu.fpt.fts.adapter.PlacesAutoCompleteAdapter;
import vn.edu.fpt.fts.common.Common;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

public class InformationFragment extends Fragment {
	private Spinner spinner;
	private ArrayAdapter<String> dataAdapter;
	private Calendar calendar1, calendar2, currentTime;
	private DatePickerDialog.OnDateSetListener date1, date2;
	private EditText etPickupDate, etDeliverDate, etNotes, etPrice, etWeight;
	private AutoCompleteTextView actPickupAddr, actDeliverAddr;
	private ImageButton ibPickupMap, ibDeliverMap;
	private Button btnPost;
	private List<String> cateList = new ArrayList<String>();
	private int cateId, spinnerPos;
	private Double pickupLat = 0.0, deliverLat = 0.0, pickupLng = 0.0,
			deliverLng = 0.0;
	private String ownerid, goodsID;

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
//		task2.execute(new String[] { url1 });
		task2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[] {url1});

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
				updateLabel(etPickupDate, calendar1);
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
				updateLabel(etDeliverDate, calendar2);
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
					DatePickerDialog dialog = new DatePickerDialog(
							getActivity(), date1, calendar1.get(Calendar.YEAR),
							calendar1.get(Calendar.MONTH), calendar1
									.get(Calendar.DAY_OF_MONTH));
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
							DatePickerDialog dialog = new DatePickerDialog(
									getActivity(), date2, calendar2
											.get(Calendar.YEAR), calendar2
											.get(Calendar.MONTH), calendar2
											.get(Calendar.DAY_OF_MONTH));
							DatePicker picker = dialog.getDatePicker();
							Calendar cal = Calendar.getInstance();
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
		ibPickupMap = (ImageButton) rootView
				.findViewById(R.id.imagebtn_pickup_address);
		ibPickupMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						CreateGoodsMapFragment.class);
				intent.putExtra("flag", "pickup");
				intent.putExtra("address", actPickupAddr.getText().toString());
				startActivity(intent);
			}
		});

		// deliver address and map button
		actDeliverAddr = (AutoCompleteTextView) rootView
				.findViewById(R.id.edittext_deliver_address);
		ibDeliverMap = (ImageButton) rootView
				.findViewById(R.id.imagebtn_deliver_address);
		ibDeliverMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getActivity(),
						CreateGoodsMapFragment.class);
				intent.putExtra("flag", "delivery");
				intent.putExtra("address", actDeliverAddr.getText().toString());
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
				Geocoder geocoder = new Geocoder(getActivity());
				try {
					List<Address> list = geocoder.getFromLocationName(
							actPickupAddr.getText().toString(), 1);
					pickupLng = list.get(0).getLongitude();
					pickupLat = list.get(0).getLatitude();
					List<Address> list2 = geocoder.getFromLocationName(
							actDeliverAddr.getText().toString(), 1);
					deliverLng = list2.get(0).getLongitude();
					deliverLat = list2.get(0).getLatitude();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				postData(v);
			}
		});

		// lay longitude va latitude
		// Bundle bundle = getIntent().getBundleExtra("info");
		// if (bundle != null) {
		// String flag = bundle.getString("flag");
		// if (flag.equals("pickup")) {
		// pickupLat = bundle.getDouble("lat");
		// pickupLng = bundle.getDouble("lng");
		// } else if (flag.equals("delivery")) {
		// deliverLat = bundle.getDouble("lat");
		// deliverLng = bundle.getDouble("lng");
		// }
		// }

		// set owner id

		SharedPreferences preferences = getActivity().getSharedPreferences(
				"MyPrefs", Context.MODE_PRIVATE);
		ownerid = preferences.getString("ownerID", "");
		
		WebServiceTask3 wst3 = new WebServiceTask3(WebServiceTask3.POST_TASK,
				getActivity(), "Đang xử lý...");
		String url = Common.IP_URL + Common.Service_Goods_getGoodsByID;
		wst3.addNameValuePair("goodsID", goodsID);
//		wst3.execute(new String[] { url });
		wst3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[] {url});

		return rootView;
	}

	private void updateLabel(EditText et, Calendar calendar) {
		String format = "dd/MM/yy";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		et.setText(sdf.format(calendar.getTime()));
	}

	private String formatDate(Calendar calendar) {
		String format = "yyyy-MM-dd HH:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		return sdf.format(calendar.getTime());
	}

	// ------------------------------------------------------------------------------

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
		wst.addNameValuePair("createTime", formatDate(currentTime));
		wst.addNameValuePair("deliveryAddress", actDeliverAddr.getText()
				.toString());
		wst.addNameValuePair("deliveryMarkerLatidute",
				Double.toString(deliverLat));
		wst.addNameValuePair("deliveryMarkerLongtitude",
				Double.toString(deliverLng));
		wst.addNameValuePair("deliveryTime", formatDate(calendar2));
		wst.addNameValuePair("goodsCategoryID", Integer.toString(cateId));
		wst.addNameValuePair("notes", etNotes.getText().toString());
		wst.addNameValuePair("ownerID", ownerid);
		wst.addNameValuePair("pickupAddress", actPickupAddr.getText()
				.toString());
		wst.addNameValuePair("pickupMarkerLatidute", Double.toString(pickupLat));
		wst.addNameValuePair("pickupMarkerLongtitude",
				Double.toString(pickupLng));
		wst.addNameValuePair("pickupTime", formatDate(calendar1));
		wst.addNameValuePair("price", etPrice.getText().toString());
		wst.addNameValuePair("weight", etWeight.getText().toString());

		// the passed String is the URL we will POST to
		String url = Common.IP_URL + Common.Service_Goods_Update;
//		wst.execute(new String[] { url });
		wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new String[] {url});

	}

	// public void handleResponse(String response) {
	// Ham xu li du lieu khi web server response
	// EditText edFirstName = (EditText) findViewById(R.id.first_name);
	// EditText edLastName = (EditText) findViewById(R.id.last_name);
	// EditText edEmail = (EditText) findViewById(R.id.email);
	//
	// edFirstName.setText("");
	// edLastName.setText("");
	// edEmail.setText("");
	//
	// try {
	//
	// JSONObject jso = new JSONObject(response);
	//
	// String firstName = jso.getString("firstName");
	// String lastName = jso.getString("lastName");
	// String email = jso.getString("email");
	//
	// edFirstName.setText(firstName);
	// edLastName.setText(lastName);
	// edEmail.setText(email);
	//
	// } catch (Exception e) {
	// Log.e(TAG, e.getLocalizedMessage(), e);
	// }
	//
	// }

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
			if (response.equals("-1")) {
				Toast.makeText(getActivity(), "Hàng chưa được cập nhật",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getActivity(), "Hàng cập nhật thành công",
						Toast.LENGTH_LONG).show();
				getActivity().finish();
				getActivity().startActivity(getActivity().getIntent());
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
				String deliverDate = jsonObject.getString("deliveryTime");
				String pickupDate = jsonObject.getString("pickupTime");
				String[] tmp = deliverDate.split(" ");
				String[] tmp1 = pickupDate.split(" ");
				Date deliver = new Date();
				Date pickup = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				try {
					deliver = sdf.parse(tmp[0]);
					pickup = sdf.parse(tmp1[0]);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				sdf = new SimpleDateFormat("dd/MM/yyyy");
				deliverDate = sdf.format(deliver);
				pickupDate = sdf.format(pickup);
				
				etDeliverDate.setText(deliverDate);
				etNotes.setText(jsonObject.getString("notes"));
				etPickupDate.setText(pickupDate);
				etPrice.setText(jsonObject.getString("price").replace(".0", ""));
				etWeight.setText(jsonObject.getString("weight"));
				actPickupAddr.setText(jsonObject.getString("pickupAddress"));
				actDeliverAddr.setText(jsonObject.getString("deliveryAddress"));
				
				int cate = Integer.parseInt(jsonObject.getString("goodsCategoryID"));
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

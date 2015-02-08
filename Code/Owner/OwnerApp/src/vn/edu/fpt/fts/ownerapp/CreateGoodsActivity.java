package vn.edu.fpt.fts.ownerapp;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.ownerapp.R;
import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;

import vn.edu.fpt.fts.adapter.PlacesAutoCompleteAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.style.UpdateLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;

public class CreateGoodsActivity extends Activity {

	private static final String SERVICE_URL = "http://192.168.1.6:8080/FTS/api/Goods/Create";

	private static final String TAG = "CreateGoodsActivity";

	private Spinner spinner;
	private Calendar calendar;
	private DatePickerDialog.OnDateSetListener date1, date2;
	private EditText etPickupDate, etDeliverDate;
	private AutoCompleteTextView edittextPickupAddr, edittextDeliverAddr;
	private ImageButton ibPickupMap, ibDeliverMap;
	private int mHour, mMinute;
	private Button btnPost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_goods);

		// drop down list
		spinner = (Spinner) findViewById(R.id.spinner_goods_type);
		List<String> list = new ArrayList<String>();
		list.add("Thức ăn");
		list.add("Bàn ghế");
		list.add("Gia dụng");
		list.add("Sắt thép");
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_dropdown_item, list);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(dataAdapter);

		// date picker listener
		calendar = Calendar.getInstance();
		date1 = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateLabel(etPickupDate);
			}
		};
		date2 = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateLabel(etDeliverDate);
			}
		};

		// date picker: pickup date
		etPickupDate = (EditText) findViewById(R.id.edittext_pickup_date);
		etPickupDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					new DatePickerDialog(CreateGoodsActivity.this, date1,
							calendar.get(Calendar.YEAR), calendar
									.get(Calendar.MONTH), calendar
									.get(Calendar.DAY_OF_MONTH)).show();
				}
			}
		});

		// date picker: deliver date
		etDeliverDate = (EditText) findViewById(R.id.edittext_deliver_date);
		etDeliverDate
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (hasFocus) {
							new DatePickerDialog(CreateGoodsActivity.this,
									date2, calendar.get(Calendar.YEAR),
									calendar.get(Calendar.MONTH), calendar
											.get(Calendar.DAY_OF_MONTH)).show();
						}
					}
				});

		// pickup address and map button
		edittextPickupAddr = (AutoCompleteTextView) findViewById(R.id.edittext_pickup_address);
		ibPickupMap = (ImageButton) findViewById(R.id.imagebtn_pickup_address);
		ibPickupMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CreateGoodsActivity.this,
						CreateGoodsMapFragment.class);
				intent.putExtra("address", edittextPickupAddr.getText()
						.toString());
				startActivity(intent);
			}
		});

		// deliver address and map button
		edittextDeliverAddr = (AutoCompleteTextView) findViewById(R.id.edittext_deliver_address);
		ibDeliverMap = (ImageButton) findViewById(R.id.imagebtn_deliver_address);
		ibDeliverMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CreateGoodsActivity.this,
						CreateGoodsMapFragment.class);
				intent.putExtra("address", edittextDeliverAddr.getText()
						.toString());
				startActivity(intent);
			}
		});

		// auto complete textview
		edittextPickupAddr.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.list_item_pickup));
		edittextDeliverAddr.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.list_item_deliver));

		// button
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		btnPost = (Button) findViewById(R.id.btn_post);
		btnPost.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// JSONObject json = new JSONObject();
				// JSONObject json1 = new JSONObject();
				// JSONArray ja = new JSONArray();
				// try {
				// json.put("active", "1");
				// json.put("createTime", "2015-05-04 00:00:00.0");
				// json.put("deliveryAddress", "Vung Tau");
				// json.put("deliveryMarkerLatidute", "0.0");
				// json.put("deliveryMarkerLongtitude", "0.0");
				// json.put("deliveryTime", "2015-05-24 00:00:00.0");
				// json.put("goodsCategoryID", "1");
				// json.put("notes", "12345");
				// json.put("ownerID", "1");
				// json.put("pickupAddress", "Ca Mau");
				// json.put("pickupMarkerLatidute", "0.0");
				// json.put("pickupMarkerLongtitude", "0.0");
				// json.put("pickupTime", "2015-05-04 00:00:00.0");
				// json.put("price", "3000");
				// json.put("weight", "1000");
				// ja.put(json);
				// json1.put("goods", ja);
				//
				// } catch (JSONException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// }
				// HttpClient httpClient = new DefaultHttpClient();
				// HttpPost request = new HttpPost(
				// "http://192.168.1.16:8080/FTS/api/Goods/Create");
				// try {
				// StringEntity param = new StringEntity(json1.toString());
				// request.addHeader("content-type", "application/json");
				// request.setEntity(param);
				// httpClient.execute(request);
				// } catch (UnsupportedEncodingException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (ClientProtocolException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } catch (IOException e) {
				// // TODO Auto-generated catch block
				// e.printStackTrace();
				// } finally {
				// httpClient.getConnectionManager().shutdown();
				// }
				
				//Chi viec goi ham postData
				postData(v);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_goods, menu);
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
		return super.onOptionsItemSelected(item);
	}

	private void updateLabel(EditText et) {
		String format = "MM/dd/yy";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		et.setText(sdf.format(calendar.getTime()));
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

		WebServiceTask wst = new WebServiceTask(WebServiceTask.POST_TASK, this,
				"Đang xử lý...");
		// Cac cap gia tri gui ve server
		wst.addNameValuePair("active", "1");
		wst.addNameValuePair("createTime", "2015-05-04 00:00:00.0");
		wst.addNameValuePair("deliveryAddress", "Vung Tau");
		wst.addNameValuePair("deliveryMarkerLatidute", "0.0");
		wst.addNameValuePair("deliveryMarkerLongtitude", "0.0");
		wst.addNameValuePair("deliveryTime", "2015-05-24 00:00:00.0");
		wst.addNameValuePair("goodsCategoryID", "1");
		wst.addNameValuePair("notes", "12345");
		wst.addNameValuePair("ownerID", "1");
		wst.addNameValuePair("pickupAddress", "Ca Mau");
		wst.addNameValuePair("pickupMarkerLatidute", "0.0");
		wst.addNameValuePair("pickupMarkerLongtitude", "0.0");
		wst.addNameValuePair("pickupTime", "2015-05-04 00:00:00.0");
		wst.addNameValuePair("price", "3000");
		wst.addNameValuePair("weight", "1000");

		// the passed String is the URL we will POST to
		wst.execute(new String[] { SERVICE_URL });

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

	private void hideKeyboard() {

		InputMethodManager inputManager = (InputMethodManager) CreateGoodsActivity.this
				.getSystemService(Context.INPUT_METHOD_SERVICE);

		inputManager.hideSoftInputFromWindow(CreateGoodsActivity.this
				.getCurrentFocus().getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);
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

			hideKeyboard();
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

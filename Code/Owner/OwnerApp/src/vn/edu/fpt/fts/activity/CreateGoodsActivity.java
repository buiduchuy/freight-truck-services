package vn.edu.fpt.fts.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
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

import vn.edu.fpt.fts.adapter.PlacesAutoCompleteAdapter;
import vn.edu.fpt.fts.classes.ConfirmCreateDialog;
import vn.edu.fpt.fts.classes.Goods;
import vn.edu.fpt.fts.common.Common;
import vn.edu.fpt.fts.common.GeocoderHelper;
import vn.edu.fpt.fts.fragment.CreateGoodsMapFragment;
import vn.edu.fpt.fts.fragment.R;
import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateGoodsActivity extends FragmentActivity {

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
	private String ownerid, errorMsg = "", selected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_goods);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);

		etNotes = (EditText) findViewById(R.id.edittext_note);
		etPrice = (EditText) findViewById(R.id.edittext_price);
		etWeight = (EditText) findViewById(R.id.edittext_weight);

		// drop down list
		WebServiceTask2 task2 = new WebServiceTask2(WebServiceTask2.GET_TASK,
				this, "Đang xử lý...");
		String url = Common.IP_URL + Common.Service_GoodsCategory_Get;
		// task2.execute(new String[] { url });
		task2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });

		spinner = (Spinner) findViewById(R.id.spinner_goods_type);
		dataAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, cateList);
		dataAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner.setAdapter(dataAdapter);
		spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				selected = parent.getItemAtPosition(position).toString();
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
		etPickupDate = (EditText) findViewById(R.id.edittext_pickup_date);
		etPickupDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					// DatePickerDialog dialog = new DatePickerDialog(
					// CreateGoodsActivity.this, date1, calendar1
					// .get(Calendar.YEAR), calendar1
					// .get(Calendar.MONTH), calendar1
					// .get(Calendar.DAY_OF_MONTH));
					MyDatePickerDialog dialog = new MyDatePickerDialog(
							CreateGoodsActivity.this, date1, calendar1
									.get(Calendar.YEAR), calendar1
									.get(Calendar.MONTH), calendar1
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
		etDeliverDate = (EditText) findViewById(R.id.edittext_deliver_date);
		etDeliverDate
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						// TODO Auto-generated method stub
						if (hasFocus) {
							// DatePickerDialog dialog = new DatePickerDialog(
							// CreateGoodsActivity.this, date2, calendar2
							// .get(Calendar.YEAR), calendar2
							// .get(Calendar.MONTH), calendar2
							// .get(Calendar.DAY_OF_MONTH));
							MyDatePickerDialog dialog = new MyDatePickerDialog(
									CreateGoodsActivity.this, date2, calendar2
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
							// Calendar cal = calendar1;
							picker.setMinDate(cal.getTimeInMillis() - 1000);
							cal.add(Calendar.MONTH, 1);
							picker.setMaxDate(cal.getTimeInMillis());
							dialog.show();
						}
					}
				});

		// pickup address and map button
		actPickupAddr = (AutoCompleteTextView) findViewById(R.id.edittext_pickup_address);
		ibPickupMap = (ImageButton) findViewById(R.id.imagebtn_pickup_address);
		ibPickupMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CreateGoodsActivity.this,
						CreateGoodsMapFragment.class);
				intent.putExtra("flag", "pickup");
				intent.putExtra("address", actPickupAddr.getText().toString());
				intent.putExtra("lat", pickupLat);
				intent.putExtra("long", pickupLng);
				startActivity(intent);
			}
		});

		// deliver address and map button
		actDeliverAddr = (AutoCompleteTextView) findViewById(R.id.edittext_deliver_address);
		ibDeliverMap = (ImageButton) findViewById(R.id.imagebtn_deliver_address);
		ibDeliverMap.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(CreateGoodsActivity.this,
						CreateGoodsMapFragment.class);
				intent.putExtra("flag", "delivery");
				intent.putExtra("address", actDeliverAddr.getText().toString());
				intent.putExtra("lat", deliverLat);
				intent.putExtra("long", deliverLng);
				startActivity(intent);
			}
		});

		// auto complete textview
		actPickupAddr.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.list_item_pickup));
		actDeliverAddr.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.list_item_deliver));

		// button
		btnPost = (Button) findViewById(R.id.btn_post);
		btnPost.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// Chi viec goi ham postData
				if (validate()) {
					Goods goods = new Goods();
					goods.setGoodsCategory(selected);
					goods.setPickupTime(etPickupDate.getText().toString());
					goods.setDeliveryTime(etDeliverDate.getText().toString());
					goods.setPickupAddress(actPickupAddr.getText().toString());
					goods.setDeliveryAddress(actDeliverAddr.getText()
							.toString());
					goods.setWeight(Integer.parseInt(etWeight.getText()
							.toString()));
					goods.setPrice(Double.parseDouble(etPrice.getText()
							.toString()));
					goods.setNotes(etNotes.getText().toString());
					DialogFragment dialog = ConfirmCreateDialog
							.newInstance(goods);
					dialog.show(getSupportFragmentManager(), "");

					// postData();
				} else {
					Toast.makeText(CreateGoodsActivity.this, errorMsg,
							Toast.LENGTH_LONG).show();
				}
			}
		});

		// lay longitude va latitude
		Bundle bundle = getIntent().getBundleExtra("info");
		if (bundle != null) {
			String flag = bundle.getString("flag");
			if (flag.equals("pickup")) {
				pickupLat = bundle.getDouble("lat");
				pickupLng = bundle.getDouble("lng");
			} else if (flag.equals("delivery")) {
				deliverLat = bundle.getDouble("lat");
				deliverLng = bundle.getDouble("lng");
			}
		}

		// set owner id
		SharedPreferences preferences = getSharedPreferences("MyPrefs",
				Context.MODE_PRIVATE);
		ownerid = preferences.getString("ownerID", "");

		// if (savedInstanceState != null) {
		// spinner.setSelection(savedInstanceState.getInt("spinner"));
		// etDeliverDate.setText(savedInstanceState.getString("deliverDate"));
		// etNotes.setText(savedInstanceState.getString("note"));
		// etPickupDate.setText(savedInstanceState.getString("pickupDate"));
		// etPrice.setText(savedInstanceState.getString("price"));
		// etWeight.setText(savedInstanceState.getString("weight"));
		// actPickupAddr.setText(savedInstanceState.getString("pickup"));
		// actDeliverAddr.setText(savedInstanceState.getString("deliver"));
		// }
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putInt("spinner", spinnerPos);
		outState.putString("weight", etWeight.getText().toString());
		outState.putString("pickupDate", etPickupDate.getText().toString());
		outState.putString("deliverDate", etDeliverDate.getText().toString());
		outState.putString("price", etPrice.getText().toString());
		outState.putString("note", etNotes.getText().toString());
		outState.putString("pickup", actPickupAddr.getText().toString());
		outState.putString("deliver", actDeliverAddr.getText().toString());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		spinner.setSelection(savedInstanceState.getInt("spinner"));
		etDeliverDate.setText(savedInstanceState.getString("deliverDate"));
		etNotes.setText(savedInstanceState.getString("note"));
		etPickupDate.setText(savedInstanceState.getString("pickupDate"));
		etPrice.setText(savedInstanceState.getString("price"));
		etWeight.setText(savedInstanceState.getString("weight"));
		actPickupAddr.setText(savedInstanceState.getString("pickup"));
		actDeliverAddr.setText(savedInstanceState.getString("deliver"));

		Bundle bundle = getIntent().getBundleExtra("info");
		if (bundle != null) {
			String flag = bundle.getString("flag");
			if (flag.equals("pickup")) {
				pickupLat = bundle.getDouble("lat");
				pickupLng = bundle.getDouble("lng");
			} else if (flag.equals("delivery")) {
				deliverLat = bundle.getDouble("lat");
				deliverLng = bundle.getDouble("lng");
			}
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		SharedPreferences preferences = getSharedPreferences("MyPrefs",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();

		editor.putInt("spinner", spinnerPos);
		editor.putString("weight", etWeight.getText().toString());
		editor.putString("pickupDate", etPickupDate.getText().toString());
		editor.putString("deliverDate", etDeliverDate.getText().toString());
		editor.putString("price", etPrice.getText().toString());
		editor.putString("note", etNotes.getText().toString());
		editor.putString("pickup", actPickupAddr.getText().toString());
		editor.putString("deliver", actDeliverAddr.getText().toString());
		editor.commit();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		SharedPreferences preferences = getSharedPreferences("MyPrefs",
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = preferences.edit();
		int check = preferences.getInt("spinner", 0);
		if (!(check == 0)) {
			spinner.setSelection(preferences.getInt("spinner", 0));
			etDeliverDate.setText(preferences.getString("deliverDate", "sai roi"));
			etNotes.setText(preferences.getString("note", "sai roi"));
			etPickupDate.setText(preferences.getString("pickupDate", "sai roi"));
			etPrice.setText(preferences.getString("price", ""));
			etWeight.setText(preferences.getString("weight", ""));
			actPickupAddr.setText(preferences.getString("pickup", ""));
			actDeliverAddr.setText(preferences.getString("deliver", ""));
		}
			
		editor.remove("spinner");
		editor.remove("deliverDate");
		editor.remove("note");
		editor.remove("pickupDate");
		editor.remove("price");
		editor.remove("weight");
		editor.remove("pickup");
		editor.remove("deliver");

		Bundle bundle = getIntent().getBundleExtra("info");
		if (bundle != null) {
			String flag = bundle.getString("flag");
			if (flag.equals("pickup")) {
				pickupLat = bundle.getDouble("lat");
				pickupLng = bundle.getDouble("lng");
			} else if (flag.equals("delivery")) {
				deliverLat = bundle.getDouble("lat");
				deliverLng = bundle.getDouble("lng");
			}
		}
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
		if (id == R.id.action_history) {
			Intent intent = new Intent(CreateGoodsActivity.this,
					HistoryActivity.class);
			startActivity(intent);
		}
		if (id == android.R.id.home) {
			Intent intent = new Intent(CreateGoodsActivity.this,
					MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Intent intent = new Intent(CreateGoodsActivity.this,
					MainActivity.class);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public class MyDatePickerDialog extends DatePickerDialog {

		private CharSequence title;

		public MyDatePickerDialog(Context context, OnDateSetListener callBack,
				int year, int monthOfYear, int dayOfMonth) {
			super(context, callBack, year, monthOfYear, dayOfMonth);
		}

		public void setPermanentTitle(CharSequence title) {
			this.title = title;
			setTitle(title);
		}

		@Override
		public void onDateChanged(DatePicker view, int year, int month, int day) {
			super.onDateChanged(view, year, month, day);
			setTitle(title);
		}
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
		String a = Common.formatDate(calendar1);
		String b = Common.formatDate(calendar2);
		if (calendar1.compareTo(calendar2) >= 0) {
			check = false;
			errorMsg = "Ngày nhận hàng không được trễ hơn ngày giao hàng";
		}

		Geocoder geocoder = new Geocoder(getBaseContext());
		try {
			List<Address> list = geocoder.getFromLocationName(actPickupAddr
					.getText().toString(), 1);
			List<Address> list2 = geocoder.getFromLocationName(actDeliverAddr
					.getText().toString(), 1);
			if (list.size() > 0 && list2.size() > 0) {
				pickupLng = list.get(0).getLongitude();
				pickupLat = list.get(0).getLatitude();

				deliverLng = list2.get(0).getLongitude();
				deliverLat = list2.get(0).getLatitude();
			} else {
				errorMsg = "Địa chỉ không có thật";
				check = false;
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			check = false;
			errorMsg = "Địa chỉ không có thật";
		}

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

		LatLng pickup = new LatLng(pickupLat, pickupLng);
		LatLng deliver = new LatLng(deliverLat, deliverLng);
		String url = helper.makeURL(pickup, deliver);
		// if (!helper.checkPath(url)) {
		// check = false;
		// errorMsg = "Không thể tìm đường đi thích hợp cho địa chỉ đã nhập";
		// }

		return check;
	}

	// ------------------------------------------------------------------------------

	public void postData() {

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

		currentTime = Calendar.getInstance();
		wst.addNameValuePair("active", "1");
		wst.addNameValuePair("createTime", Common.formatDate(currentTime));
		wst.addNameValuePair("deliveryAddress", actDeliverAddr.getText()
				.toString());
		wst.addNameValuePair("deliveryMarkerLatidute",
				Double.toString(deliverLat));
		wst.addNameValuePair("deliveryMarkerLongtitude",
				Double.toString(deliverLng));
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
		String url = Common.IP_URL + Common.Service_Goods_Create;
		// wst.execute(new String[] { url });
		wst.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,
				new String[] { url });

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
				Toast.makeText(CreateGoodsActivity.this, "Hàng chưa được tạo",
						Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(CreateGoodsActivity.this, "Hàng tạo thành công",
						Toast.LENGTH_LONG).show();
				Intent intent = new Intent(CreateGoodsActivity.this,
						SuggestActivity.class);
				intent.putExtra("goodsID", response);
				intent.putExtra("price", etPrice.getText().toString());
				intent.putExtra("notes", etNotes.getText().toString());
				startActivity(intent);
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

	private class GetLocation extends AsyncTask<String, Void, JSONObject> {
		@Override
		protected JSONObject doInBackground(String... address) {
			// TODO Auto-generated method stub
			StringBuilder stringBuilder = new StringBuilder();
			try {

				address[0] = address[0].replaceAll(" ", "%20");

				HttpPost httppost = new HttpPost(
						"http://maps.google.com/maps/api/geocode/json?address="
								+ address + "&sensor=false");
				HttpClient client = new DefaultHttpClient();
				HttpResponse response;
				stringBuilder = new StringBuilder();

				response = client.execute(httppost);
				HttpEntity entity = response.getEntity();
				InputStream stream = entity.getContent();
				int b;
				while ((b = stream.read()) != -1) {
					stringBuilder.append((char) b);
				}
			} catch (ClientProtocolException e) {
				return null;
			} catch (IOException e) {
				return null;
			}

			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject = new JSONObject(stringBuilder.toString());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

			return jsonObject;
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

		}
	}
}

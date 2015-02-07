package com.example.ownerapp;

import java.io.Closeable;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.maps.LocationSource.OnLocationChangedListener;

import sample.tabsswipe.adapter.PlacesAutoCompleteAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.style.UpdateLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
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
									date2, calendar.get(Calendar.YEAR), calendar
											.get(Calendar.MONTH), calendar
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
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
	    StrictMode.setThreadPolicy(policy);
		btnPost = (Button)findViewById(R.id.btn_post);
		btnPost.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				JSONObject json = new JSONObject();
				JSONObject json1 = new JSONObject();
				try {
					json.put("active", "1");
					json.put("createTime", "2015-05-04 00:00:00.0");
					json.put("deliveryAddress", "Vung Tau");
					json.put("deliveryMarkerLatidute", "0.0");
					json.put("deliveryMarkerLongtitude", "0.0");
					json.put("deliveryTime", "2015-05-24 00:00:00.0");
					json.put("goodsCategoryID", "1");
					json.put("notes", "12345");
					json.put("ownerID", "1");
					json.put("pickupAddress", "Ca Mau");
					json.put("pickupMarkerLatidute", "0.0");
					json.put("pickupMarkerLongtitude", "0.0");
					json.put("pickupTime", "2015-05-04 00:00:00.0");
					json.put("price", "3000");
					json.put("weight", "1000");
					json1.put("goods", json);
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				HttpClient httpClient = new DefaultHttpClient();
				HttpPost request = new HttpPost("http://192.168.1.4:8080/FTS/api/Goods/create");
				try {
					StringEntity param = new StringEntity(json1.toString());
					request.addHeader("content-type", "application/x-www-form-urlencoded");
					request.setEntity(param);
					httpClient.execute(request);
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					httpClient.getConnectionManager().shutdown();
				}
				
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

}

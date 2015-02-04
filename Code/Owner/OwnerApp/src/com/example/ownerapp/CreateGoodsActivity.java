package com.example.ownerapp;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import sample.tabsswipe.adapter.PlacesAutoCompleteAdapter;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.UpdateLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TimePicker;

public class CreateGoodsActivity extends Activity {
	private Spinner spinner;
	private Calendar calendar;
	private DatePickerDialog.OnDateSetListener date;
	private EditText edittextDate, edittextTime;
	private AutoCompleteTextView edittextPickupAddr, edittextDeliverAddr;
	private ImageButton ibPickupMap, ibDeliverMap;
	private int mHour, mMinute;

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

		// date picker dialog
		calendar = Calendar.getInstance();
		date = new DatePickerDialog.OnDateSetListener() {

			@Override
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// TODO Auto-generated method stub
				calendar.set(Calendar.YEAR, year);
				calendar.set(Calendar.MONTH, monthOfYear);
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				updateLabel();
			}
		};

		edittextDate = (EditText) findViewById(R.id.edittext_pickup_date);
		edittextDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					new DatePickerDialog(CreateGoodsActivity.this, date,
							calendar.get(Calendar.YEAR), calendar
									.get(Calendar.MONTH), calendar
									.get(Calendar.DAY_OF_MONTH)).show();
				}
			}
		});

		edittextTime = (EditText) findViewById(R.id.edittext_pickup_time);
		edittextTime.setOnFocusChangeListener(new View.OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if (hasFocus) {
					mHour = calendar.get(Calendar.HOUR);
					mMinute = calendar.get(Calendar.MINUTE);
					TimePickerDialog tpd = new TimePickerDialog(
							CreateGoodsActivity.this,
							new TimePickerDialog.OnTimeSetListener() {

								@Override
								public void onTimeSet(TimePicker view,
										int hourOfDay, int minute) {
									// TODO Auto-generated method stub
									edittextTime.setText(hourOfDay + " : "
											+ minute);
								}
							}, mHour, mMinute, false);
					tpd.show();
				}
			}
		});
		edittextPickupAddr = (AutoCompleteTextView)
		findViewById(R.id.edittext_pickup_address);
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

		// auto complete textview
		AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.edittext_pickup_address);
		autoCompleteTextView.setAdapter(new PlacesAutoCompleteAdapter(this,
				R.layout.list_item_pickup));

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

	private void updateLabel() {
		String format = "MM/dd/yy";
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
		edittextDate.setText(sdf.format(calendar.getTime()));
	}

}

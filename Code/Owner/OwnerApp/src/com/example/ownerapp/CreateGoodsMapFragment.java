package com.example.ownerapp;

import java.io.IOException;
import java.util.List;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Interpolator;
import android.graphics.Point;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;

public class CreateGoodsMapFragment extends Activity {
	Marker checkMarker = null;
	private double mlong, mlat;
	private LatLng point;
	private GoogleMap map;
	private EditText etAddress;
	private Button btFind;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_create_goods_fragment);

		etAddress = (EditText) findViewById(R.id.edittext_address);
		btFind = (Button) findViewById(R.id.button_find);

		String address = "144/14/1 trần bình trọng phường 1 quận 1 gò vấp hồ chí minh";

		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		map = mapFragment.getMap();

		btFind.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Geocoder geocoder = new Geocoder(getBaseContext());
				try {
					String add = etAddress.getText().toString();
					List<Address> list = geocoder.getFromLocationName(
							add, 1);
					mlong = list.get(0).getLongitude();
					mlat = list.get(0).getLatitude();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				MarkerOptions mMarker = new MarkerOptions();
				point = new LatLng(mlat, mlong);
				mMarker.position(point);
				map.addMarker(mMarker);
			}
		});

		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

			@Override
			public void onMapClick(LatLng arg0) {
				// TODO Auto-generated method stub
				MarkerOptions marker = new MarkerOptions();
				marker.position(arg0);
				if (checkMarker == null) {
					checkMarker = map.addMarker(marker);
				} else {
					map.clear();
					checkMarker = null;
					checkMarker = map.addMarker(marker);
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_goods_map, menu);
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

	private void moveMapToMyLocation() {
		LocationManager locMan = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

		Criteria crit = new Criteria();

		Location loc = locMan.getLastKnownLocation(locMan.getBestProvider(crit,
				false));

		CameraPosition camPos = new CameraPosition.Builder()

		.target(new LatLng(loc.getLatitude(), loc.getLongitude()))

		.zoom(12.8f)

		.build();

		CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(camPos);

		map.moveCamera(camUpdate);
	}
}

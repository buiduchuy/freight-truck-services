package vn.edu.fpt.fts.ownerapp;

import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class CreateGoodsMapFragment extends Activity {	
	private double mlong, mlat;
	private LatLng point;
	private GoogleMap map;	
	private Button btn_accept;
	private String flag;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_create_goods_fragment);

		String address = getIntent().getStringExtra("address");
		flag = getIntent().getStringExtra("flag");
		
		// khoi tao map
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.map);
		map = mapFragment.getMap();
		
		// tao marker tren map
		Geocoder geocoder = new Geocoder(getBaseContext());
		try {			
			List<Address> list = geocoder.getFromLocationName(
					address, 1);
			mlong = list.get(0).getLongitude();
			mlat = list.get(0).getLatitude();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		MarkerOptions mMarker = new MarkerOptions();
		point = new LatLng(mlat, mlong);
		mMarker.position(point);
		mMarker.draggable(true);
		map.addMarker(mMarker);

		//zoom to marker	
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 18));
		
		btn_accept = (Button)findViewById(R.id.button_accept);
		btn_accept.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putString("flag", flag);
				bundle.putDouble("lat", mlat);
				bundle.putDouble("lng", mlong);				
				Intent intent = new Intent(CreateGoodsMapFragment.this, CreateGoodsActivity.class);
				intent.putExtra("info", bundle);
				startActivity(intent);
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

	
}

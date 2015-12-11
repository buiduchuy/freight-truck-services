package vn.edu.fpt.fts.fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class EditGoodsMapActivity extends Activity {	
	private GoogleMap map;
	private LatLng point;	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_goods_map);
		ActionBar actionBar = getActionBar();
		actionBar.setHomeButtonEnabled(true);
		MapFragment mapFragment = (MapFragment) getFragmentManager()
				.findFragmentById(R.id.goodsMap);
		map = mapFragment.getMap();
		point = getIntent().getParcelableExtra("marker");
		MarkerOptions mMarker = new MarkerOptions();
		mMarker.icon(BitmapDescriptorFactory.fromResource(R.drawable.owner_marker_icon_small));		
		mMarker.position(point);
		mMarker.draggable(true);
		map.addMarker(mMarker);
		//zoom to marker	
		map.moveCamera(CameraUpdateFactory.newLatLngZoom(point, 14));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_goods_map, menu);
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

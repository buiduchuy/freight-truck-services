package vn.edu.fpt.fts.layout;

import java.util.ArrayList;

import vn.edu.fpt.fts.drawer.NavDrawerAdapter;
import vn.edu.fpt.fts.drawer.NavDrawerItem;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends FragmentActivity {

	private String[] mNavigationDrawerItemTitles;
	private TypedArray mNavigationImage;
	protected DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	private NavDrawerAdapter adapter;
	private ArrayList<NavDrawerItem> array = new ArrayList<NavDrawerItem>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Dịch vụ xe tải");
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		  StrictMode.setThreadPolicy(policy);

		mNavigationDrawerItemTitles = getResources().getStringArray(
				R.array.navigation_drawer_items_array);
		mNavigationImage = getResources().obtainTypedArray(
				R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		for (int i = 0; i < mNavigationDrawerItemTitles.length; i++) {
			array.add(new NavDrawerItem(mNavigationDrawerItemTitles[i],
					mNavigationImage.getResourceId(i, -1)));
		}

		mNavigationImage.recycle();

		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		adapter = new NavDrawerAdapter(getApplicationContext(), array);

		mDrawerList.setAdapter(adapter);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.hello_world,
				R.string.hello_world) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);
		FragmentManager mng = getSupportFragmentManager();
		FragmentTransaction trs = mng.beginTransaction();
		RouteList frag1 = new RouteList();
		trs.replace(R.id.content_frame, frag1);
		trs.commit();

	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}

		private void selectItem(int position) {
			Fragment fragment = null;
			switch (position) {
			case 0:
				fragment = new RouteList();
				break;
			case 1:
				fragment = new TabDeals();
				break;
			case 2:
				fragment = new History();
				break;
			case 3:
				fragment = new SuggestList();
			default:
				break;
			}

			if (fragment != null) {
				FragmentManager fragmentManager = getSupportFragmentManager();
				FragmentTransaction transaction = fragmentManager
						.beginTransaction();
				transaction.replace(R.id.content_frame, fragment);
				transaction.addToBackStack(null);
				transaction.commit();
				mDrawerList.setItemChecked(position, true);
				mDrawerList.setSelection(position);
				setTitle(mNavigationDrawerItemTitles[position]);
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity_actions, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		int itemId = item.getItemId();
		if (itemId == R.id.action_create) {
			setTitle("Tạo lộ trình mới");
			Intent intent = getIntent();
			intent.removeExtra("markerList");
			FragmentManager mng = getSupportFragmentManager();
			FragmentTransaction trs = mng.beginTransaction();
			CreateRoute frag1 = new CreateRoute();
			trs.replace(R.id.content_frame, frag1, "createRoute");
			trs.addToBackStack("createRoute");
			trs.commit();
		} else {
		}
		return super.onOptionsItemSelected(item);
	}

	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
}

package vn.edu.fpt.fts.layout;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.TextView;
import vn.edu.fpt.fts.classes.AlarmReceiver;
import vn.edu.fpt.fts.classes.Constant;
import vn.edu.fpt.fts.drawer.ListItem;
import vn.edu.fpt.fts.drawer.ListNotiAdapter;
import vn.edu.fpt.fts.drawer.NavDrawerAdapter;
import vn.edu.fpt.fts.drawer.NavDrawerItem;

public class MainActivity extends FragmentActivity {

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
				fragment = new DealHistory();
				break;
			case 3:
				fragment = new History();
				break;
			case 4:
				SharedPreferences share = getSharedPreferences("driver",
						Context.MODE_PRIVATE);
				Editor editor = share.edit();
				editor.remove("driverID");
				editor.remove("email");
				editor.remove("driverName");
				editor.remove("remember");
				editor.commit();

				AlarmReceiver.list = new ArrayList<ListItem>();

				NotificationManager nMgr = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				nMgr.cancelAll();

				Intent intentstop = new Intent(MainActivity.this,
						AlarmReceiver.class);
				PendingIntent senderstop = PendingIntent.getBroadcast(
						MainActivity.this, 0, intentstop, 0);
				AlarmManager alarmManagerstop = (AlarmManager) getSystemService(ALARM_SERVICE);
				alarmManagerstop.cancel(senderstop);

				Intent intent = new Intent(getApplicationContext(), Login.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_CLEAR_TASK
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				break;
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
	private static final String SERVICE_URL = Constant.SERVICE_URL
			+ "DealNotification/getDealNotificationByDriverID";
	private NavDrawerAdapter adapter;
	Intent alarmIntent;
	Intent alarmIntent2;
	private ArrayList<NavDrawerItem> array = new ArrayList<NavDrawerItem>();
	Handler handler;
	ArrayList<ListItem> list = new ArrayList<ListItem>();
	ArrayList<String> map;
	protected DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	ActionBarDrawerToggle mDrawerToggle;
	private String[] mNavigationDrawerItemTitles;

	private TypedArray mNavigationImage;
	ListNotiAdapter notiAdapter;
	int oldSize, newSize;
	private PendingIntent pendingIntent, pendingIntent2;

	Runnable r;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getActionBar();

		actionBar.setIcon(R.drawable.ic_action_place);
		actionBar.setTitle("Lộ trình");

		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE
				| ActionBar.DISPLAY_SHOW_HOME | ActionBar.DISPLAY_HOME_AS_UP);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);

		TextView driver = (TextView) findViewById(R.id.driver);

		SharedPreferences share = getSharedPreferences("driver",
				Context.MODE_PRIVATE);

		driver.setText("Xin chào tài xế " + share.getString("driverName", ""));

		mNavigationDrawerItemTitles = getResources().getStringArray(
				R.array.navigation_drawer_items_array);
		mNavigationImage = getResources().obtainTypedArray(
				R.array.nav_drawer_icons);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		for (int i = 0; i < mNavigationDrawerItemTitles.length; i++) {
			array.add(new NavDrawerItem(mNavigationDrawerItemTitles[i],
					mNavigationImage.getResourceId(i + 1, -1)));
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
			@Override
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
			}

			/** Called when a drawer has settled in a completely open state. */
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

		alarmIntent = new Intent(MainActivity.this, AlarmReceiver.class);
		alarmIntent
				.putExtra("driverID", getIntent().getStringExtra("driverID"));

//		alarmIntent2 = new Intent(MainActivity.this, OrderAlarmReceiver.class);
//		alarmIntent2.putExtra("driverID", getIntent()
//				.getStringExtra("driverID"));

		pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
				alarmIntent, PendingIntent.FLAG_CANCEL_CURRENT);
//		pendingIntent2 = PendingIntent.getBroadcast(MainActivity.this, 0,
//				alarmIntent2, PendingIntent.FLAG_CANCEL_CURRENT);
		int interval = 10000;

		manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis(), interval, pendingIntent);
//		manager.setInexactRepeating(AlarmManager.RTC_WAKEUP,
//				System.currentTimeMillis(), interval, pendingIntent2);

		if (getIntent().getStringExtra("dealID") == null) {
			FragmentManager mng = getSupportFragmentManager();
			FragmentTransaction trs = mng.beginTransaction();
			RouteList frag1 = new RouteList();
			trs.replace(R.id.content_frame, frag1);
			trs.commit();
		} else {
			String id = getIntent().getStringExtra("dealID");
			NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
			mNotificationManager.cancel(Integer.parseInt(id));
			String type = getIntent().getStringExtra("type");
			String status = getIntent().getStringExtra("status");
			String sender = getIntent().getStringExtra("sender");
			if (type.equals("deal")) {
				if (status.equals("1")) {
					FragmentManager mng = getSupportFragmentManager();
					FragmentTransaction trs = mng.beginTransaction();
					OfferResponse frag = new OfferResponse();
					Bundle bundle = new Bundle();
					bundle.putString("dealID", id);
					frag.setArguments(bundle);
					trs.replace(R.id.content_frame, frag);
					trs.addToBackStack(null);
					trs.commit();
				} else {
					FragmentManager mng = getSupportFragmentManager();
					FragmentTransaction trs = mng.beginTransaction();
					DealHistoryDetail frag = new DealHistoryDetail();
					Bundle bundle = new Bundle();
					bundle.putString("dealID", id);
					frag.setArguments(bundle);
					trs.replace(R.id.content_frame, frag);
					trs.addToBackStack(null);
					trs.commit();
				}
			} else if (type.equals("order")) {
				FragmentManager mng = getSupportFragmentManager();
				FragmentTransaction trs = mng.beginTransaction();
				HistoryDetail frag = new HistoryDetail();
				Bundle bundle = new Bundle();
				bundle.putString("orderID", id);
				frag.setArguments(bundle);
				trs.replace(R.id.content_frame, frag);
				trs.addToBackStack(null);
				trs.commit();
			}
		}

		if (getIntent().getStringExtra("orderID") != null) {
			FragmentManager mng = getSupportFragmentManager();
			FragmentTransaction trs = mng.beginTransaction();
			HistoryDetail frag = new HistoryDetail();
			Bundle bundle = new Bundle();
			bundle.putString("orderID", getIntent().getStringExtra("orderID"));
			frag.setArguments(bundle);
			trs.replace(R.id.content_frame, frag);
			trs.addToBackStack(null);
			trs.commit();
		}
		getIntent().removeExtra("dealID");
		getIntent().removeExtra("status");
		getIntent().removeExtra("type");
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
		switch (item.getItemId()) {
		case R.id.action_create:
			setTitle("Tạo lộ trình mới");
			Intent intent = getIntent();
			intent.removeExtra("markerList");
			FragmentManager mng = getSupportFragmentManager();
			FragmentTransaction trs = mng.beginTransaction();
			CreateRoute frag1 = new CreateRoute();
			trs.replace(R.id.content_frame, frag1, "createRoute");
			trs.addToBackStack("createRoute");
			trs.commit();
			return true;
		default:
			break;
		}
		return false;
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}
}
